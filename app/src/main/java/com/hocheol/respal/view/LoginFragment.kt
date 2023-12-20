package com.hocheol.respal.view

import com.hocheol.respal.widget.utils.Contants.MY_RESUME_FRAGMENT_TAG
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentLoginBinding
import com.hocheol.respal.viewmodel.LoginViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private var TAG = this.javaClass.simpleName

    private val mainViewModel by viewModels<MainViewModel>()
    private val viewModel by viewModels<LoginViewModel>()

    override fun init() {
        binding.githubLoginBtn.setOnClickListener {
            viewModel.signInOauth(requireContext(), "github")
        }
        binding.googleLoginBtn.setOnClickListener {
            viewModel.signInOauth(requireContext(), "google")
        }
        binding.kakaoLoginBtn.setOnClickListener {
            viewModel.signInOauth(requireContext(), "kakao")
        }

        viewModel.fragmentToReplace.observe(viewLifecycleOwner) {fragment ->
            if (fragment != null) {
                activity?.runOnUiThread{
                    mainViewModel.replaceFragment(fragment, null, MY_RESUME_FRAGMENT_TAG)
                    viewModel.setFragmentToReplace(null)
                }
            }
        }
    }
}