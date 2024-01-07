package com.hocheol.respal.view

import android.util.Log
import android.view.View
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
        binding.githubLoginBtn.setOnClickListener(oauthClickListener)
        binding.googleLoginBtn.setOnClickListener(oauthClickListener)
        binding.kakaoLoginBtn.setOnClickListener(oauthClickListener)

        mainViewModel.oauthResponse.observe(requireActivity()) { oauthResponse ->
            if (oauthResponse.second == "signup") {
//                viewModel.sendOauthSignUp(oauthResponse.first)
            } else {
//                viewModel.sendOauthCallBack(oauthResponse.first)
            }
        }

        viewModel.fragmentToReplace.observe(this) { newFragment ->
            Log.d(TAG, "fragmentToReplace Observe")
            if (newFragment != null) {
                requireActivity().runOnUiThread{
                    mainViewModel.replaceFragment(newFragment, null, MY_RESUME_FRAGMENT_TAG)
                }
            }
        }
    }

    override fun onResume() {
        Log.d(TAG, "++ onResume ++")
        super.onResume()
//        viewModel.fragmentToReplace.observe(viewLifecycleOwner) { newFragment ->
//            if (newFragment != null) {
//                requireActivity().runOnUiThread {
//                    mainViewModel.replaceFragment(newFragment, null, MY_RESUME_FRAGMENT_TAG)
//                    viewModel.setFragmentToReplace(null)
//                }
//            }
//        }
        mainViewModel.oauthResponse.observe(requireActivity()) { oauthResponse ->
            if (oauthResponse.second == "signup") {
//                viewModel.sendOauthSignUp(oauthResponse.first)
            } else {
//                viewModel.sendOauthCallBack(oauthResponse.first)
            }
        }
    }

    private val oauthClickListener = View.OnClickListener { view ->
        val provider = when (view.id) {
            binding.githubLoginBtn.id -> "github"
            binding.googleLoginBtn.id -> "google"
            binding.kakaoLoginBtn.id -> "kakao"
            else -> return@OnClickListener
        }
        viewModel.signInOauth(requireContext(), provider)
    }
}