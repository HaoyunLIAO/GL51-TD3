package gl51

import gl51.data.MovieRequest
import gl51.movie.data.Movie
import gl51.movie.service.MovieRegistry
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Post

import javax.inject.Inject


@Controller("/movie")
class MovieController {
    @Inject
    MovieRegistry registry

    @Get("/")
    List<Movie> index() {
        []
    }

    @Post('/')
    HttpStatus addMovie(MovieRequest movieRequest) {
        registry.addMovieToFavorites(movieRequest.getImdbID())
        HttpStatus.CREATED
    }
}