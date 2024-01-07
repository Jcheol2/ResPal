package com.hocheol.respal.view

import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.data.local.SharedPreferenceStorage
import com.hocheol.respal.databinding.FragmentMyResumeBinding
import com.hocheol.respal.repository.MainRepository
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.viewmodel.MyResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyResumeFragment @Inject constructor(
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseFragment<FragmentMyResumeBinding>(R.layout.fragment_my_resume) {
    private var TAG = this.javaClass.simpleName

    private val mainViewModel by viewModels<MainViewModel>()
    private val viewModel by viewModels<MyResumeViewModel>()

    override fun init() {
        val userInfo = sharedPreferenceStorage.getUserInfo()
        if (userInfo != null) {
            Glide.with(this)
                .load(userInfo.picture)
                .into(binding.profileIconImage)
            binding.profileUserNameText.text = userInfo.nickname
        }
    }
}