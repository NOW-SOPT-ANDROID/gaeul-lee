package com.sopt.now.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.util.friend.FriendAdapter
import com.sopt.now.ui.base.BindingFragment
import com.sopt.now.ui.main.MainViewModel.Companion.LOGIN_INFO

class HomeFragment() : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var friendAdapter: FriendAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendAdapter = FriendAdapter()
        binding.rvFriends.run {
            adapter = friendAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.fetchFriends(PAGE)

        val userId = requireActivity().intent.getStringExtra(LOGIN_INFO)
        userId?.let { viewModel.fetchUserInfo(it.toInt()) }

        viewModel.friendList.observe(viewLifecycleOwner) {
            friendAdapter.setFriendList(it)
        }
        viewModel.userInfo.observe(viewLifecycleOwner) {
            friendAdapter.setUser(it.data)
        }
    }

    companion object {
        const val PAGE = 2
    }

}
