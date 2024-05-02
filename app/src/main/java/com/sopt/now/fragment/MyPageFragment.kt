package com.sopt.now.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.sopt.now.R
import com.sopt.now.ServicePool
import com.sopt.now.activity.ChangePwdActivity
import com.sopt.now.activity.LoginActivity
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.response.ResponseUserInfoDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment() : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = requireNotNull(_binding)

    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = requireActivity().intent.getStringExtra(LOGIN_INFO)
        if (userId != null) {
            // 해당 userId로 서버에서 사용자 정보 가져오기
            getUserInfo(userId!!.toInt())
        }
        logoutBtnClick()
        changePasswordBtnClick()
    }

    private fun getUserInfo(userId: Int) {
        ServicePool.userService.getUserInfo(userId).enqueue(object : Callback<ResponseUserInfoDto> {
            override fun onResponse(
                call: Call<ResponseUserInfoDto>,
                response: Response<ResponseUserInfoDto>,
            ) {
                if (response.isSuccessful) {
                    val data: ResponseUserInfoDto? = response.body()
                    with(binding) {
                        tvUserNickname.text = data?.data?.nickname
                        tvDescription.text = data?.data?.phone
                    }

                } else {
                    val error = response.errorBody()
                    Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                Toast.makeText(requireContext(), t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
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
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    activity?.startActivity(intent)
                }
                setNegativeButton("취소", null)
                show()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val USER_INFO = "userInfo"
        const val LOGIN_INFO = "loginInfo"
    }
}