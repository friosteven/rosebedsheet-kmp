package com.friosteven.rosebedsheet_kmp.modules.seller.data.repository

import com.friosteven.rosebedsheet_kmp.core.data.repository.base.Resource
import com.friosteven.rosebedsheet_kmp.core.data.repository.base.serviceCall
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.CategoriesWithSizesDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.CategoryDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.ColorDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.DesignDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.MaterialDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.toDomain
import com.friosteven.rosebedsheet_kmp.modules.seller.data.service.SellerService
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoriesWithSizesModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoryModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.ColorModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.DesignModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.MaterialModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.repository.SellerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SellerRepositoryImpl(private val service: SellerService): SellerRepository {
    override suspend fun fetchColors(): Flow<Resource<List<ColorModel>>> {
        return flowOf(serviceCall {
            service.fetchColors().map { it.toDomain() }
        })
    }

    override suspend fun fetchCategories(): Flow<Resource<List<CategoryModel>>> {
        return flowOf(serviceCall {
            service.fetchCategories().map { it.toDomain() }
        })
    }

    override suspend fun fetchMaterials(): Flow<Resource<List<MaterialModel>>> {
        return flowOf(serviceCall {
            service.fetchMaterials().map { it.toDomain() }
        })
    }

    override suspend fun fetchDesigns(): Flow<Resource<List<DesignModel>>> {
        return flowOf(serviceCall {
            service.fetchDesigns().map { it.toDomain() }
        })
    }

    override suspend fun fetchCategoriesWithSizes(): Flow<Resource<List<CategoriesWithSizesModel>>> {
        return flowOf(serviceCall {
            service.fetchCategoriesWithSizes().map { it.toDomain() }
        })
    }
}