import SwiftUI
import ComposeApp

//Start point on SwiftUI
@main
struct iOSApp: App {
    
    init() {
        AppModuleKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            //ContentView()
            HomeScreen()
        }
    }
}

