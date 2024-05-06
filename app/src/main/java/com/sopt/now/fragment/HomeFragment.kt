package com.sopt.now.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var viewModel: HomeViewModel

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

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.fetchFriends(2)

        val userId = requireActivity().intent.getStringExtra(LOGIN_INFO)
        userId?.let {
            viewModel.fetchUserInfo(it.toInt())
        }

        viewModel.friendList.observe(viewLifecycleOwner) {
            friendAdapter.setFriendList(it)
        }
        viewModel.userInfo.observe(viewLifecycleOwner) {
            friendAdapter.setUser(it.data)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}