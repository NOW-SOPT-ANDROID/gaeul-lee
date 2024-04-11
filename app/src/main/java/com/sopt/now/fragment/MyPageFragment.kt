package com.sopt.now.fragment

import UserData
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.now.activity.LoginActivity
import com.sopt.now.databinding.FragmentMyPageBinding

class MyPageFragment(private val user: UserData) : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = requireNotNull(_binding)

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
        with(binding){
            userNickname.text = user.nickname
            userId.text = user.id
            userPassword.text = user.pwd
        }

        logoutBtnClick()
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