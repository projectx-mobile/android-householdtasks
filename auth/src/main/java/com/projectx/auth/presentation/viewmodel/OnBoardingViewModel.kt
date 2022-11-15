package com.projectx.auth.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.projectx.common.presentation.viewmodel.BaseViewModel
import com.projectx.auth.presentation.fragment.OnBoardingFragmentDirections
import com.projectx.common.presentation.navigation.NavEvent

class OnBoardingViewModel : BaseViewModel() {

    private val _imagePreviousPosition = MutableLiveData<Int>()
    private val _imagePosition = MutableLiveData<Int>()

    /**
     * Pair: prev position, cur position
     */
    val positionChange = MediatorLiveData<Pair<Int?, Int?>>().apply {
        addSource(_imagePreviousPosition) {
            value = it to value?.second
        }
        addSource(_imagePosition) {
            value = value?.first to it
        }
    }.distinctUntilChanged()

    fun onImageSelected(position: Int) {
        _imagePosition.value = position
    }

    fun onImageUnselected(position: Int) {
        _imagePreviousPosition.value = position
    }

    fun navigateToChooseLoginType() =
        navigate(NavEvent.To(OnBoardingFragmentDirections.actionOnBoardingFragmentToChooseLoginTypeFragment()))

    fun navigateToSignUpWithEmail() =
        navigate(NavEvent.To(OnBoardingFragmentDirections.actionOnBoardingFragmentToSignUpWithEmailFragment()))
}