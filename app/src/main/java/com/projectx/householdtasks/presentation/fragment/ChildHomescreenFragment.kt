package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectx.householdtasks.databinding.FragmentHomescreenChildBinding

class ChildHomescreenFragment : BaseFragment() {

    private var _binding: FragmentHomescreenChildBinding? = null
    private val binding get() = _binding!!
    //private val viewModel by viewModel<ChildHomescreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentHomescreenChildBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindUI().subscribeUI()
    }

    private fun FragmentHomescreenChildBinding.bindUI() = this.also {




    }


    private fun FragmentHomescreenChildBinding.subscribeUI() = this.also {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}