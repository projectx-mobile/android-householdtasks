package com.projectx.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.projectx.common.presentation.navigation.NavigationObserver
import com.projectx.common.presentation.viewmodel.BaseViewModel

abstract class BaseActivity<Binding : ViewBinding, ViewModel : BaseViewModel>(bindingInflater: (LayoutInflater) -> Binding) : AppCompatActivity() {

    protected val binding by viewBinding<Binding>(bindingInflater)
    protected abstract val viewModel: ViewModel
    protected open val navObserver by lazy { NavigationObserver(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navObserver.register(this)
        binding.bindUI()
    }

    open fun Binding.bindUI() {}

    protected open fun getNavController(@IdRes navHostId: Int) =
        (supportFragmentManager.findFragmentById(navHostId) as NavHostFragment).navController

    inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
        crossinline bindingInflater: (LayoutInflater) -> T
    ) = lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }
}