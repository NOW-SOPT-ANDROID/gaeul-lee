package com.sopt.now.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.presentation.main.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.util.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment() : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val friendAdapter by lazy { FriendAdapter() }
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFriendAdapter()
        observeFriends()
        observeUserInfo()
    }

    private fun initFriendAdapter() {
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

    private fun observeFriends() {
        viewModel.fetchFriends(PAGE)
        viewModel.friends.observe(viewLifecycleOwner) {
            friendAdapter.setFriendList(it)
        }
    }

    companion object {
        const val PAGE = 2
    }

}
