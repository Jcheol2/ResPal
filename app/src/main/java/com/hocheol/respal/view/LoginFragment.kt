package com.hocheol.respal.view

import android.text.Editable
import android.text.method.PasswordTransformationMethod
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
import com.hocheol.respal.widget.utils.Constants.MY_RESUME_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.Constants.SIGN_UP_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.isValidEmail
import com.hocheol.respal.widget.utils.isValidPassword
import com.hocheol.respal.widget.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<LoginViewModel>()

    private var isPasswordVisible = false
    override fun init() {
        val userInfo = viewModel.getUserInfo()
        if (userInfo != null) {
            val editableEmail = Editable.Factory.getInstance().newEditable(userInfo.email)
            binding.emailInputText.text = editableEmail
        }
        binding.findAccountBtn.setOnSingleClickListener {
            mainViewModel.replaceFragment(FindAccountFragment(), null, FIND_ACCOUNT_FRAGMENT_TAG)
        }
        binding.loginBtn.setOnSingleClickListener {
            val inputEmail = binding.emailInputText.text.toString()
            // 임시 주석
//            if (!isValidEmail(inputEmail)) {
//                shortShowToast("Invalid email format")
//                return@setOnSingleClickListener
//            }
            val inputPw = binding.pwInputText.text.toString()
            if (!isValidPassword(inputPw)) {
                shortShowToast("Password must be 8-20 characters long, and include letters, numbers, and special characters")
                return@setOnSingleClickListener
            }
            viewModel.commonLogin(inputEmail, inputPw)
        }
        binding.pwEyeBtn.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.pwInputText.transformationMethod = null
                binding.pwEyeBtn.setImageResource(R.drawable.ic_eye_visible)
                binding.pwInputText.setSelection(binding.pwInputText.text?.length ?: 0)
            } else {
                binding.pwInputText.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.pwEyeBtn.setImageResource(R.drawable.ic_eye_invisible)
                binding.pwInputText.setSelection(binding.pwInputText.text?.length ?: 0)
            }
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
                        mainViewModel.replaceFragment(ViewPagerFragment(), null, MY_RESUME_FRAGMENT_TAG)
                    } else {
                        shortShowToast("Login Failed")
                    }
                }
            }
        }
    }
}