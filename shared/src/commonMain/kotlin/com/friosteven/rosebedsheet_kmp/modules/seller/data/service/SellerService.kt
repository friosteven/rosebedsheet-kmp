package com.friosteven.rosebedsheet_kmp.modules.seller.data.service

import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.CategoriesWithSizesDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.CategoryDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.ColorDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.DesignDto
import com.friosteven.rosebedsheet_kmp.modules.seller.data.dto.MaterialDto
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.parameter

class SellerService(private val httpClient: HttpClient) {
    suspend fun fetchColors(): List<ColorDto> {
        return httpClient.get(urlString = "/rest/v1/colors") {
            parameter("select", "*")
        }.body()
    }

    suspend fun fetchCategories(): List<CategoryDto> {
        return httpClient.get(urlString = "/rest/v1/categories") {
            parameter("select", "*")
        }.body()
    }

    suspend fun fetchMaterials(): List<MaterialDto> {
        return httpClient.get(urlString = "/rest/v1/materials") {
            parameter("select", "*")
        }.body()
    }

    suspend fun fetchDesigns(): List<DesignDto> {
        return httpClient.get(urlString = "/rest/v1/designs") {
            parameter("select", "*")
        }.body()
    }

    suspend fun fetchCategoriesWithSizes(): List<CategoriesWithSizesDto> {
        return httpClient.get(urlString = "/rest/v1/rpc/get_categories_with_sizes").body()
    }


}