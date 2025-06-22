import SwiftUI
import shared
import KMPNativeCoroutinesAsync

@main
struct iOSApp: App {
    init() {
        DIHelper.shared.setupKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

