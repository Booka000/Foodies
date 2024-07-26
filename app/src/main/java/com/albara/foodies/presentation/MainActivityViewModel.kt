package com.albara.foodies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albara.foodies.domain.use_case.manege_date_in_local.AccessDataInLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val accessDataInLocalUseCase: AccessDataInLocalUseCase
) : ViewModel() {

    val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch {
            accessDataInLocalUseCase.emptyTheCart()
        }.invokeOnCompletion {
            _isReady.update { true }
        }
    }
}