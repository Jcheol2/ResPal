package com.hocheol.respal.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentLoginBinding
import com.hocheol.respal.viewmodel.LoginViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.viewmodel.MyResumeViewModel
import com.hocheol.respal.widget.utils.Contants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyResumeFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_my_resume) {
    private var TAG = this.javaClass.simpleName

    private val mainViewModel by viewModels<MainViewModel>()
    private val viewModel by viewModels<MyResumeViewModel>()

    override fun init() {
    }
}