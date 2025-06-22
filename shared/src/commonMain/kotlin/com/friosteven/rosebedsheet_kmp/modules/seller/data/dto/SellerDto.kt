package com.friosteven.rosebedsheet_kmp.modules.seller.data.dto

import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoriesWithSizesModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoryModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.ColorModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.DesignModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.MaterialModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.SizeModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ColorDto(
    val id: Int,
    val name: String,
    @SerialName("hex")
    val hex: String
)

@Serializable
data class CategoryDto(
    val id: Int,
    val name: String,
    val type: String
)


@Serializable
data class MaterialDto(
    val id: Int,
    val name: String,
    val key: String
)

@Serializable
data class DesignDto(
    val id: Int,
    val name: String,
    val key: String
)

@Serializable
data class SizeDto(
    val id: Int,
    val name: String,
    val width: Int,
    val length: Int
)

@Serializable
data class CategoriesWithSizesDto(
    val id: Int,
    val name: String,
    val type: String,
    val sizes: List<SizeDto>
)

