package ir.avarche.android.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val providers: Map<String, Provider<out ViewModel>>):ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        providers[modelClass.simpleName]?.get() as T

}