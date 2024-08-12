import Foundation
import CoreLocation

class LocationManager: NSObject, ObservableObject, CLLocationManagerDelegate {
    
    private let locationManager = CLLocationManager()
    
    private var callback : Optional<() -> Void> = nil
    
    @Published var authorizationStatus: CLAuthorizationStatus = .notDetermined
    
    override init() {
        super.init()
        self.locationManager.delegate = self
        self.authorizationStatus = locationManager.authorizationStatus
    }
    
    func requestPermission(completion: @escaping () -> Void) {
        if(authorizationStatus != .notDetermined){
            completion()
        } else {
            callback = completion
            locationManager.requestWhenInUseAuthorization()
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        DispatchQueue.main.async {
            self.authorizationStatus = status
            if let callback = self.callback {
                if(self.authorizationStatus != .notDetermined){
                    callback()
                    self.callback = nil
                }
                else {
                    self.handleAuthorizationStatusChange()
                }
            }
        }
    }
    
    private func handleAuthorizationStatusChange() {
            switch authorizationStatus {
            case .authorizedAlways, .authorizedWhenInUse:
                print("Location access granted")
                callback?()
                callback = nil
            case .denied, .restricted:
                print("Location access denied or restricted")
                // Aquí puedes manejar el caso de acceso denegado o restringido
                callback = nil
            case .notDetermined:
                print("Location access not determined")
                // No hacer nada, se está esperando una decisión del usuario
            @unknown default:
                print("Unknown authorization status")
                // Manejar cualquier estado nuevo que pueda ser agregado en el futuro
            }
        }
}
