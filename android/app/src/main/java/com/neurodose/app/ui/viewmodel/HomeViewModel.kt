package com.neurodose.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurodose.app.data.local.entity.SupplementEntity
import com.neurodose.app.domain.usecase.GetSupplementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * HomeViewModel manages the state for the HomeScreen.
 * 
 * ViewModels:
 * - Hold UI state
 * - Survive configuration changes (screen rotation)
 * - Communicate with use cases
 * - Provide data to the UI via Flow/StateFlow
 * 
 * @HiltViewModel tells Hilt to inject this ViewModel.
 * @Inject constructor parameters are automatically provided by Hilt.
 * 
 * @param getSupplementsUseCase The use case for getting supplements
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSupplementsUseCase: GetSupplementsUseCase
) : ViewModel() {
    
    /**
     * All supplements as a StateFlow.
     * 
     * StateFlow is a Flow that:
     * - Emits the current state immediately to new collectors
     * - Survives configuration changes
     * - Is optimized for UI updates
     * 
     * stateIn() converts Flow to StateFlow with:
     * - viewModelScope: Coroutine scope tied to ViewModel lifetime
     * - SharingStarted.Lazily: Start collecting when first subscriber appears
     * - initialValue: Empty list initially
     */
    val supplements: Flow<List<SupplementEntity>> = getSupplementsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )
}

