package com.friosteven.rosebedsheet_kmp.modules.seller.presentation

import com.friosteven.rosebedsheet_kmp.core.data.repository.base.Status
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoriesWithSizesModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoryModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.ColorModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.DesignModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.MaterialModel
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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.isActive

class SellerPresenter(private val repository: SellerRepository) : KoinComponent {
    private val presenterScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val _isLoading = MutableStateFlow(false)
    @NativeCoroutinesState
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    @NativeCoroutinesState
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _colors = MutableStateFlow<List<ColorModel>>(emptyList())
    @NativeCoroutinesState
    val colors: StateFlow<List<ColorModel>> = _colors.asStateFlow()

    fun fetchColors() {
        presenterScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.fetchColors().collect { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            _colors.value = resource.data ?: emptyList()
                        }
                        Status.ERROR -> {
                            _error.value = resource.message ?: "Failed to load colors (message was null)"
                        }
                        Status.LOADING -> { }
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * CATEGORIES
     */

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    @NativeCoroutinesState
    val categories: StateFlow<List<CategoryModel>> = _categories.asStateFlow()

    fun fetchCategories() {
        presenterScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.fetchCategories().collect { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            _categories.value = resource.data ?: emptyList()
                        }
                        Status.ERROR -> {
                            _error.value = resource.message ?: "Failed to load categories (message was null)"
                        }
                        Status.LOADING -> { }
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }


    /**
     * MATERIALS
     */

    private val _materials = MutableStateFlow<List<MaterialModel>>(emptyList())
    @NativeCoroutinesState
    val materials: StateFlow<List<MaterialModel>> = _materials.asStateFlow()

    fun fetchMaterials() {
        presenterScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.fetchMaterials().collect { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            _materials.value = resource.data ?: emptyList()
                        }
                        Status.ERROR -> {
                            _error.value = resource.message ?: "Failed to load materials (message was null)"
                        }
                        Status.LOADING -> { }
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }

    }

    private val _designs = MutableStateFlow<List<DesignModel>>(emptyList())
    @NativeCoroutinesState
    val designs: StateFlow<List<DesignModel>> = _designs.asStateFlow()

    fun fetchDesigns() {
        presenterScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.fetchDesigns().collect { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            _designs.value = resource.data ?: emptyList()
                        }
                        Status.ERROR -> {
                            _error.value = resource.message ?: "Failed to load designs (message was null)"
                        }
                        Status.LOADING -> { }
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _categoriesWithSizes = MutableStateFlow<List<CategoriesWithSizesModel>>(emptyList())
    @NativeCoroutinesState
    val categoriesWithSizes: StateFlow<List<CategoriesWithSizesModel>> = _categoriesWithSizes.asStateFlow()

    fun fetchCategoriesWithSizes() {
        presenterScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.fetchCategoriesWithSizes().collect { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            _categoriesWithSizes.value = resource.data ?: emptyList()
                        }
                        Status.ERROR -> {
                            _error.value = resource.message ?: "Failed to load categories with sizes (message was null)"
                        }
                        Status.LOADING -> { }
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "An unexpected error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }


    private val _sizesBasedOnSelectedCategory = MutableStateFlow<List<SizeModel>>(emptyList())
    @NativeCoroutinesState
    val sizesBasedOnSelectedCategory: StateFlow<List<SizeModel>> = _sizesBasedOnSelectedCategory.asStateFlow()

    fun setSizesBasedOnSelectedCategory(selectedCategory: CategoriesWithSizesModel) {
       val value = _categoriesWithSizes.value.find {
           it.name == selectedCategory.name
       }
        _sizesBasedOnSelectedCategory.value =  value?.sizes ?: emptyList()
    }


    /**
     * This method MUST be called when the presenter's lifecycle ends
     * (e.g., from a ViewModel's onCleared() or a deinit in Swift)
     * to cancel all running coroutines and prevent memory leaks.
     */
    fun onCleared() {
        presenterScope.cancel()
        _error.value = null
        _isLoading.value = false
        _colors.value = emptyList()
        _categories.value = emptyList()
        _materials.value = emptyList()
        _designs.value = emptyList()
        _categoriesWithSizes.value = emptyList()
        _sizesBasedOnSelectedCategory.value = emptyList()
    }
    }