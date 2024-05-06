package com.sopt.now.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BindingFragment<T : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T
) : Fragment() {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding ?: throw IllegalStateException("binding object is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}