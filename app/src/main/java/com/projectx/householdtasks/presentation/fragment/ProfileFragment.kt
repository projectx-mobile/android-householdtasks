package com.projectx.householdtasks.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentProfileBinding
import com.projectx.householdtasks.presentation.FamilyMember
import com.projectx.householdtasks.presentation.MyFamilyProfileAdapter

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var familyMembers: List<FamilyMember>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addScrollListener()
        setNavigation()
        familyMembers = createFamilyList()
        val adapter = MyFamilyProfileAdapter(familyMembers!!)
        binding.recyclerViewFamilyMembers.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFamilyMembers.layoutManager = layoutManager
    }

    private fun addScrollListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > 0) {
                    binding.appBarLayout.visibility = View.VISIBLE
                } else {
                    binding.appBarLayout.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setNavigation() {
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

        binding.support.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_supportScreenFragment)
        }
    }

    private fun createFamilyList(): List<FamilyMember> {
        return listOf(
            FamilyMember("Добавить", null, ContextCompat.getDrawable(requireContext(), R.drawable.selector_add_button)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Алиса", "А", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Борис", "Б", ContextCompat.getDrawable(requireContext(), R.drawable.oval)!!),
            FamilyMember("Приглашен", null, ContextCompat.getDrawable(requireContext(), R.drawable.button_invited_person)!!),
        )
    }

    override fun onDestroyView() {
        _binding = null
        familyMembers = null
        super.onDestroyView()
    }
}

