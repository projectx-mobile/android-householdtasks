package com.projectx.householdtasks.presentation.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.DialogChangeProfilePhotoBinding
import com.projectx.householdtasks.databinding.FragmentEditProfileBinding
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
                binding.profilePhoto.setImageBitmap(
                    Drawable.createFromStream(requireActivity().contentResolver.openInputStream(data.data!!),
                        null)!!.toBitmap()
                )
                binding.personNameFirstLetter.text = ""
            }
        }

    private val resultCameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {

            val bundle = result.data?.extras
            val bitmap: Bitmap = bundle?.get("data") as Bitmap
            binding.profilePhoto.setImageBitmap(bitmap)
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
        viewModel.newName.postValue("Марго")

        binding.apply {
            toolbarLayout.toolbar.setOnClickListener {
                findNavController().navigateUp()
            }
            buttonSaveChanges.setOnClickListener {
                binding.textVewSaveChanges.visibility = View.VISIBLE
                findNavController().navigate(R.id.profileFragment)
            }

            binding.profilePhoto.setOnClickListener {
                createDialogSetPhoto()
            }

            buttonDeleteProfile.setOnClickListener {
                createDialogDeleteProfile()
            }
        }
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
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.title_no_camera_permission))
            .setMessage(getString(R.string.message_no_camera_permission))
            .setPositiveButton(
                getString(R.string.settings)
            ) { dialog, id ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", requireActivity().packageName, null)
                    )
                )
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }
            .setCancelable(false)
            .show()
        val inset = InsetDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rectangle_white_without_boarders
            ), 40
        )
        alertDialog.window?.setBackgroundDrawable(inset)
    }

    private fun createDialogDeleteProfile() {
        val textView = TextView(activity)
        with(textView) {
            textView.text = context.getString(R.string.title_dialog_delete_profile)
            textView.textSize = 18.0F
            textView.setTypeface(null, Typeface.BOLD)
            setPadding(24, 26, 24, 20)
        }
        val alertDialog = AlertDialog.Builder(requireContext())
            .setCustomTitle(textView)
            .setMessage(getString(R.string.message_dialog_delete_profile))
            .setNegativeButton(getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }
            .setPositiveButton(getString(R.string.delete)) { dialog, which ->
                Toast.makeText(
                    requireContext(),
                    getString(R.string.profile_is_deleted), Toast.LENGTH_SHORT
                ).show()
            }
            .setCancelable(false)
            .show()
        val inset = InsetDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rectangle_white_without_boarders
            ), 40
        )
        alertDialog.window?.setBackgroundDrawable(inset)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
