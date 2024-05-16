package com.hocheol.respal.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.hocheol.respal.R
import com.hocheol.respal.base.BaseFragment
import com.hocheol.respal.databinding.FragmentMyResumeBinding
import com.hocheol.respal.viewmodel.MainViewModel
import com.hocheol.respal.viewmodel.MyResumeViewModel
import com.hocheol.respal.widget.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyResumeFragment: BaseFragment<FragmentMyResumeBinding>(R.layout.fragment_my_resume) {
    private var TAG = this.javaClass.simpleName

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<MyResumeViewModel>()
    private var btnState = "myCv"

    override fun init() {
        val userInfo = viewModel.getUserInfo()
        if (userInfo != null) {
            Glide.with(this)
                .load(userInfo.picture)
                .into(binding.profileIconImage)
            binding.profileUserNameText.text = userInfo.nickname
        }
//        viewModel.getTest()
//        viewModel.findResume()
        binding.editProfileBtn
        binding.myCvBtn.setOnSingleClickListener {
            if (btnState != "myCv") {
                binding.myCvBtn.setTextColor(resources.getColor(R.color.color_white))
                binding.myCvBtn.setBackgroundResource(R.drawable.rectangle_background)
                binding.mentionedBtn.setTextColor(resources.getColor(R.color.color_3B3B3B))
                binding.mentionedBtn.background = null
                btnState = "myCv"
            }
        }
        binding.mentionedBtn.setOnSingleClickListener {
            if (btnState != "mentioned") {
                binding.mentionedBtn.setTextColor(resources.getColor(R.color.color_white))
                binding.mentionedBtn.setBackgroundResource(R.drawable.rectangle_background)
                binding.myCvBtn.setTextColor(resources.getColor(R.color.color_3B3B3B))
                binding.myCvBtn.background = null
                btnState = "mentioned"
            }
        }
        binding.cvRecyclerView // TODO
    }
}