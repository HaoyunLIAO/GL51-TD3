package gl51.movie.service.impl
import gl51.movie.data.Movie
import gl51.movie.service.MovieClient
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class MovieRegistryImplTest extends Specification {

    @Inject MovieRegistryImpl registry

    void "injectionShouldWork"() {
        expect:
        registry != null
    }

    void "favoritesShouldBeEmpty"() {
        expect:
        registry.listFavorites() == []
    }

    void "addingaFavoritesShouldFillInTheDatabase"() {
        when:
        registry.addMovieToFavorites("aaaa")
        then:
        registry.listFavorites().size() == 1
        registry.listFavorites().find({ it.title == 'my movie' })

    }

    @MockBean(MovieClientImpl)
    MovieClient client() {
        def mock = Mock(MovieClient)
        mock.getMovieDetail("aaaa") >> new Movie(imdbID: "aaaa", title: "my movie")
        mock
    }
}