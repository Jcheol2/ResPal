package com.hocheol.respal.widget.utils

import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val TAG = this.javaClass.simpleName

    val mPending: AtomicBoolean = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG,"Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}

fun View.setOnSingleClickListener(click: (View) -> Unit) {
    val listener = DuplicateClickListener { click(it) }
    setOnClickListener(listener)
}

class DuplicateClickListener(private val click: (View) -> Unit) : View.OnClickListener {

    companion object {
        private const val CLICK_INTERVAL = 1000
    }

    private var lastClickedTime: Long = 0L

    override fun onClick(v: View?) {
        if (isSafe() && v != null) {
            lastClickedTime = System.currentTimeMillis()
            click(v)
        }
    }

    private fun isSafe(): Boolean {
        return System.currentTimeMillis() - lastClickedTime > CLICK_INTERVAL
    }
}

fun Any.toJsonRequestBody(): RequestBody {
    val gson = Gson()
    val json = gson.toJson(this)
    val mediaType = "application/json".toMediaTypeOrNull()
    return json.toRequestBody(mediaType)
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(emailPattern.toRegex())
}

fun isValidPassword(password: String): Boolean {
    val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,20}\$"
    return password.matches(passwordPattern.toRegex())
}