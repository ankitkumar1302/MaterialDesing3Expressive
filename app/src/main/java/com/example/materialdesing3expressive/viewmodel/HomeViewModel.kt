package com.example.materialdesing3expressive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val userName: String = "User",
    val componentsCount: Int = 15,
    val recentActivity: List<String> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadHomeData()
    }
    
    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Simulate loading data
            kotlinx.coroutines.delay(1000)
            
            _uiState.value = HomeUiState(
                isLoading = false,
                userName = "John Doe",
                componentsCount = 15,
                recentActivity = listOf(
                    "Viewed Components Gallery",
                    "Updated Profile",
                    "Changed Theme Settings"
                )
            )
        }
    }
    
    fun refreshData() {
        loadHomeData()
    }
}