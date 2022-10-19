package com.projectx.householdtasks.presentation.fragment

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentEditProfileEmailBinding
import com.projectx.householdtasks.presentation.EmailValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.EditProfileEmailScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.EditProfileEmailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileEmailFragment :
    BaseFragment<FragmentEditProfileEmailBinding, EditProfileEmailViewModel.EditProfileEmailUiState, EditProfileEmailScreenEvent>() {

    override fun getViewBinding() = FragmentEditProfileEmailBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<EditProfileEmailViewModel>().value

    override fun bindUI() = super.bindUI().apply {
        //TODO: set current email
        currentEmailLayout.editText?.setText("name@example.com")
        addEmailChangedListener()
        toolbarLayout.toolbar.setOnClickListener {
            viewModel.onEvent(EditProfileEmailScreenEvent.NavBack(findNavController()))
        }
        buttonSaveChanges.setOnClickListener {
            viewModel.onEvent(EditProfileEmailScreenEvent.HandleSaveChanges)
        }
    }

    private fun FragmentEditProfileEmailBinding.addEmailChangedListener() {
        newEmail.addTextChangedListener {
            viewModel.onEvent(EditProfileEmailScreenEvent.SetNewEmailValue(it.toString()))
            buttonSaveChanges.isEnabled =
                (viewModel as EditProfileEmailViewModel).isSaveButtonEnabled()
            viewModel.onEvent(EditProfileEmailScreenEvent.ResetEmailError)
        }
    }

    override fun FragmentEditProfileEmailBinding.processState(state: EditProfileEmailViewModel.EditProfileEmailUiState) {
        resetError()
        helpMessage.visibility = View.INVISIBLE

        when (state.emailValidationResult) {
            EmailValidationResult.InvalidEmailError -> setErrorForEmail()
            else -> {}
        }

        when (state.requestResult) {
            RequestResult.Success -> {
                helpMessage.visibility = View.VISIBLE
                viewModel.onEvent(EditProfileEmailScreenEvent.NavigateToProfile(findNavController()))
            }
            RequestResult.RequestFailedError -> setConnectionError()
            else -> {}
        }

        state.newEmail.let {
            if (newEmailLayout.editText!!.text.toString() != it) {
                newEmailLayout.editText!!.setText(it)
            }
            buttonSaveChanges.isEnabled =
                (viewModel as EditProfileEmailViewModel).isSaveButtonEnabled()
        }
    }

    private fun FragmentEditProfileEmailBinding.resetError() {
        newEmailLayout.error = ""
    }

    private fun FragmentEditProfileEmailBinding.setErrorForEmail() {
        newEmailLayout.error = getString(R.string.email_error)
    }

    //    TODO: add error for failure request
    private fun FragmentEditProfileEmailBinding.setConnectionError() {
        newEmailLayout.error = "Ошибка подключения к серверу"
    }
}
