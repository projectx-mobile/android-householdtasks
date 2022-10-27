package com.projectx.auth.presentation.fragment

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.projectx.common.presentation.fragment.BaseFragment
import com.projectx.auth.R
import com.projectx.auth.databinding.FragmentLoginBinding
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.auth.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

const val PERSON = "person" //todo

class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {

    override val viewModel by viewModel<LoginViewModel>()

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

    override fun FragmentLoginBinding.bindUI() {
        binding.appbarLogin.toolbar.setNavigationOnClickListener {
            viewModel.navigate(NavEvent.Up)
        }
        person = arguments?.getString(PERSON)
        person?.let {
            setStringsForCurrentPerson(it)
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

    override fun LoginViewModel.subscribeUI() {
        email.observe(viewLifecycleOwner) {
            hideEmailErrorsOnChange()
        }
        password.observe(viewLifecycleOwner) {
            hidePasswordErrorsOnChange()
        }
    }

    private fun hideEmailErrorsOnChange() {
        binding.emailLogin.isErrorEnabled = false
    }

    private fun hidePasswordErrorsOnChange() {
        binding.familyIdLogin.error = null
    }

    private fun setLink() {
        var start = 19 //todo
        var end = 39 //todo
        if (person == "child") {
            binding.textviewLoginRestoreAccount.text = getString(R.string.login_with_qr_code)
            start = 0 //todo
            end = 21 //todo
        }
        val spannableString = SpannableString(binding.textviewLoginRestoreAccount.text)
        spannableString.setSpan(MyClickableSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
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
        binding.emailLogin.error = "Error" //todo get text from BA and add to strings
        binding.familyIdLogin.error = getString(R.string.authentication_error)
    }

    inner class MyClickableSpan : ClickableSpan() {
        override fun onClick(widget: View) {
            Toast.makeText(requireContext(), "Link clicked", Toast.LENGTH_SHORT)
                .show() //todo text add to strings
        }

        override fun updateDrawState(drawState: TextPaint) {
            super.updateDrawState(drawState)
            drawState.bgColor = ContextCompat.getColor(requireContext(), com.projectx.common.R.color.white)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        person = null
    }
}
