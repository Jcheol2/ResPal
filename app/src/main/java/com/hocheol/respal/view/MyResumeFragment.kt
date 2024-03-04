package com.hocheol.respal.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentMyResumeBinding
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.viewmodel.MyResumeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyResumeFragment: BaseFragment<FragmentMyResumeBinding>(R.layout.fragment_my_resume) {
    private var TAG = this.javaClass.simpleName

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<MyResumeViewModel>()

    override fun init() {
        val userInfo = viewModel.getUserInfo()
        if (userInfo != null) {
            Glide.with(this)
                .load(userInfo.picture)
                .into(binding.profileIconImage)
            binding.profileUserNameText.text = userInfo.nickname
        }
        viewModel.getTest()
        viewModel.findResume()
    }
}