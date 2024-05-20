package com.hocheol.respal.view

import androidx.core.content.ContextCompat
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
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<MyResumeViewModel>()

    private var btnState = "myCv"

    override fun init() {
        val userInfo = viewModel.getUserInfo()
        if (userInfo != null) {
            val profilePhoto = userInfo.picture.ifEmpty {
                ContextCompat.getDrawable(requireActivity(), R.drawable.ic_user)
            }
            Glide.with(this)
                .load(profilePhoto)
                .into(binding.profileIconImage)
            val nickname = userInfo.nickname.ifEmpty {
                userInfo.email.split("@")[0]
            }
            binding.profileUserNameText.text = nickname
        }
        binding.editProfileBtn
        binding.myCvBtn.setOnSingleClickListener {
            viewModel.findResume()
            if (btnState != "myCv") {
                binding.myCvBtn.setTextColor(resources.getColor(R.color.color_white))
                binding.myCvBtn.setBackgroundResource(R.drawable.background_button_6d4acd)
                binding.mentionedBtn.setTextColor(resources.getColor(R.color.color_3B3B3B))
                binding.mentionedBtn.background = null
                btnState = "myCv"
            }
        }
        binding.mentionedBtn.setOnSingleClickListener {
            if (btnState != "mentioned") {
                binding.mentionedBtn.setTextColor(resources.getColor(R.color.color_white))
                binding.mentionedBtn.setBackgroundResource(R.drawable.background_button_6d4acd)
                binding.myCvBtn.setTextColor(resources.getColor(R.color.color_3B3B3B))
                binding.myCvBtn.background = null
                btnState = "mentioned"
            }
        }
        binding.cvRecyclerView // TODO
        initObserve()
    }

    private fun initObserve() {
        viewModel.responseEvent.observe(viewLifecycleOwner) { success ->
            when (success.first) {
                "findResume" -> {
                    if (success.second) {
                        shortShowToast("findResume Success")
                    } else {
                        shortShowToast("findResume Failed")
                    }
                }
            }
        }
    }
}