package com.hocheol.respal.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentSettingsBinding
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.viewmodel.SettingsViewModel
import com.hocheol.respal.widget.utils.Constants.LOGIN_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment: BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<SettingsViewModel>()

    override fun init() {
        binding.logoutBtn.setOnSingleClickListener {
            viewModel.logout()
        }

        initObserve()
    }

    private fun initObserve() {
        viewModel.responseEvent.observe(viewLifecycleOwner) { success ->
            when (success.first) {
                "logout" -> {
                    if (success.second) {
                        shortShowToast("Logout Success")
                        mainViewModel.replaceFragment(LoginFragment(), null, LOGIN_FRAGMENT_TAG)
                    } else {
                        shortShowToast("Logout Failed")
                    }
                }
            }
        }
    }
}