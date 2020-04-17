package gl51

import gl51.data.MovieRequest
import gl51.movie.data.Movie
import gl51.movie.service.MovieClient
import gl51.movie.service.impl.MovieClientImpl
import gl51.movie.service.impl.MovieRegistryImpl
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.test.annotation.MockBean
import io.reactivex.Flowable
import spock.lang.AutoCleanup
import spock.lang.Specification
import spock.lang.Shared
import javax.inject.Inject
@MicronautTest
class MovieControllerSpec extends Specification {
    @Shared @Inject
    EmbeddedServer embeddedServer
    @Shared @AutoCleanup @Inject @Client("/")
    RxHttpClient client

    @Inject
    MovieRegistryImpl registry

    void "testIndex"() {
        given:
        Flowable flowable = client.retrieve(HttpRequest.GET("/movie"), Argument.listOf(Movie))
        def content = flowable.firstElement()
        expect:
        content.blockingGet() == []
    }
    void "testFilmCreation"() {
        given:
        HttpResponse response = client.toBlocking().exchange(
                HttpRequest.POST("/movie", new MovieRequest(imdbID: "lhy"))
        )
        expect:
        response.status == HttpStatus.CREATED
        registry.listFavorites().find{ it.title == 'best movie' }
        registry.listFavorites().size() == 1
    }

    @MockBean(MovieClientImpl)
    MovieClient movieClient() {
        def mock = Mock(MovieClient)
        mock.getMovieDetail("lhy") >> new Movie(imdbID: "lhy", title: "best movie")
        mock
    }
}
