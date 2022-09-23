package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectx.householdtasks.databinding.FragmentChooseLoginTypeBinding
import com.projectx.householdtasks.presentation.viewmodel.ChooseLoginTypeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseLoginTypeFragment : BaseFragment() {

    private var _binding: FragmentChooseLoginTypeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ChooseLoginTypeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentChooseLoginTypeBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindUI().subscribeUI()
    }
    private fun FragmentChooseLoginTypeBinding.bindUI() = this.also {

    }

    private fun FragmentChooseLoginTypeBinding.subscribeUI() = this.also {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}