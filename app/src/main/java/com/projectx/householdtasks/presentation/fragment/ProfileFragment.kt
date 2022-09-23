package com.projectx.householdtasks.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileEmail.setOnClickListener {}
        addScrollListener()

        binding.navigationBar.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.profileEmail.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileEmailFragment)
        }

        binding.profilePassword.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfilePasswordFragment)
        }
        binding.profilePerson.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_accountStatusFragment)
        }
        binding.linkedAccounts.setOnClickListener {

        }
        binding.notifications.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_notificationFragment)
        }
        binding.addUserButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_inviteUserFragment)
        }
        binding.support.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_supportScreenFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun addScrollListener() {
        binding.nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > 0) {
                binding.appBarLayout.visibility = View.VISIBLE
            } else {
                binding.appBarLayout.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

