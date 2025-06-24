//
//  Testnav.swift
//  Rose Bedsheet
//
//  Created by John Steven Frio on 6/12/25.
//

import Foundation
import SwiftUI

///In summary, while this pattern provides ultimate flexibility by letting you navigate to any
///view without pre-defining routes, it comes at the cost of performance, type safety, and
/// makes essential features like deep linking nearly impossible to implement cleanly. For most complex applications, a type-safe, data-driven approach using enums is generally the more robust and maintainable solution.

// MARK: - 1. Navigation Target (The AnyView Wrapper)
/// This struct is the key to the new pattern. It wraps any View into an AnyView
/// and gives it a stable, hashable identity using a UUID. This allows our
/// NavigationPath to store a collection of different views.
struct NavigationTarget: Hashable {
    let id: UUID
    let view: AnyView

    // A custom initializer to hide the AnyView conversion.
    init<V: View>(_ view: V) {
        self.id = UUID()
        self.view = AnyView(view)
    }

    // We only hash the unique ID.
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }

    // We only compare the unique ID.
    static func == (lhs: NavigationTarget, rhs: NavigationTarget) -> Bool {
        lhs.id == rhs.id
    }
}

// MARK: - 2. Generic Router
/// The Router class is now simpler. Its navigate function takes a View,
/// wraps it in our NavigationTarget, and appends it to the path.
final class Router: ObservableObject {
    @Published var path = NavigationPath()

    /// Wraps any SwiftUI View in a NavigationTarget and appends it to the path.
    /// - Parameter view: The view instance to navigate to.
    func navigate<V: View>(to view: V) {
        path.append(NavigationTarget(view))
    }

    /// Navigates back to the previous view in the stack.
    func back() {
        if !path.isEmpty {
            path.removeLast()
        }
    }

    /// Resets the navigation stack, returning to the root view.
    func popToRoot() {
        path.removeLast(path.count)
    }
}

// MARK: - 3. View Destination Builder
/// We now only need ONE navigation destination modifier. It listens for our
/// NavigationTarget and simply displays the AnyView contained within it.
extension View {
    func withRouterDestinations() -> some View {
        self.navigationDestination(for: NavigationTarget.self) { target in
            target.view
        }
    }
}


// MARK: - 4. Sample Views
/// The views now directly call the router with the concrete view they want to navigate to.

struct RootView: View {
    @EnvironmentObject private var router: Router

    var body: some View {
        List {
            Section("Product Flow") {
                Button("Navigate to Product Detail (P123)") {
                    // Directly instantiate and pass the destination view
                    router.navigate(to: ProductDetailViewTest(productId: "P123"))
                }
                Button("Navigate to Create Listing") {
                    router.navigate(to: CreateListingView(onDone: {
                        print("Listing Created!")
                    }))
                }
            }

            Section("Settings Flow") {
                Button("Navigate to Profile") {
                    router.navigate(to: ProfileViewTest())
                }
                Button("Navigate to Notifications") {
                    router.navigate(to: NotificationsView())
                }
            }
            
            Section("Actions") {
                Button("Pop to Root") {
                    router.popToRoot()
                }
                .disabled(router.path.isEmpty)
            }
        }
        .navigationTitle("AnyView Navigation")
    }
}


struct ProductDetailViewTest: View {
    @EnvironmentObject private var router: Router
    let productId: String

    var body: some View {
        VStack(spacing: 20) {
            Text("Product Detail for \(productId)")
                .font(.largeTitle)
            Button("View Reviews for this Product") {
                // Navigate to another view instance
                router.navigate(to: ReviewsView(productId: productId))
            }
            Button("Go Back") {
                router.back()
            }
        }
        .navigationTitle("Product \(productId)")
        .navigationBarTitleDisplayMode(.inline)
    }
}


struct CreateListingView: View {
    @EnvironmentObject private var router: Router
    let onDone: () -> Void

    var body: some View {
        VStack(spacing: 20) {
            Text("Create New Listing")
                .font(.largeTitle)
            Button("Finish and Go Back") {
                onDone()
                // The view itself is responsible for dismissal
                router.back()
            }
        }
        .navigationTitle("New Listing")
        .navigationBarBackButtonHidden()
    }
}

struct ReviewsView: View {
    let productId: String
    var body: some View {
        Text("Reviews for \(productId)")
            .navigationTitle("Reviews")
    }
}

struct ProfileViewTest: View {
    @EnvironmentObject private var router: Router
    var body: some View {
        VStack(spacing: 20) {
            Text("User Profile")
                .font(.largeTitle)
            Button("View a Product (P456)") {
                router.navigate(to: ProductDetailViewTest(productId: "P456"))
            }
        }
        .navigationTitle("Profile")
    }
}

struct NotificationsView: View {
    var body: some View {
        Text("Notification Settings")
            .navigationTitle("Notifications")
    }
}

struct PrivacyView: View {
    @EnvironmentObject private var router: Router
    var body: some View {
        VStack(spacing: 20) {
            Text("Privacy Settings")
                .navigationTitle("Privacy")
            Button("Go to Notifications (Deep Link)") {
                router.popToRoot()
                router.navigate(to: NotificationsView())
            }
        }
    }
}

//
//// MARK: - 5. Main App Entry Point
//@main
//struct AnyViewRouterApp: App {
//    @StateObject private var router = Router()
//
//    var body: some Scene {
//        WindowGroup {
//            NavigationStack(path: $router.path) {
//                RootView()
//                    // Apply our single, generic destination modifier.
//                    .withRouterDestinations()
//            }
//            .environmentObject(router)
//        }
//    }
//}
