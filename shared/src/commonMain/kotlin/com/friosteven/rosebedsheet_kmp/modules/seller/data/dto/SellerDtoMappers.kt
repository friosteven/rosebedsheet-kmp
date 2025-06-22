package com.friosteven.rosebedsheet_kmp.modules.seller.data.dto

import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoriesWithSizesModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.CategoryModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.ColorModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.DesignModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.MaterialModel
import com.friosteven.rosebedsheet_kmp.modules.seller.domain.model.SizeModel

fun ColorDto.toDomain(): ColorModel {
    return ColorModel(
        id = this.id,
        name = this.name,
        hex = this.hex
    )
}

fun CategoryDto.toDomain(): CategoryModel {
    return CategoryModel(
        id = this.id,
        name = this.name,
        type = this.type
    )
}

fun MaterialDto.toDomain(): MaterialModel {
    return MaterialModel(
        id = this.id,
        name = this.name,
        key = this.key
    )
}

fun DesignDto.toDomain(): DesignModel {
    return DesignModel(
        id = this.id,
        name = this.name,
        key = this.key
    )
}

fun SizeDto.toDomain(): SizeModel {
    return SizeModel(
        id = this.id,
        name = this.name,
        width = this.width,
        length = this.length
    )
}

fun CategoriesWithSizesDto.toDomain(): CategoriesWithSizesModel {
    return CategoriesWithSizesModel(
        id = this.id,
        name = this.name,
        type = this.type,
        sizes = this.sizes.map { it.toDomain() }
    )
}