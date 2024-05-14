package com.hocheol.respal.view

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentLoginBinding
import com.hocheol.respal.viewmodel.LoginViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.widget.utils.Constants.FIND_ACCOUNT_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.GITHUB
import com.hocheol.respal.widget.utils.Constants.GOOGLE
import com.hocheol.respal.widget.utils.Constants.KAKAO
import com.hocheol.respal.widget.utils.Constants.MY_RESUME_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.SIGN_UP_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private var TAG = this.javaClass.simpleName
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<LoginViewModel>()

    override fun init() {
        binding.idInputText
        binding.pwInputText
        binding.findAccountBtn.setOnSingleClickListener {
            mainViewModel.replaceFragment(FindAccountFragment(), null, FIND_ACCOUNT_FRAGMENT_TAG)
        }
        binding.loginBtn.setOnSingleClickListener {
//            if () 로그인 성공 시 replace 하도록 변경
            mainViewModel.replaceFragment(MyResumeFragment(), null, MY_RESUME_FRAGMENT_TAG)
        }
        binding.signUpBtn.setOnSingleClickListener {
            mainViewModel.replaceFragment(SignUpFragment(), null, SIGN_UP_FRAGMENT_TAG)
        }
        binding.githubLoginBtn.setOnSingleClickListener {
            viewModel.signInOauth(requireContext(), GITHUB)
        }
        binding.googleLoginBtn.setOnSingleClickListener {
            viewModel.signInOauth(requireContext(), GOOGLE)
        }
        binding.kakaoLoginBtn.setOnSingleClickListener {
            viewModel.signInOauth(requireContext(), KAKAO)
        }
    }
}