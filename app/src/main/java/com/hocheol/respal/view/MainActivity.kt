package com.hocheol.respal.view

import android.view.View
import androidx.activity.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseActivity
import com.hocheol.respal.databinding.ActivityMainBinding
import com.hocheol.respal.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel by viewModels<MainViewModel>() // by viewModels()을 사용하여 Hilt가 생성한 ViewModel 인스턴스를 획득

    // onCreate는 BaseActivity에서 정의했으므로 init이 거의 onCreate인 셈임..
    override fun init() {
        binding.activity = this
    }

    fun clickSearchBtn(view: View){
        shortShowToast("Toast Test")
        mainViewModel.getUserInfo(binding.name.text.toString())

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, LoginFragment(), "LOGIN_FRAGMENT_TAG")
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}