package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.projectx.householdtasks.presentation.event.UiEvent
import com.projectx.householdtasks.presentation.state.UiState.Companion.process
import com.projectx.householdtasks.presentation.viewmodel.BaseViewModel

abstract class BaseFragment<Binding : ViewBinding, State : Any, Event : UiEvent> : Fragment() {

    private var _binding: Binding? = null
    private val binding: Binding get() = _binding!!
    protected abstract fun getViewBinding(): Binding

    protected val viewModel: BaseViewModel<State, Event> by lazy { getBaseViewModel() }
    protected abstract fun getBaseViewModel(): BaseViewModel<State, Event>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = getViewBinding().apply { _binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
        subscribeUI()
    }

    open fun bindUI() = binding

    open fun subscribeUI() = binding.apply {
        viewModel.state.observe(viewLifecycleOwner) { UiState ->
            process(UiState,
                onLoading = { processLoading() },
                onError = { message -> processError(message) },
                onReady = { state: State -> processState(state) }
            )
        }
    }

    open fun Binding.processLoading() {}

    open fun Binding.processError(message: String) {}

    open fun Binding.processState(state: State) {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}