package com.sopt.now.presentation.mypage

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.sopt.now.R
import com.sopt.now.data.remote.ServicePool.friendService
import com.sopt.now.data.remote.ServicePool.userService
import com.sopt.now.data.repositoryImpl.FollowerRepositoryImpl
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.domain.FollowerRepository
import com.sopt.now.presentation.changePwd.ChangePwdActivity
import com.sopt.now.presentation.login.LoginActivity
import com.sopt.now.presentation.login.LoginActivity.Companion.PREF_KEY
import com.sopt.now.presentation.main.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.presentation.main.MainViewModel.Companion.USER_INFO
import com.sopt.now.util.BindingFragment

class MyPageFragment() : BindingFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {
    private val followerRepository: FollowerRepository by lazy {
        FollowerRepositoryImpl(
            userService,
            friendService
        )
    }
    private val viewModel: MyPageViewModel by activityViewModels {
        MyPageViewModelFactory(followerRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = requireActivity().intent.getStringExtra(LOGIN_INFO)
        fetchUserInfo(userId)
        observeUserInfo()
        logoutBtnClick()
        changePasswordBtnClick(userId)
    }

    private fun fetchUserInfo(userId: String?) {
        viewModel.fetchUserInfo(userId?.toInt() ?: 0)
    }

    private fun observeUserInfo() {
        with(binding) {
            viewModel.userInfo.observe(viewLifecycleOwner) {
                it?.let {
                    tvUserNickname.text = it.data.nickname
                    tvDescription.text = it.data.phone
                }
            }
        }
    }

    private fun changePasswordBtnClick(userId: String?) {
        binding.btnEditPwd.setOnClickListener {
            val intent = Intent(requireContext(), ChangePwdActivity::class.java)
            intent.putExtra(USER_INFO, userId)
            activity?.startActivity(intent)
        }
    }

    private fun logoutBtnClick() {
        binding.logoutBtn.setOnClickListener {
            AlertDialog.Builder(requireContext()).run {
                setTitle("로그아웃")
                setMessage("로그아웃 하시겠습니까?")
                setIcon(R.drawable.ic_pets_pink_24)
                setPositiveButton("확인") { _, _ ->
                    navigateToLoginActivity()
                }
                setNegativeButton("취소", null)
                show()
            }

        }
    }

    private fun navigateToLoginActivity() {
        val sharedPreferences = requireActivity().getSharedPreferences(PREF_KEY, MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity?.startActivity(intent)
    }

}