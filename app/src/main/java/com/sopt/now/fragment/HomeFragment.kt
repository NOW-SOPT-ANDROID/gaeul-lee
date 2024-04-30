package com.sopt.now.fragment

import UserData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.ServicePool
import com.sopt.now.activity.MainActivity
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.fragment.MyPageFragment.Companion.LOGIN_INFO
import com.sopt.now.friend.Friend
import com.sopt.now.friend.FriendAdapter
import com.sopt.now.response.ResponseFriendsDto
import com.sopt.now.response.ResponseLoginDto
import com.sopt.now.response.ResponseUserInfoDto
import com.sopt.now.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment() : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    private val viewModel = HomeViewModel()
    private lateinit var friendAdapter: FriendAdapter
    val mockFriendList = mutableListOf<Friend>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendAdapter = FriendAdapter()
        binding.rvFriends.run {
            adapter = friendAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        getFriendsInfo()
        // 인텐트에서 userId 가져오기
        val userId = requireActivity().intent.getStringExtra(LOGIN_INFO)
        Log.e("HomeFragment", "userId: $userId")
        if (userId != null) {
            // 해당 userId로 서버에서 사용자 정보 가져오기
            getUserInfo(userId.toInt())
        }
    }

    private fun getFriendsInfo() {
        ServicePool.friendService.getFriends(2).enqueue(object : Callback<ResponseFriendsDto> {

            override fun onResponse(
                call: Call<ResponseFriendsDto>,
                response: Response<ResponseFriendsDto>
            ) {
                if (response.isSuccessful) {
                    val friends = response.body()?.data
                    Log.d("HomeViewModel", "friends: $friends")
                    friends?.forEach { friend ->
                        mockFriendList.add(Friend(friend.avatar, friend.firstName, friend.email))
                    }
                    friendAdapter.setFriendList(mockFriendList)
                } else {
                    Log.d("HomeViewModel", "response is not successful")
                }
            }

            override fun onFailure(call: Call<ResponseFriendsDto>, t: Throwable) {
                Log.d("HomeViewModel", "onFailure: ${t.message}")
            }

        })
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
                    data?.let {
                        friendAdapter.setUser(it.data)
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}