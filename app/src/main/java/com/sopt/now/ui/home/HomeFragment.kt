package com.sopt.now.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.ui.base.BindingFragment
import com.sopt.now.ui.main.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.util.friend.FriendAdapter
import com.sopt.now.util.user.User

class HomeFragment() : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private lateinit var friendAdapter: FriendAdapter
    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFriendAdapter()
        observeFriendList()
        observeUserInfo()
    }

    private fun initFriendAdapter() {
        friendAdapter = FriendAdapter()
        binding.rvFriends.run {
            adapter = friendAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun observeUserInfo() {
        val userId = requireActivity().intent.getStringExtra(LOGIN_INFO)
        userId?.let { viewModel.fetchUserInfo(it.toInt()) }
        viewModel.userInfo.observe(viewLifecycleOwner) {
            friendAdapter.setUser(User(it.authenticationId, it.nickname, it.phone))
        }
    }

    private fun observeFriendList() {
        viewModel.fetchFriends(PAGE)
        viewModel.friendList.observe(viewLifecycleOwner) {
            friendAdapter.setFriendList(it)
        }
    }

    companion object {
        const val PAGE = 2
    }

}
