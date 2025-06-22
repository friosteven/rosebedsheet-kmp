package com.friosteven.rosebedsheet_kmp.di

import appModule
import com.friosteven.rosebedsheet_kmp.modules.seller.presentation.SellerPresenter
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.stopKoin

// This object acts as a bridge for dependency injection.
object DIHelper : KoinComponent {
    fun setupKoin() {

        startKoin {
            modules(appModule()) // Use the same module defined in commonMain
        }
    }

    val sellerPresenter: SellerPresenter by inject()

}