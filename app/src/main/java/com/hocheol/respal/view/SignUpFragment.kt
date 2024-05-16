package com.hocheol.respal.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentLoginBinding
import com.hocheol.respal.databinding.FragmentSignUpBinding
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.viewmodel.SignUpViewModel
import com.hocheol.respal.widget.utils.Constants.LOGIN_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {
    private var TAG = this.javaClass.simpleName

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<SignUpViewModel>()

    override fun init() {
        binding.githubLoginBtn // TODO
        binding.googleLoginBtn // TODO
        binding.kakaoLoginBtn // TODO
        binding.idInputText // TODO
        binding.pwInputText // TODO
        binding.emailInputText // TODO
        binding.nickNameInputText // TODO
        binding.termsOfServiceBtn.setOnSingleClickListener {
        }
        binding.privacyPolicyBtn.setOnSingleClickListener {
        }
        binding.signUpBtn.setOnSingleClickListener {
        }
        binding.loginBtn.setOnSingleClickListener {
            mainViewModel.replaceFragment(LoginFragment(), null, LOGIN_FRAGMENT_TAG)
        }
    }
}