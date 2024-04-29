package com.sopt.now.fragment

import UserData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.databinding.FragmentHomeBinding
import com.sopt.now.friend.FriendAdapter
import com.sopt.now.viewmodel.HomeViewModel

class HomeFragment() : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding)

    private val viewModel = HomeViewModel()

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
        val friendAdapter = FriendAdapter()
        binding.rvFriends.run {
            adapter = friendAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        friendAdapter.setFriendList(viewModel.mockFriendList)
        //friendAdapter.setUser(user)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}