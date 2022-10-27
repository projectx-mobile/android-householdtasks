package com.projectx.common.presentation.state

sealed interface UiState<out Type> {
    object Loading : UiState<Nothing>
    open class Error(val error: Throwable? = null) : UiState<Nothing>
    data class Ready<out Type>(val data: Type) : UiState<Type>

    companion object {
        inline fun <Type> process(
            state: UiState<Type>?,
            onLoading: () -> Unit = {},
            onError: (error: Throwable?) -> Unit = {},
            onReady: (data: Type) -> Unit = {}
        ) = when (state) {
            is Loading -> onLoading()
            is Error -> onError(state.error)
            is Ready -> onReady(state.data)
            else -> {}
        }
    }
}