import Foundation
import SwiftUI
import ComposeApp

struct DetailScreen: View {
       
    //var viewModel: DetailViewModel
    @StateObject var viewModel: SharedViewModelStoreOwner<DetailViewModel>
    
    init(id: Int32) {
        self._viewModel = StateObject(
            wrappedValue: SharedViewModelStoreOwner(DetailViewModel(id: id))
        )
    }
    
    var body: some View {
        VStack{
            Observing(viewModel.instance.movie) { (result: ResultObject<MovieModel>) in
                switch result {
                    case is ResultObjectLoading<MovieModel>:
                        ProgressView("Loading...")
                            .padding(.top, 10)
                    case is ResultObjectSuccess<MovieModel>:
                        if let moviesState = (result as? ResultObjectSuccess<MovieModel>)?.data {
                            MovieDetail(
                                movie: moviesState,
                                onFavoriteClick: {
                                    viewModel.instance.setFavorite(movieModel: moviesState)
                                }
                            )
                        }
                    case is ResultObjectError<MovieModel>:
                        if let error = (result as? ResultObjectError)?.exception {
                            Text("Error: \(String(describing: error.message))")
                        } else {
                            Text("Error: Unknown error")
                        }
                    case is ResultObjectEmpty<MovieModel>:
                        Text("No movies found")
                    default:
                        Text("Unknown state")
                    }
            }
        }
    }
    
    struct MovieDetail: View {
        var movie: MovieModel
        var onFavoriteClick: () -> Void
        
        var body: some View {
            ScrollView {
                VStack(alignment: .leading) {
                    if let backdrop = movie.backdropPath {
                        AsyncImage(url: URL(string: backdrop)) { image in
                            image
                                .resizable()
                                .frame(maxWidth: .infinity)
                        } placeholder: {
                            ProgressView()
                        }
                        .aspectRatio(16 / 9, contentMode: .fill)
                        .frame(maxHeight: 200)
                        .clipped()
                    }
                    Text(movie.overview)
                        .padding()
                    
                    VStack(alignment: .leading, spacing: 8) {
                        Text("**Original language**: \(movie.originalLanguage)")
                            .frame(maxWidth: .infinity, alignment: .leading)
                        Text("**Original title**: \(movie.originalTitle)")
                            .frame(maxWidth: .infinity, alignment: .leading)
                        Text("**Release date**: \(movie.releaseDate)")
                            .frame(maxWidth: .infinity, alignment: .leading)
                        Text("**Popularity**: \(movie.popularity)")
                            .frame(maxWidth: .infinity, alignment: .leading)
                        Text("**Vote average**: \(movie.voteAverage)")
                            .frame(maxWidth: .infinity, alignment: .leading)
                    }
                    .padding()
                    .background(Color.secondary.opacity(0.1))
                    .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .leading)
                    .cornerRadius(8)
                }
            }
            .navigationTitle(movie.title)
            .navigationBarItems(trailing: Button(action: onFavoriteClick) {
                Image(systemName: movie.isFavorite ? "heart.fill" : "heart")
            })
        }
    }
}
