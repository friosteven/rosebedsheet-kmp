package com.friosteven.rosebedsheet_kmp.modules.seller.domain.model

data class ColorModel(
    val id: Int,
    val name: String,
    val hex: String
)

data class CategoryModel(
    val id: Int,
    val name: String,
    val type: String
)

data class MaterialModel(
    val id: Int,
    val name: String,
    val key: String
)

data class DesignModel(
    val id: Int,
    val name: String,
    val key: String
)

data class CategoriesWithSizesModel(
    val id: Int,
    val name: String,
    val type: String,
    val sizes: List<SizeModel>
)

data class SizeModel(
    val id: Int,
    val name: String,
    val width: Int,
    val length: Int
)