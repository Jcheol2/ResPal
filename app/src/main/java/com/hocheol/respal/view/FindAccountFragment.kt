package com.hocheol.respal.view

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentFindAccountBinding
import com.hocheol.respal.databinding.FragmentLoginBinding
import com.hocheol.respal.viewmodel.FindAccountViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.widget.utils.Contants.LOGIN_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindAccountFragment : BaseFragment<FragmentFindAccountBinding>(R.layout.fragment_find_account) {
    private var TAG = this.javaClass.simpleName

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<FindAccountViewModel>()

    override fun init() {
        binding.findAccountBtn.setOnSingleClickListener {
//            if () 계정 찾기 실패 시 visible 하도록 변경
            binding.findAccountFailedView.visibility = View.VISIBLE
        }
        binding.cancelBtn.setOnSingleClickListener {
            mainViewModel.replaceFragment(LoginFragment(), null, LOGIN_FRAGMENT_TAG)
        }
        binding.emailInputText
    }
}