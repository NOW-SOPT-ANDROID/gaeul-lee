package com.sopt.now.fragment

import UserData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
                    Log.d("login", "data: $data, userId: $userId")
                    with(binding) {
                        tvUserNickname.text = data?.data?.nickname
                        tvDescription.text = data?.data?.phone
                    }

                } else {
                    val error = response.errorBody()
                    Log.e("HomeFragment", "error: $error")
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                Log.e("HomeFragment", "onFailure: ${t.message}")
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
            val intent = Intent(requireContext(), LoginActivity::class.java)
            activity?.startActivity(intent)
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