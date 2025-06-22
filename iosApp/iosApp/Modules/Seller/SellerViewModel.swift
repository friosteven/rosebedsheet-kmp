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


@MainActor
class SellerViewModel: ObservableObject {
    
    @Injected(\.sellerPresenter) private var presenter: SellerPresenter
    @Published private(set) var isLoading: Bool = false
    @Published private(set) var colors: [ColorModel] = []
    @Published private(set) var error: String? = nil

    private var observationTasks = [Task<Void, Never>]()

    init() {
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
    
    func fetchColors() {
        presenter.fetchColors()
    }
    
    
    //TODO: MAKE GENERIC
    private func observeAllStates() {
        
        let colorsTask = Task {
            do {
                let stream = asyncSequence(for: presenter.colorsFlow)
                for try await colorList in stream {
                    self.colors = colorList
                }
            } catch {
                print("🛑 Failed to observe colors: \(error.localizedDescription)")
            }
        }
        observationTasks.append(colorsTask)
        
        let loadingTask = Task {
            do {
                let stream = asyncSequence(for: presenter.isLoadingColorsFlow)
                for try await loadingState in stream {
                    self.isLoading = loadingState.boolValue
                }
            } catch {
                print("🛑 Failed to observe loading state: \(error.localizedDescription)")
            }
        }
        observationTasks.append(loadingTask)

        let errorTask = Task {
            do {
                let stream = asyncSequence(for: presenter.colorErrorFlow)
                for try await errorMessage in stream {
                    self.error = errorMessage
                }
            } catch {
                print("🛑 Failed to observe error state: \(error.localizedDescription)")
            }
        }
        observationTasks.append(errorTask)
    }
}

