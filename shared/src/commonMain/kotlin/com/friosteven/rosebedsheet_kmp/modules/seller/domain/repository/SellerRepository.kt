package com.friosteven.rosebedsheet_kmp.modules.seller.domain.repository

import com.friosteven.rosebedsheet_kmp.core.data.repository.base.Resource
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.CategoriesWithSizesDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.CategoryDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.ColorDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.DesignDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.MaterialDto
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoriesWithSizesModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoryModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.ColorModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.DesignModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.MaterialModel
import kotlinx.coroutines.flow.Flow

interface SellerRepository {
    suspend fun fetchColors(): Flow<Resource<List<ColorModel>>>
    suspend fun fetchCategories(): Flow<Resource<List<CategoryModel>>>
    suspend fun fetchMaterials(): Flow<Resource<List<MaterialModel>>>
    suspend fun fetchDesigns(): Flow<Resource<List<DesignModel>>>
    suspend fun fetchCategoriesWithSizes(): Flow<Resource<List<CategoriesWithSizesModel>>>
}