import Foundation
import SwiftUI
import ComposeApp

extension MovieModel: Identifiable {}

struct HomeScreen: View {
    
    @StateObject 
    var viewModel = SharedViewModelStoreOwner<HomeViewModel>()
    
    /*@StateObject*/ 
    var locationManager = LocationManager()
   
    var body: some View {
        NavigationView {
            VStack {
                Observing(viewModel.instance.status) { (result: ResultObject<MoviesState>) in
                    switch (result) {
                    case is ResultObjectLoading<MoviesState>:
                        ProgressView("Loading...")
                            .padding(.top, 10)
                    case is ResultObjectSuccess<MoviesState>:
                        if let moviesState = (result as? ResultObjectSuccess<MoviesState>)?.data {
                            MoviesGrid(movies: moviesState.movies)
                        }
                    case is ResultObjectError<MoviesState>:
                        if let error = (result as? ResultObjectError)?.exception {
                            Text("Error: \(String(describing: error.message))")
                        } else {
                            Text("Error: Unknown error")
                        }
                    case is ResultObjectEmpty<MoviesState>:
                        Text("No movies found")
                    default:
                        Text("Unknown state")
                    }
                }
            }.navigationBarTitle(Text("KmpMovies"))
        }.onAppear {
            locationManager.requestPermission {
                if(locationManager.authorizationStatus == .denied || 
                   locationManager.authorizationStatus == .restricted) {                    
//                    .alert(isPresented: .constant(locationManager.authorizationStatus == .denied || locationManager.authorizationStatus == .restricted)) {
//                        Alert(title: Text("Location Access Denied"),
//                              message: Text("Please enable location access in Settings."),
//                              dismissButton: .default(Text("OK")))
//                    }
                    
                }
                else{
                    viewModel.instance.processIntent(intent: HomeIntent.LoadMovies())
                }
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
}

    struct MoviesGrid: View {
        let movies: [MovieModel]
                
        var body: some View {
            ScrollView {
                let columns = [GridItem(.adaptive(minimum: 100))]
                LazyVGrid(columns: columns, spacing: 16) {
                    ForEach(movies, id:\.id) { movie in
                        NavigationLink(destination: DetailScreen(id: movie.id)){
                            MovieItem(
                                movie: movie
                            )
                        }
                    }
                }
                .padding(.horizontal)
            }
        }
    }
    
    struct MovieItem: View {
        let movie: MovieModel
        // let onMovieClick: (MovieModel) -> Void
        
        var body: some View {
                VStack {
                    GeometryReader { geometry in
                        ZStack(alignment: .topTrailing) {
                            AsyncImage(url: URL(string: movie.posterPath)) { phase in
                                switch phase {
                                case .empty:
                                    ProgressView()
                                        .progressViewStyle(CircularProgressViewStyle())
                                        .frame(width: geometry.size.width, height: geometry.size.height)
                                case .success(let image):
                                    image
                                        .resizable()
                                        .aspectRatio(2 / 3, contentMode: .fill)
                                        .frame(width: geometry.size.width, height: geometry.size.height)
                                        .clipped()
                                        .cornerRadius(8)
                                case .failure:
                                    Image(systemName: "photo")
                                        .resizable()
                                        .aspectRatio(2 / 3, contentMode: .fill)
                                        .frame(width: geometry.size.width, height: geometry.size.height)
                                        .clipped()
                                        .cornerRadius(8)
                                @unknown default:
                                    EmptyView()
                                }
                            }
                            
                            if movie.isFavorite {
                                Image(systemName: "heart.fill")
                                    .foregroundColor(.red)
                                    .padding(5)
                            }
                        }
                    }
                    .aspectRatio(2 / 3, contentMode: .fit)

                    Text(movie.title)
                        .font(.caption)
                        .lineLimit(1)
                        .padding(5)
                }
            }
    }




