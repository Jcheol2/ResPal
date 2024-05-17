package com.hocheol.respal.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
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

    private lateinit var photoImage: String

    override fun init() {
        binding.githubLoginBtn // TODO
        binding.googleLoginBtn // TODO
        binding.kakaoLoginBtn // TODO
        binding.emailInputText // TODO
        binding.pwInputText // TODO
        binding.pwConfirmInputText // TODO
        binding.nickNameInputText // TODO
        binding.postPhotoBtn.setOnSingleClickListener {
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityResult.launch(intent)
        }
        binding.postPhotoText // TODO
        binding.termsOfServiceBtn.setOnSingleClickListener {
        }
        binding.privacyPolicyBtn.setOnSingleClickListener {
        }
        binding.signUpBtn.setOnSingleClickListener {
            val inputEmail = binding.emailInputText.text.toString()
            if (!isValidEmail(inputEmail)) {
                shortShowToast("Invalid email format")
                return@setOnSingleClickListener
            }
            val inputPw = binding.pwInputText.text.toString()
            if (!isValidPassword(inputPw)) {
                shortShowToast("Password must be 8-20 characters long, and include letters, numbers, and special characters")
                return@setOnSingleClickListener
            }
            val inputNickname = binding.nickNameInputText.text.toString().ifEmpty {
                inputEmail.split("@")[0]
            }
            val inputPhoto = if (::photoImage.isInitialized) {
                photoImage
            } else {
                ""
            }

            viewModel.signUp(inputEmail, inputPw, inputNickname, inputPhoto) {
                activity?.runOnUiThread {
                    if (it) {
                        shortShowToast("signUp Success")
                    } else {
                        shortShowToast("signUp Failed")
                    }
                }
            }
        }
        binding.loginBtn.setOnSingleClickListener {
            mainViewModel.replaceFragment(LoginFragment(), null, LOGIN_FRAGMENT_TAG)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,20}\$"
        return password.matches(passwordPattern.toRegex())
    }
}