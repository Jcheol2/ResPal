package com.hocheol.respal.view

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentFindAccountBinding
import com.hocheol.respal.viewmodel.FindAccountViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.widget.utils.Constants.LOGIN_FRAGMENT_TAG
import com.hocheol.respal.widget.utils.isValidEmail
import com.hocheol.respal.widget.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindAccountFragment : BaseFragment<FragmentFindAccountBinding>(R.layout.fragment_find_account) {
    private val TAG = this.javaClass.simpleName

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<FindAccountViewModel>()

    override fun init() {
        binding.findAccountBtn.setOnSingleClickListener {
            val emailAddress = binding.emailInputText.text.toString()
            if (!isValidEmail(emailAddress)) {
                shortShowToast("Invalid email format")
                binding.findAccountFailedView.visibility = View.VISIBLE
                return@setOnSingleClickListener
            }
            viewModel.findAccount(emailAddress, "tmpPassword")
        }
        binding.cancelBtn.setOnSingleClickListener {
            mainViewModel.replaceFragment(LoginFragment(), null, LOGIN_FRAGMENT_TAG)
        }
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.findAccountFailedView.visibility == View.VISIBLE) {
                    binding.findAccountFailedView.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) {
                return
            }
        }
        binding.emailInputText.addTextChangedListener(textWatcher)
        initObserve()
    }

    private fun initObserve() {
        viewModel.responseEvent.observe(viewLifecycleOwner) { success ->
            when (success.first) {
                "findAccount" -> {
                    if (success.second) {
                        shortShowToast("findAccount Success")
                    } else {
                        shortShowToast("findAccount Failed")
                    }
                }
            }
        }
    }
}