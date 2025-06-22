package com.friosteven.rosebedsheet_kmp.modules.seller.presentation

import com.friosteven.rosebedsheet_kmp.core.data.repository.base.Status
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.ColorModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.SizeModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.repository.SellerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState

class SellerPresenter(private val repository: SellerRepository) : KoinComponent {
    private val presenterScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val _isLoadingColors = MutableStateFlow(false)
    @NativeCoroutinesState
    val isLoadingColors: StateFlow<Boolean> = _isLoadingColors.asStateFlow()

    private val _colors = MutableStateFlow<List<ColorModel>>(emptyList())
    @NativeCoroutinesState
    val colors: StateFlow<List<ColorModel>> = _colors.asStateFlow()

    private val _colorError = MutableStateFlow<String?>(null)
    @NativeCoroutinesState
    val colorError: StateFlow<String?> = _colorError.asStateFlow()



    // --- State Properties for SIZES ---
    private val _isLoadingSizes = MutableStateFlow(false)
    @NativeCoroutinesState
    val isLoadingSizes: StateFlow<Boolean> = _isLoadingSizes.asStateFlow()

    private val _sizes = MutableStateFlow<List<SizeModel>>(emptyList())
    @NativeCoroutinesState
    val sizes: StateFlow<List<SizeModel>> = _sizes.asStateFlow()

    private val _sizeError = MutableStateFlow<String?>(null)
    @NativeCoroutinesState
    val sizeError: StateFlow<String?> = _sizeError.asStateFlow()


    fun fetchColors() {
        presenterScope.launch {
            _isLoadingColors.value = true
            _colorError.value = null

            try {
                repository.fetchColors().collect { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            _colors.value = resource.data ?: emptyList()
                            print("from android ${_colors.value}")
                        }
                        Status.ERROR -> {
                            _colorError.value = resource.message ?: "Failed to load colors"
                            print("from android error ${_colorError.value}")
                        }
                        Status.LOADING -> { }
                    }
                }
            } catch (e: Exception) {
                _colorError.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoadingColors.value = false
            }
        }
    }


    /**
     * This method MUST be called when the presenter's lifecycle ends
     * (e.g., from a ViewModel's onCleared() or a deinit in Swift)
     * to cancel all running coroutines and prevent memory leaks.
     */
    fun onCleared() {
        presenterScope.cancel()
    }
}