package com.hocheol.respal.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hocheol.respal.R
import com.hocheol.respal.viewmodel.FindAccountViewModel

class FindAccountFragment : Fragment() {

    companion object {
        fun newInstance() = FindAccountFragment()
    }

    private lateinit var viewModel: FindAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_find_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FindAccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}