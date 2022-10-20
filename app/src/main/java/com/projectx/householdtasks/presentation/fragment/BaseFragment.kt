package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.distinctUntilChanged
import androidx.viewbinding.ViewBinding
import com.projectx.householdtasks.presentation.state.UiState.Companion.process
import com.projectx.householdtasks.presentation.viewmodel.BaseViewModel

abstract class BaseFragment<Binding : ViewBinding, ViewModel : BaseViewModel>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> Binding
) : Fragment() {

    private var _binding: Binding? = null
    protected val binding: Binding get() = _binding!!

    protected abstract val viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflate.invoke(inflater, container, false).apply { _binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            bindUI()
            viewModel.subscribeUI()
        }
    }

    open fun Binding.bindUI() {}

    open fun ViewModel.subscribeUI() {}

    fun <Type> BaseViewModel.MutableUiState<Type>.observeUiState(
        isDistinctUntilChangedEnabled: Boolean = true,
        processLoading: () -> Unit = {},
        processError: (error: Throwable?) -> Unit = {},
        processState: (state: Type) -> Unit = {}
    ) {
        val state =
            if (isDistinctUntilChangedEnabled) this@observeUiState.distinctUntilChanged() else this@observeUiState
        state.observe(viewLifecycleOwner) { UiState ->
            process(UiState,
                onLoading = { processLoading() },
                onError = { error -> processError(error) },
                onReady = { state -> processState(state) }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}