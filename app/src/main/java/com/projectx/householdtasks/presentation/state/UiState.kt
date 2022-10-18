package com.projectx.householdtasks.presentation.state

sealed interface UiState<out Type> {
    object Loading : UiState<Nothing>
    data class Error(val message: String) : UiState<Nothing>
    open class Ready<out Type>(val data: Type) : UiState<Type>

    companion object {
        fun <Type> process(
            state: UiState<Type>,
            onLoading: () -> Unit = {},
            onError: (message: String) -> Unit = {},
            onReady: (data: Type) -> Unit = {}
        ) = when (state) {
            is Loading -> onLoading()
            is Error -> onError(state.message)
            is Ready -> onReady(state.data)
        }
    }
}