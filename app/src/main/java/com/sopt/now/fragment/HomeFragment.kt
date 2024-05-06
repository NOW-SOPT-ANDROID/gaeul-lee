package com.sopt.now.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.sopt.now.R
import com.sopt.now.ServicePool
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.fragment.MyPageFragment.Companion.LOGIN_INFO
import com.sopt.now.friend.Friend
import com.sopt.now.friend.FriendAdapter
import com.sopt.now.response.ResponseFriendsDto
import com.sopt.now.response.ResponseUserInfoDto
import com.sopt.now.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment() : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    private lateinit var friendAdapter: FriendAdapter
    val friendList = mutableListOf<Friend>()
    val viewModel = HomeViewModel()

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
        // viewModel.uploadFriends()
        getFriendsInfo()
        // 인텐트에서 userId 가져오기
        val userId = requireActivity().intent.getStringExtra(LOGIN_INFO)
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
                    friends?.forEach { friend ->
                        friendList.add(Friend(friend.avatar, friend.firstName, friend.email))
                    }
                    friendAdapter.setFriendList(friendList)
                } else {
                    Toast.makeText(requireContext(), R.string.friends_error, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseFriendsDto>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
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
                    data?.let {
                        friendAdapter.setUser(it.data)
                    }

                } else {
                    val error = response.errorBody()
                    Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseUserInfoDto>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString())
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}