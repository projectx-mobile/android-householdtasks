package com.projectx.householdtasks.presentation.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.DialogChangeProfilePhotoBinding
import com.projectx.householdtasks.databinding.DialogDeleteProfileBinding
import com.projectx.householdtasks.databinding.DialogPermissionSettingsBinding
import com.projectx.householdtasks.databinding.FragmentEditProfileBinding
import com.projectx.householdtasks.presentation.NameValidationResult
import com.projectx.householdtasks.presentation.RequestResult
import com.projectx.householdtasks.presentation.event.EditProfileScreenEvent
import com.projectx.householdtasks.presentation.viewmodel.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding, EditProfileViewModel.EditProfileUiState, EditProfileScreenEvent>() {

    override fun getViewBinding() = FragmentEditProfileBinding.inflate(layoutInflater)

    override fun getBaseViewModel() = viewModel<EditProfileViewModel>().value

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val data: Intent = result.data!!
                val bitmap = Drawable.createFromStream(
                    requireActivity().contentResolver.openInputStream(data.data!!),
                    null
                )!!.toBitmap()
                Glide.with(this).load(bitmap).circleCrop().into(binding.profilePhoto)

                binding.personNameFirstLetter.text = ""
            }
        }

    private val resultCameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bundle = result.data?.extras
                val bitmap: Bitmap = bundle?.get("data") as Bitmap
                Glide.with(this).load(bitmap).circleCrop().into(binding.profilePhoto)
                binding.personNameFirstLetter.text = ""
            }
        }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        resultCameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    override fun bindUI() = super.bindUI().apply {
        //TODO: set current name
        viewModel.onEvent(EditProfileScreenEvent.SetNameValue("Марго"))

        addNameChangedListener()

        toolbarLayout.toolbar.setOnClickListener {
            viewModel.onEvent(EditProfileScreenEvent.NavBack(findNavController()))
        }
        buttonSaveChanges.setOnClickListener {
            viewModel.onEvent(EditProfileScreenEvent.HandleSaveChanges)
        }

        profilePhoto.setOnClickListener {
            createDialogSetPhoto()
        }

        buttonDeleteProfile.setOnClickListener {
            createDialogDeleteProfile()
        }
    }

    private fun FragmentEditProfileBinding.addNameChangedListener() {
        name.addTextChangedListener {
            viewModel.onEvent(EditProfileScreenEvent.SetNameValue(it.toString()))
            viewModel.onEvent(EditProfileScreenEvent.ResetNameError)
        }
    }

    override fun FragmentEditProfileBinding.processState(state: EditProfileViewModel.EditProfileUiState) {
        resetError()
        textVewSaveChanges.visibility = View.INVISIBLE

        state.newName.value?.let {
            addNameObserver(it)
        }
        state.nameValidationResult.value?.let {
            addValidationResultObserver(it)
        }
        state.requestResult.value?.let {
            addRequestResultObserver(it)
        }
    }

    private fun FragmentEditProfileBinding.addNameObserver(newName: String) {
        if (personNameLayout.editText?.text.toString() != newName) {
            personNameLayout.editText?.setText(newName)
        }
        val isSaveButtonEnabled = (viewModel as EditProfileViewModel).isSaveButtonEnabled()
        buttonSaveChanges.isEnabled = isSaveButtonEnabled
        if (isSaveButtonEnabled) {
            buttonSaveChanges.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.dark_text_color
                )
            )
        } else {
            buttonSaveChanges.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
    }

    private fun FragmentEditProfileBinding.addValidationResultObserver(nameValidationResult: NameValidationResult) {
        when (nameValidationResult) {
            NameValidationResult.LengthError -> setErrorForNameLength()
            NameValidationResult.NoLettersError -> setErrorForNameNotContainsLetters()
            NameValidationResult.InvalidCharacterError -> setErrorForNameContainsOtherSymbols()
            NameValidationResult.OK -> {}
        }
    }

    private fun FragmentEditProfileBinding.addRequestResultObserver(requestResult: RequestResult) {
        when (requestResult) {
            RequestResult.Success -> {
                textVewSaveChanges.visibility = View.VISIBLE
                viewModel.onEvent(EditProfileScreenEvent.NavigateToProfile(findNavController()))
            }
            RequestResult.RequestFailedError -> setConnectionError()
        }
    }

    private fun FragmentEditProfileBinding.resetError() {
        personNameLayout.error = ""
    }

    private fun FragmentEditProfileBinding.setErrorForNameLength() {
        personNameLayout.error = getString(R.string.name_length_error)
    }

    private fun FragmentEditProfileBinding.setErrorForNameNotContainsLetters() {
        personNameLayout.error = getString(R.string.name_not_contains_letter)
    }

    private fun FragmentEditProfileBinding.setErrorForNameContainsOtherSymbols() {
        personNameLayout.error = getString(R.string.name_not_contains_letter)
    }

    //TODO: add error for failure request
    private fun FragmentEditProfileBinding.setConnectionError() {
        personNameLayout.error = getString(R.string.connection_error)
    }

    private fun createDialogSetPhoto() {
        val dialog = DialogChangeProfilePhotoBinding.inflate(LayoutInflater.from(requireContext()))

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialog.root)
            .setCancelable(false)
            .show()

        val inset = InsetDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rectangle_white_without_boarders
            ), 40
        )
        alertDialog.window?.setBackgroundDrawable(inset)

        dialog.negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }
        dialog.loadPhotoButton.setOnClickListener {
            alertDialog.dismiss()
            resultLauncher.launch(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            )
        }

        dialog.selfieButton.setOnClickListener {
            onClickRequestPermission()
            alertDialog.dismiss()
        }
    }

    private fun onClickRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                resultCameraLauncher.launch(
                    Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE,
                    )
                )
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            ) -> {
                buildAlertMessageNoCameraPermission()
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun buildAlertMessageNoCameraPermission() {
        val dialog = DialogPermissionSettingsBinding.inflate(LayoutInflater.from(requireContext()))

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialog.root)
            .setCancelable(false)
            .show()

        val inset = InsetDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rectangle_white_without_boarders
            ), 40
        )
        alertDialog.window?.setBackgroundDrawable(inset)

        dialog.negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }
        dialog.positiveButton.setOnClickListener {
            alertDialog.dismiss()
            startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", requireActivity().packageName, null)
                )
            )
        }
    }

    private fun createDialogDeleteProfile() {
        val dialog = DialogDeleteProfileBinding.inflate(LayoutInflater.from(requireContext()))

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialog.root)
            .setCancelable(false)
            .show()

        val inset = InsetDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rectangle_white_without_boarders
            ), 40
        )
        alertDialog.window?.setBackgroundDrawable(inset)

        dialog.negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }
        dialog.positiveButton.setOnClickListener {
            //TODO: send request
            alertDialog.dismiss()
            Toast.makeText(
                requireContext(),
                getString(R.string.profile_is_deleted), Toast.LENGTH_SHORT
            ).show()
        }
    }
}
