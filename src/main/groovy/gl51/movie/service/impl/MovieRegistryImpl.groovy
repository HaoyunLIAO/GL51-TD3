package gl51.movie.service.impl

import gl51.movie.data.Movie
import gl51.movie.service.MovieClient
import gl51.movie.service.MovieRegistry

import javax.inject.Inject
import javax.inject.Singleton

// Shift + ctl + T => créer un test
@Singleton
class MovieRegistryImpl implements MovieRegistry {

    private List<Movie> internalRegistry = []
    @Inject
    private MovieClient movieClient
    @Override
    void addMovieToFavorites(String ID) {
        internalRegistry << movieClient.getMovieDetail(ID)
    }

    @Override
    List<Movie> listFavorites() {
        internalRegistry
    }
}
