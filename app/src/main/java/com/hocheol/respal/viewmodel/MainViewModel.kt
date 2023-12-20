package com.hocheol.respal.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseViewModel
import com.hocheol.respal.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private lateinit var supportFragmentManager: FragmentManager

    fun init(sfm: FragmentManager) {
        supportFragmentManager = sfm
    }

    fun openFragment(fragment: Fragment, data: JSONObject?, tag: String) {
        Log.e("Test", "[openView] tag = $tag")
        var bundle: Bundle? = null
        if (data != null) {
            Log.e("Test", "bundle set = $data")
            bundle = Bundle()
            bundle.putString("data", data.toString())
            fragment.arguments = bundle
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout, fragment, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
//        _fragmentToReplace.postValue(fragment)
    }

    fun replaceFragment(fragment: Fragment, data: JSONObject?, tag: String) {
        Log.e("Test", "[replaceFragment] tag = $tag")
        var bundle: Bundle? = null
        if (data != null) {
            Log.e("Test", "bundle set = $data")
            bundle = Bundle()
            bundle.putString("data", data.toString())
            fragment.arguments = bundle
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment, tag)
            .commitAllowingStateLoss()
//        _fragmentToReplace.postValue(fragment)
    }

    fun closeFragment(fragment: Fragment) {
        Log.e("Test", "[closeFragment] tag = " + fragment.tag)
        val list = supportFragmentManager.fragments
        Log.e("Test", "fragments list = " + list.size)
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss()
        }
        // 프래그먼트를 닫으면 _fragmentToReplace의 값을 이전 프래그먼트로 설정
        val indexOfRemovedFragment = list.indexOf(fragment)
        val previousFragment = if (indexOfRemovedFragment > 0) list[indexOfRemovedFragment - 1] else null
        Log.e("Test", "[closeFragment] previousFragment  = $previousFragment")
//        _fragmentToReplace.postValue(previousFragment)
    }
}