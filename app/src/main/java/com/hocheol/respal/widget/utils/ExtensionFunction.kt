package com.bixolon.tableorder.view.etc

import android.view.View
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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