package com.hocheol.respal.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentActivityBinding
import com.hocheol.respal.viewmodel.ActivityViewModel
import com.hocheol.respal.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityFragment: BaseFragment<FragmentActivityBinding>(R.layout.fragment_activity) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<ActivityViewModel>()

    override fun init() {
        binding.notificationViewAllBtn // TODO
        binding.notificationRecyclerView // TODO
        binding.notificationEmptyText // TODO
        binding.commentViewAllBtn // TODO
        binding.commentRecyclerView // TODO
        binding.commentEmptyText // TODO
    }
}