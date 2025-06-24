//
//  AppRouter.swift
//  Rose Bedsheet
//
//  Created by John Steven Frio on 6/2/25.
//

import Foundation
import Combine
import SwiftUI


class AppRouter: ObservableObject {
    @Published var sellerRouter = SellerRouter()
    @Published var cartRouter = CartRouter()
    @Published var profileRouter = ProfileRouter()
    
    private var cancellables = Set<AnyCancellable>()

    init() {
        // Merge the objectWillChange publishers from all nested routers
        Publishers.MergeMany(
            sellerRouter.objectWillChange,
            cartRouter.objectWillChange,
            profileRouter.objectWillChange
        )
        .sink { [weak self] _ in
            // This single subscription handles changes from BOTH routers
            self?.objectWillChange.send()
        }
        .store(in: &cancellables)
    }
    
    func reset() {
        sellerRouter.reset()
        cartRouter.reset()
        profileRouter.reset()
    }
    
    
    
//    @Published var path = [AppRouterNavigationDestination]()
//    @Published var currentSheetDestination: AppRouterSheetDestination?
//    @Published var currentFullscreenDestination: AppRouterFullScreenDestination?

//    /// Pushes a new destination onto the navigation stack.
//    func navigate(to destination: AppRouterNavigationDestination) {
//        path.append(destination)
//    }
//
//    /// Pops the latest destination from the navigation stack.
//    func back() {
//        _ = path.popLast()
//    }
//
//    /// Resets the navigation stack to its root.
//    func reset() {
//        path = []
//    }
}

//
///// Defines the destinations that can be pushed onto the navigation stack.
//enum AppRouterNavigationDestination: Hashable, Equatable {
//    case buyer
//    case seller
//
//    struct OnDone<T>: Equatable, Hashable {
//        let id = UUID()
//        let callback: (_ payload: T) -> Void
//
//        static func == (lhs: OnDone<T>, rhs: OnDone<T>) -> Bool {
//            lhs.id == rhs.id
//        }
//
//        func hash(into hasher: inout Hasher) {
//            hasher.combine(id)
//        }
//    }
//}
//
///// Defines the destinations that can be presented as a sheet.
//enum AppRouterSheetDestination: Hashable, Identifiable {
//    case newPost(onDone: OnDone<String>)
//    case notifications
//
//    var id: String {
//        switch self {
//        case .newPost:
//            return "newPost"
//        case .notifications:
//            return "notifications"
//        }
//    }
//
//    struct OnDone<T>: Equatable, Hashable {
//        let id = UUID()
//        let callback: (_ payload: T) -> Void
//
//        static func == (lhs: OnDone<T>, rhs: OnDone<T>) -> Bool {
//            lhs.id == rhs.id
//        }
//
//        func hash(into hasher: inout Hasher) {
//            hasher.combine(id)
//        }
//    }
//}
//
///// Defines the destinations that can be presented as a full-screen cover.
//enum AppRouterFullScreenDestination: Hashable, Identifiable {
//    case onboarding
//    case imagePicker
//    case sellerCreateListing
//    case profile
//
//    var id: String {
//        switch self {
//        case .onboarding:
//            return "onboarding"
//        case .imagePicker:
//            return "imagePicker"
//        case .sellerCreateListing:
//            return "sellerCreateListing"
//        case .profile:
//            return "profile"
//        }
//    }
//
//    struct OnDone<T>: Equatable, Hashable {
//        let id = UUID()
//        let callback: (_ payload: T) -> Void
//
//        static func == (lhs: OnDone<T>, rhs: OnDone<T>) -> Bool {
//            lhs.id == rhs.id
//        }
//
//        func hash(into hasher: inout Hasher) {
//            hasher.combine(id)
//        }
//    }
//}

// MARK: - View Extension for Routing

//extension View {
//    /// Adds navigation destination handling for the `AppRouter`.
//    @ViewBuilder
//    func withAppRouter(router: AppRouter) -> some View {
//        navigationDestination(for: AppRouterNavigationDestination.self) { destination in
//            switch destination {
//            case .buyer:
//                VStack {
//                    Text("")
//                }
//            case .seller:
//                SellerDashboardView()
//                    .toolbar(.hidden, for: .navigationBar)
//            }
//        }
//    }
//
//    /// Adds sheet presentation handling for the `AppRouter`.
//    @ViewBuilder
//    func withAppSheetCover(
//        router: AppRouter,
//        destination: Binding<AppRouterSheetDestination?>
//    ) -> some View {
//        sheet(item: destination) { destination in
//            switch destination {
//            case .newPost:
//                // Replace with your New Post View
//                Text("New Post Sheet")
//            case .notifications:
//                // Replace with your Notifications View
//                Text("Notifications Sheet")
//            }
//        }
//    }

//    /// Adds full-screen cover presentation handling for the `AppRouter`.
//    @ViewBuilder
//    func withAppFullscreenCover(
//        router: AppRouter,
//        destination: Binding<AppRouterFullScreenDestination?>
//    ) -> some View {
//        fullScreenCover(item: destination) { destination in
//            switch destination {
//            case .onboarding:
//                // Replace with your Onboarding View
//                Text("Onboarding Fullscreen")
//            case .imagePicker:
//                // Replace with your Image Picker View
//                Text("Image Picker Fullscreen")
//            case .sellerCreateListing:
//                SellerCreateListingView()
//            case .profile:
//                ProfileView()
//            }
//        }
//    }
//}
