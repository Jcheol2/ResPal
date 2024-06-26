package com.hocheol.respal.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.repository.MainRepository
import com.hocheol.respal.widget.utils.Constants.ACTIVITY_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.MY_RESUME_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.SETTINGS_FRAGMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    private lateinit var supportFragmentManager: FragmentManager

    private val _currentFragmentTag = MutableLiveData<String?>()
    val currentFragmentTag: LiveData<String?> = _currentFragmentTag

    private val _currentViewPagerPosition = MutableLiveData<Int>()
    val currentViewPagerPosition: LiveData<Int> = _currentViewPagerPosition

    fun init(sfm: FragmentManager) {
        supportFragmentManager = sfm
    }

    fun openFragment(fragment: Fragment, data: JSONObject?, tag: String) {
        if (data != null) {
            val bundle = Bundle()
            bundle.putString("data", data.toString())
            fragment.arguments = bundle
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout, fragment, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
        setCurrentFragmentTag(tag)
    }

    fun replaceFragment(fragment: Fragment, data: JSONObject?, tag: String) {
        if (data != null) {
            val bundle = Bundle()
            bundle.putString("data", data.toString())
            fragment.arguments = bundle
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment, tag)
            .commitAllowingStateLoss()
        setCurrentFragmentTag(tag)
    }

    fun closeFragment(fragment: Fragment) {
        val list = supportFragmentManager.fragments
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss()
        }
        // 프래그먼트를 닫으면 _currentFragmentTag의 값을 이전 프래그먼트로 설정
        val indexOfRemovedFragment = list.indexOf(fragment)
        val previousFragment = if (indexOfRemovedFragment > 0) list[indexOfRemovedFragment - 1] else null
        Log.e(TAG, "[closeFragment] previousFragment  = $previousFragment")
        setCurrentFragmentTag(previousFragment?.tag)
    }

    private fun setCurrentFragmentTag(currentFragmentTag: String?) {
        _currentFragmentTag.postValue(currentFragmentTag)
    }

    fun onPageSelected(position: Int) {
        when(position) {
            0 -> {
                setCurrentFragmentTag(MY_RESUME_FRAGMENT_TAG)
            }
            1 -> {
                setCurrentFragmentTag(ACTIVITY_FRAGMENT_TAG)
            }
            2 -> {
                setCurrentFragmentTag(SETTINGS_FRAGMENT)
            }
        }
        _currentViewPagerPosition.postValue(position)
    }

    fun requestOauthInfo(uid: String, type: String, callback: (Boolean) -> Unit) {
        coroutineScope.launch {
            try {
                val response: ResponseBody = withContext(Dispatchers.IO) {
                    try {
                        mainRepository.requestOauthInfo(uid, type)
                    } catch (e: HttpException) {
                        val errorCode = e.code()
                        Log.e(TAG, "HTTP Error Code: $errorCode")
                        throw e
                    }
                }
                Log.d("TEST", response.toString())
//                sharedPreferenceStorage.saveUserInfo(
//                    UserInfo(
//                        response.result.userInfo.email,
//                        null,
//                        response.result.userInfo.image,
//                        response.result.userInfo.nickname,
//                        response.result.provider
//                    ))
//                sharedPreferenceStorage.saveAccessToken(response.result.accessToken)
//                sharedPreferenceStorage.saveRefreshToken(response.result.refreshToken)
                callback(true)
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
                callback(false)
            }
        }
    }

//    fun sendOauthSignUp(code: String, callback: (Boolean) -> Unit) {
//        coroutineScope.launch {
//            try {
//                val response: NewMemberResponseDto = withContext(Dispatchers.IO) {
//                    try {
//                        mainRepository.oauthSignUp(code).blockingGet()
//                    } catch (e: HttpException) {
//                        val errorCode = e.code()
//                        Log.e(TAG, "HTTP Error Code: $errorCode")
//                        throw e
//                    }
//                }
//
//                val requestInput: HashMap<String, Any?> = HashMap()
//                requestInput["email"] = response.result.userInfo.email
//                requestInput["password"] = "temp1234!"
//                requestInput["picture"] = response.result.userInfo.image
//                requestInput["nickname"] = response.result.userInfo.nickname
//                requestInput["provider"] = response.result.provider
//
//                sharedPreferenceStorage.saveUserInfo(
//                    UserInfo(
//                        response.result.userInfo.email,
//                        "temp1234!",
//                        response.result.userInfo.image,
//                        response.result.userInfo.nickname,
//                        response.result.provider
//                    )
//                )
//
//                val gson = Gson()
//                Log.d(TAG, "Request JSON: ${gson.toJson(requestInput)}")
//
//                // Use async to execute this block concurrently
//                val response1Deferred = async(Dispatchers.IO) {
//                    try {
//                        mainRepository.signUp(requestInput.toJsonRequestBody()).blockingGet()
//                    } catch (e: HttpException) {
//                        val errorCode = e.code()
//                        Log.e(TAG, "HTTP Error Code: $errorCode")
//                        throw e
//                    }
//                }
//
//                // Wait for the result of the async block
//                val response1: SignUpResponseDto = response1Deferred.await()
//
////                sharedPreferenceStorage.saveAccessToken(response1.result.accessToken)
////                sharedPreferenceStorage.saveRefreshToken(response1.result.refreshToken)
//                callback(true)
//            } catch (e: Exception) {
//                Log.d(TAG, e.printStackTrace().toString())
//                callback(false)
//            }
//        }
//    }

//    fun sendOauthSignUp(code: String) = mainRepository.sendOauthSignUp(code)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(
//            { responseDto ->
//                val requestInput: HashMap<String, Any?> = HashMap()
//                requestInput["email"] = responseDto.result.userInfo.email
//                requestInput["password"] = "temp1234!"
//                requestInput["picture"] = responseDto.result.userInfo.image
//                requestInput["nickname"] = responseDto.result.userInfo.nickname
//                requestInput["provider"] = responseDto.result.provider
//                sharedPreferenceStorage.saveUserInfo(
//                    UserInfo(
//                        responseDto.result.userInfo.email,
//                        "temp1234!",
//                        responseDto.result.userInfo.image,
//                        responseDto.result.userInfo.nickname,
//                        responseDto.result.provider
//                    ))
//                val gson = Gson()
//                val mediaType = "application/json".toMediaTypeOrNull()
//                val json = gson.toJson(requestInput)
//                val requestBody = RequestBody.create(mediaType, json)
//
//                Log.d(TAG, "requestInput : $requestInput")
//                signUpOauth(requestBody)
//            },
//            { error ->
//                error.printStackTrace()  // 에러 로그 출력
//
//                val response = error as? HttpException
//                if (response?.code() == 400) {
//                    println("type 설정 에러")
//                } else {
//                    // 기타 에러 처리
//                }
//            }
//        )
//
//    private fun signUpOauth(requestBody: RequestBody) = mainRepository.signUpOauth(requestBody)
//        .subscribeOn(Schedulers.io())
//        .observeOn(Schedulers.io())
//        .subscribe({ items ->
//            // 여기서 액세스토큰 리프레시 토큰 저장 후 ME 화면으로 이동
//            println(items)
//            sharedPreferenceStorage.saveAccessToken(items.result.accessToken)
//            sharedPreferenceStorage.saveRefreshToken(items.result.refreshToken)
//            replaceFragment(MyResumeFragment(), null, Constants.MY_RESUME_FRAGMENT_TAG)
//        }, { e ->
//            println(e.toString())
//        })
}