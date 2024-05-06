package com.sopt.now.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.friend.FriendAdapter
import com.sopt.now.viewmodel.HomeViewModel
import com.sopt.now.viewmodel.MainViewModel.Companion.LOGIN_INFO

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
        userId?.let { viewModel.fetchUserInfo(it.toInt()) }

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