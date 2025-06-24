//
//  SellerViewModel.swift
//  iosApp
//
//  Created by John Steven Frio on 6/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMPNativeCoroutinesAsync
import KMPNativeCoroutinesCore

extension ColorModel: Identifiable {}

extension CategoryModel: Identifiable {} // This fixes your current error

extension SizeModel: Identifiable {}

extension MaterialModel: Identifiable {}

extension DesignModel: Identifiable {}

extension CategoriesWithSizesModel: Identifiable {}


@MainActor
class SellerViewModel: ObservableObject {
    
    private var presenter: SellerPresenter
    
    @Published private(set) var isLoading: Bool = false
    @Published private(set) var error: String? = nil
    
    @Published private(set) var colorsModel: [ColorModel] = []
    @Published private(set) var categoriesModel: [CategoryModel] = []
    @Published private(set) var materialsModel: [MaterialModel] = []
    @Published private(set) var designsModel: [DesignModel] = []
    @Published private(set) var categoriesWithSizesModel: [CategoriesWithSizesModel] = []
    
    @Published var sizesBasedOnSelectedCategory: [SizeModel] = []
    
    private var observationTasks = [Task<Void, Never>]()
    
    init() {
        self.presenter = DIHelper.shared.getSellerPresenter()
        observeAllStates()
    }
    
    /**
     * Because coroutines can live forever, This method MUST be called
     * to cancel all running coroutines and prevent memory leaks.
     */
    func clearPresenter() {
        presenter.onCleared()
        observationTasks.forEach { $0.cancel() }
    }
    
    func fetchAll() {
        presenter.fetchColors()
        presenter.fetchDesigns()
        presenter.fetchMaterials()
        presenter.fetchCategories()
        presenter.fetchCategoriesWithSizes()
        
    }
    
    func fetchColors() {
        presenter.fetchColors()
        
    }
    
    func observeAllStates() {
        let loadingTask = Task {
            do {
                let stream = asyncSequence(for: presenter.isLoadingFlow)
                for try await loadingState in stream {
                    self.isLoading = loadingState.boolValue
                }
            } catch is CancellationError {
                // Expected cancellation on disappear
            } catch {
                print("🛑 Failed to observe loading state: \(error.localizedDescription)")
            }
        }
        observationTasks.append(loadingTask)
        
        let errorTask = Task {
            do {
                let stream = asyncSequence(for: presenter.errorFlow)
                for try await errorMessage in stream {
                    self.error = errorMessage
                }
            } catch is CancellationError {
                // Expected cancellation on disappear
            } catch {
                print("🛑 Failed to observe error state: \(error.localizedDescription)")
            }
        }
        observationTasks.append(errorTask)
        
        
        // MARK: - Colors Observation
        
        let colorsTask = Task {
            do {
                let stream = asyncSequence(for: presenter.colorsFlow)
                for try await data in stream {
                    self.colorsModel = data
                }
            } catch is CancellationError {
                // Expected cancellation on disappear
            } catch {
                print("🛑 Failed to observe colors: \(error.localizedDescription)")
            }
        }
        observationTasks.append(colorsTask)
        
        
        // MARK: - Categories Observation
        
        let categoriesTask = Task {
            do {
                let stream = asyncSequence(for: presenter.categoriesFlow)
                for try await data in stream {
                    self.categoriesModel = data
                }
            } catch is CancellationError {
                // Expected cancellation on disappear
            } catch {
                print("🛑 Failed to observe categories: \(error.localizedDescription)")
            }
        }
        observationTasks.append(categoriesTask)
        
        // MARK: - Materials Observation
        
        let materialsTask = Task {
            do {
                let stream = asyncSequence(for: presenter.materialsFlow)
                for try await data in stream {
                    self.materialsModel = data
                }
            } catch is CancellationError {
                // Expected cancellation on disappear
            } catch {
                print("🛑 Failed to observe materials: \(error.localizedDescription)")
            }
        }
        observationTasks.append(materialsTask)
        
        // MARK: - Designs Observation
        
        let designsTask = Task {
            do {
                let stream = asyncSequence(for: presenter.designsFlow)
                for try await data in stream {
                    self.designsModel = data
                }
            } catch is CancellationError {
                // Expected cancellation on disappear
            } catch {
                print("🛑 Failed to observe designs: \(error.localizedDescription)")
            }
        }
        observationTasks.append(designsTask)
        
        // MARK: - CategoriesWithSizes Observation
        
        let categoriesWithSizesTask = Task {
            do {
                let stream = asyncSequence(for: presenter.categoriesWithSizesFlow)
                for try await data in stream {
                    self.categoriesWithSizesModel = data
                }
            } catch is CancellationError {
                // Expected cancellation on disappear
            } catch {
                print("🛑 Failed to observe categoriesWithSizes: \(error.localizedDescription)")
            }
        }
        observationTasks.append(categoriesWithSizesTask)
        
        let sizesBasedOnSelectedCategoryTask = Task {
            do {
                let stream = asyncSequence(for: presenter.sizesBasedOnSelectedCategoryFlow)
                for try await data in stream {
                    self.sizesBasedOnSelectedCategory = data
                }
            } catch is CancellationError {
                // Expected cancellation on disappear
            } catch {
                print("🛑 Failed to observe categoriesWithSizes: \(error.localizedDescription)")
            }
        }
        observationTasks.append(sizesBasedOnSelectedCategoryTask)
    }
    
    
    func setSizesBasedOnSelectedCategory(selectedCategory: CategoriesWithSizesModel) {
        presenter.setSizesBasedOnSelectedCategory(selectedCategory: selectedCategory)
    }
}


