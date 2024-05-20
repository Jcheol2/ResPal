package com.hocheol.respal.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentLoginBinding
import com.hocheol.respal.view.viewpager.ViewPagerFragment
import com.hocheol.respal.viewmodel.LoginViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.widget.utils.Constants.FIND_ACCOUNT_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.GITHUB
import com.hocheol.respal.widget.utils.Constants.GOOGLE
import com.hocheol.respal.widget.utils.Constants.KAKAO
import com.hocheol.respal.widget.utils.Constants.SIGN_UP_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.VIEW_PAGER_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<LoginViewModel>()

    override fun init() {
        binding.emailInputText // TODO
        binding.pwInputText // TODO
        binding.findAccountBtn.setOnSingleClickListener {
            mainViewModel.replaceFragment(FindAccountFragment(), null, FIND_ACCOUNT_FRAGMENT_TAG)
        }
        binding.loginBtn.setOnSingleClickListener {
            val inputEmail = binding.emailInputText.text.toString()
            val inputPw = binding.pwInputText.text.toString()
            if (inputEmail.isEmpty() || inputPw.isEmpty()) {
                return@setOnSingleClickListener
            }
            viewModel.commonLogin(inputEmail, inputPw)
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
        initObserve()
    }

    private fun initObserve() {
        viewModel.responseEvent.observe(viewLifecycleOwner) { success ->
            when (success.first) {
                "commonLogin" -> {
                    if (success.second) {
                        shortShowToast("Login Success")
                        mainViewModel.replaceFragment(ViewPagerFragment(), null, VIEW_PAGER_FRAGMENT_TAG)
                    } else {
                        shortShowToast("Login Failed")
                    }
                }
            }
        }
    }
}