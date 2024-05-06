package com.sopt.now.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.sopt.now.R
import com.sopt.now.activity.ChangePwdActivity
import com.sopt.now.activity.LoginActivity
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.viewmodel.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.viewmodel.MainViewModel.Companion.USER_INFO
import com.sopt.now.viewmodel.MyPageViewModel

class MyPageFragment() : BindingFragment<FragmentMyPageBinding>(FragmentMyPageBinding::inflate) {

    private var userId: String? = null
    private lateinit var viewModel: MyPageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)

        userId = requireActivity().intent.getStringExtra(LOGIN_INFO)
        userId?.let { viewModel.fetchUserInfo(it.toInt()) }

        updateUI()
        logoutBtnClick()
        changePasswordBtnClick()
    }

    private fun updateUI() {
        with(binding) {
            viewModel.userInfo.observe(viewLifecycleOwner) {
                it?.let {
                    tvUserNickname.text = it.data.nickname
                    tvDescription.text = it.data.phone
                }
            }
        }
    }

    private fun changePasswordBtnClick() {
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
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity?.startActivity(intent)
    }

}