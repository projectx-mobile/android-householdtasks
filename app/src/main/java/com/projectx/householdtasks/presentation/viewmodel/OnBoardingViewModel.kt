package com.projectx.householdtasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.event.OnBoardingScreenEvent
import com.projectx.householdtasks.presentation.state.UiState

class OnBoardingViewModel : BaseViewModel<LiveData<Pair<Int?, Int?>>, OnBoardingScreenEvent>() {

    private val _imagePreviousPosition = MutableLiveData<Int>()
    private val _imagePosition = MutableLiveData<Int>()

    /**
     * Pair: prev position, cur position
     */
    override val state = object : LiveData<UiState<LiveData<Pair<Int?, Int?>>>>(
        UiState.Ready(
            MediatorLiveData<Pair<Int?, Int?>>().apply {
                addSource(_imagePreviousPosition) {
                    value = it to value?.second
                }
                addSource(_imagePosition) {
                    value = value?.first to it
                }
            }.distinctUntilChanged()
        )
    ) {}

    override fun onEvent(event: OnBoardingScreenEvent) {
        when (event) {
            is OnBoardingScreenEvent.OnImageSelected -> onImageSelected(event.position)
            is OnBoardingScreenEvent.OnImageUnselected -> onImageUnselected(event.position)
            is OnBoardingScreenEvent.NavigateToChooseLoginType -> event.navController.navigate(R.id.action_onBoardingFragment_to_chooseLoginTypeFragment)
        }
    }

    private fun onImageSelected(position: Int) {
        _imagePosition.value = position
    }

    private fun onImageUnselected(position: Int) {
        _imagePreviousPosition.value = position
    }
}