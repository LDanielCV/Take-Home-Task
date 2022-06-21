package com.assigment.taskcurrencyapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assigment.taskcurrencyapp.data.server.common.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    protected fun <T> mapResource(
        mutableStateFlow: MutableStateFlow<T>,
        resource: Resource<out Any?>
    ) =
        when (resource) {
            is Resource.Success ->
                mutableStateFlow.value = resource.data as T
            is Resource.Error ->
                _errorMessage.value = resource.message ?: ""
            is Resource.Loading ->
                _loading.postValue(resource.isLoading)
        }


}