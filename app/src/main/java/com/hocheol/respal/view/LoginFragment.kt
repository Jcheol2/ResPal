package com.hocheol.respal.view

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentLoginBinding
import com.hocheol.respal.viewmodel.LoginViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.widget.utils.Contants.MY_RESUME_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private var TAG = this.javaClass.simpleName

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<LoginViewModel>()

    override fun init() {
        binding.githubLoginBtn.setOnSingleClickListener {
            viewModel.signInOauth(requireContext(), "github")
        }
        binding.googleLoginBtn.setOnSingleClickListener {
//            viewModel.signInOauth(requireContext(), "google")
            mainViewModel.replaceFragment(MyResumeFragment(), null, MY_RESUME_FRAGMENT_TAG)
        }
        binding.kakaoLoginBtn.setOnSingleClickListener {
            viewModel.signInOauth(requireContext(), "kakao")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "++ onResume ++")
    }
}