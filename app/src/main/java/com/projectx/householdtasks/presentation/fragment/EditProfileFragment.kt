package com.projectx.householdtasks.presentation.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.projectx.householdtasks.presentation.viewmodel.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditProfileFragment : BaseFragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<EditProfileViewModel>()

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

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            resultCameraLauncher.launch(
                Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE,
                )
            )
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //          TODO: set current name
        viewModel.setNameValue("Марго")

        addNameChangedListener()
        addNameObserver()
        addUiStateObserver()

        binding.apply {
            toolbarLayout.toolbarArrowBack.setOnClickListener {
                findNavController().navigateUp()
            }
            buttonSaveChanges.setOnClickListener {
                viewModel.handleSaveChanges()
            }

            binding.profilePhoto.setOnClickListener {
                createDialogSetPhoto()
            }

            buttonDeleteProfile.setOnClickListener {
                createDialogDeleteProfile()
            }
        }
    }

    private fun addNameChangedListener() {
        binding.name.addTextChangedListener {
            viewModel.setNameValue(it.toString())
            viewModel.resetNameError()
        }
    }

    private fun addNameObserver() {
        viewModel.newName.observe(viewLifecycleOwner) {
            if (binding.personNameLayout.editText!!.text.toString() != it) {
                binding.personNameLayout.editText!!.setText(it)
            }
            binding.buttonSaveChanges.isEnabled = viewModel.isSaveButtonEnabled()
            if (viewModel.isSaveButtonEnabled()) {
                binding.buttonSaveChanges.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_text_color
                    )
                )
            } else {
                binding.buttonSaveChanges.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            }
        }
    }

    private fun addUiStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            resetError()
            binding.textVewSaveChanges.visibility = View.INVISIBLE

            when (it.nameValidationResult) {
                NameValidationResult.LengthError -> setErrorForNameLength()
                NameValidationResult.NoLettersError -> setErrorForNameNotContainsLetters()
                NameValidationResult.InvalidCharacterError -> setErrorForNameContainsOtherSymbols()
                NameValidationResult.OK -> {}
            }

            when (it.requestResult) {
                RequestResult.Success -> {
                    binding.textVewSaveChanges.visibility = View.VISIBLE
                    findNavController().navigate(R.id.profileFragment)
                }
                RequestResult.RequestFailedError -> setConnectionError()
                else -> {}
            }
        }
    }

    private fun resetError() {
        binding.personNameLayout.error = ""
    }

    private fun setErrorForNameLength() {
        binding.personNameLayout.error = getString(R.string.name_length_error)
    }

    private fun setErrorForNameNotContainsLetters() {
        binding.personNameLayout.error = getString(R.string.name_not_contains_letter)
    }

    private fun setErrorForNameContainsOtherSymbols() {
        binding.personNameLayout.error = getString(R.string.name_not_contains_letter)
    }

    //    TODO: add error for failure request
    private fun setConnectionError() {
        binding.personNameLayout.error = getString(R.string.connection_error)
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
//            TODO: send request
            alertDialog.dismiss()
            Toast.makeText(
                requireContext(),
                getString(R.string.profile_is_deleted), Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
