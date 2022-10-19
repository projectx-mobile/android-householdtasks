package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentLoginBinding
import com.projectx.householdtasks.presentation.LoginEmailResult
import com.projectx.householdtasks.presentation.LoginPasswordResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.LoginScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val PERSON = "person" //todo

class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel.LoginScreenUiState, LoginScreenEvent>() {

    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<LoginViewModel>().value

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

    override fun bindUI() = super.bindUI().apply {
        person = arguments?.getString(PERSON)
        if (person != null) {
            setStringsForCurrentPerson(person!!)
        }

        addTextChangeListeners()
        setLink()

        appbarLogin.toolbar.setNavigationOnClickListener {
            viewModel.onEvent(LoginScreenEvent.NavBack(findNavController()))
        }
        buttonLoginSubmit.setOnClickListener {
            viewModel.onEvent(LoginScreenEvent.HandleSaveChanges)
        }
    }

    override fun FragmentLoginBinding.processState(state: LoginViewModel.LoginScreenUiState) {
        addObservers(state)
        addUiStateObserver(state)
    }

    private fun addTextChangeListeners() {
        binding.emailLogin.editText?.addTextChangedListener {
            viewModel.onEvent(LoginScreenEvent.SetEmailValue(it.toString()))
            viewModel.onEvent(LoginScreenEvent.ResetEmailError)
        }

        binding.familyIdLogin.editText?.addTextChangedListener {
            viewModel.onEvent(LoginScreenEvent.SetPasswordValue(it.toString()))
            viewModel.onEvent(LoginScreenEvent.ResetPasswordError)
        }
    }

    private fun addObservers(state: LoginViewModel.LoginScreenUiState) {
        val isSaveButtonEnabled = (viewModel as LoginViewModel).isSaveButtonEnabled()
        state.email.let {
            //if (binding.emailLogin.editText!!.text.toString() != it) {
            //    binding.emailLogin.editText!!.setText(it)
            //}
            binding.buttonLoginSubmit.isEnabled = isSaveButtonEnabled
        }

        state.password.let {
            //if (binding.familyIdLogin.editText!!.text.toString() != it) {
            //    binding.familyIdLogin.editText!!.setText(it)
            //}
            binding.buttonLoginSubmit.isEnabled = isSaveButtonEnabled
        }
    }

    private fun addUiStateObserver(state: LoginViewModel.LoginScreenUiState) {
        state.let {
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
                    viewModel.onEvent(LoginScreenEvent.NavigateToProfile(findNavController()))
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
        binding.emailLogin.error =
            " " // reset error in emailLogin field and show general mistake for two fields
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

    override fun onDestroy() {
        super.onDestroy()
        person = null
    }
}
