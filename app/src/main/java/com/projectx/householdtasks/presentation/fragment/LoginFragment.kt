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
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentLoginBinding
import com.projectx.householdtasks.presentation.*
import com.projectx.householdtasks.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        person = arguments?.getString(PERSON)
        if (person != null) {
            setStringsForCurrentPerson(person!!)
        }

        addTextChangeListeners()
        addObservers()
        addUiStateObserver()
        setLink()

        viewModel.isButtonEnabled.observe(viewLifecycleOwner) {
            binding.buttonLoginSubmit.isEnabled = it
        }

        binding.apply {
            appbarLogin.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            buttonLoginSubmit.setOnClickListener {
                viewModel.handleSaveChanges()
            }
        }
    }

    private fun addTextChangeListeners() {
        binding.emailLogin.editText?.addTextChangedListener {
            viewModel.setEmailValue(it.toString())
            viewModel.resetErrorForEmail()
        }

        binding.familyIdLogin.editText?.addTextChangedListener {
            viewModel.setPasswordValue(it.toString())
            viewModel.resetErrorForPassword()
        }
    }

    private fun addObservers() {
        viewModel.email.observe(viewLifecycleOwner) {
            if (binding.emailLogin.editText!!.text.toString() != it) {
                binding.emailLogin.editText!!.setText(it)
            }
        }

        viewModel.password.observe(viewLifecycleOwner) {
            if (binding.familyIdLogin.editText!!.text.toString() != it) {
                binding.familyIdLogin.editText!!.setText(it)
            }
        }
    }

    private fun addUiStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            resetErrors()

            when (it.loginEmailResult) {
                LoginEmailResult.InvalidEmailError -> setErrorForEmail()
                LoginEmailResult.OK -> {}
            }

            when (it.loginPasswordResult) {
                LoginPasswordResult.LengthError -> setErrorForPassword()
                LoginPasswordResult.OK -> {}
            }
            when (it.requestResult) {
                RequestResult.Success -> {
                    findNavController().navigate(R.id.profileFragment)
                }
                RequestResult.RequestFailedError -> setAuthenticationError()
                else -> {}
            }
        }
    }

    private fun setStringsForCurrentPerson(person: String) {
        if (person == "child") { //todo
            binding.textviewLoginSubtitle.text = getString(R.string.login_child_subtitle)
            binding.emailLogin.hint = getString(R.string.login_email_child)
            binding.familyIdLogin.hint = getString(R.string.login_password_child)
        }
    }

    private fun setLink() {
        var start = LoginViewModel.START_PARENT_LINK
        var end = LoginViewModel.END_PARENT_LINK
        if (person == "child") {
            binding.textviewLoginRestoreAccount.text = getString(R.string.login_with_qr_code)
            start = LoginViewModel.START_CHILD_LINK
            end = LoginViewModel.END_CHILD_LINK
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
        binding.emailLogin.error = " " // reset error in emailLogin field and show general mistake for two fields
        binding.familyIdLogin.error = getString(R.string.authentication_error)
    }

    //    TODO: add error for failure request
    private fun setConnectionError() {
        binding.familyIdLogin.error = getString(R.string.connection_error)
    }

    inner class HelpMessageClickableSpan : ClickableSpan() {
        override fun onClick(widget: View) {
            Toast.makeText(requireContext(), "Link clicked", Toast.LENGTH_SHORT)
                .show() //todo text add to strings
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
