package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentLoginBinding
import com.projectx.householdtasks.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

const val PERSON = "person" //todo

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<LoginViewModel>()

    private var person: String? = null

    companion object {
        fun newInstance(person: String): LoginFragment { //todo
            val args = Bundle()
            args.putString(PERSON, person)
            val loginFragment = LoginFragment()
            loginFragment.arguments = args
            return loginFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentLoginBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this

        binding.appbarLogin.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        person = arguments?.getString(PERSON)
        if (person != null) {
            setStringsForCurrentPerson(person!!)
        }
        hideEmailErrorsOnChange()
        hidePasswordErrorsOnChange()
        setLink()
        setButtonContinueClickListener()
    }

    private fun setStringsForCurrentPerson(person: String) {
        if (person == "child") { //todo
            binding.textviewLoginSubtitle.text = getString(R.string.login_child_subtitle)
            binding.emailLogin.hint = getString(R.string.login_email_child)
            binding.familyIdLogin.hint = getString(R.string.login_password_child)
        }
    }

    private fun hideEmailErrorsOnChange() {
        viewModel.email.observe(viewLifecycleOwner) {
            binding.emailLogin.isErrorEnabled = false
        }
    }

    private fun hidePasswordErrorsOnChange() {
        viewModel.password.observe(viewLifecycleOwner) {
            binding.familyIdLogin.error = null
        }
    }

    private fun setLink() {
        var start = LoginViewModel.START_PARENT_LINK //todo
        var end = LoginViewModel.END_PARENT_LINK //todo
        if (person == "child") {
            binding.textviewLoginRestoreAccount.text = getString(R.string.login_with_qr_code)
            start = LoginViewModel.START_CHILD_LINK //todo
            end = LoginViewModel.END_CHILD_LINK //todo
        }
        val spannableString = SpannableString(binding.textviewLoginRestoreAccount.text)
        spannableString.setSpan(
            HelpMessageClickableSpan(),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.textviewLoginRestoreAccount.text = spannableString
        binding.textviewLoginRestoreAccount.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setButtonContinueClickListener() {
        binding.buttonLoginSubmit.setOnClickListener {
            resetErrors()
            if (!viewModel.isPasswordValid()) {
                setErrorForPassword()
            }
            if (!viewModel.isEmailValid()) {
                setErrorForEmail()
            }
            if (viewModel.isValid()) {

                // TODO: send API request
                var requestSucceeded = true
                requestSucceeded = Random.nextBoolean()
                if (requestSucceeded) {
                    Toast.makeText(
                        context,
                        getString(R.string.authentication_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.profileFragment)

                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.authentication_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                    setAuthenticationError()
                }
            }
        }
    }

    private fun resetErrors() {
        binding.emailLogin.isErrorEnabled = false
        binding.familyIdLogin.error = null
    }

    private fun setErrorForEmail() {
        binding.emailLogin.isErrorEnabled = true
        binding.emailLogin.error = getString(R.string.email_error)
    }

    private fun setErrorForPassword() {
        binding.familyIdLogin.error = getString(R.string.password_error)
    }

    private fun setAuthenticationError() {
//        reset error in emailLogin field and show general mistake for two fields
        binding.emailLogin.error = " " //todo get text from BA and add to strings
        binding.familyIdLogin.error = getString(R.string.authentication_error)
    }

    inner class HelpMessageClickableSpan : ClickableSpan() {
        override fun onClick(widget: View) {
            Toast.makeText(requireContext(), "Link clicked", Toast.LENGTH_SHORT).show() //todo text add to strings
        }

        override fun updateDrawState(drawState: TextPaint) {
            super.updateDrawState(drawState)
            drawState.bgColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        person = null
    }
}

