package ua.kpi.comsys.iv7101.mobilefilms.ui.movies

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.full_movie.*
import ua.kpi.comsys.iv7101.mobilefilms.R
import ua.kpi.comsys.iv7101.mobilefilms.ui.allMovies
import ua.kpi.comsys.iv7101.mobilefilms.ui.getResId
import ua.kpi.comsys.iv7101.mobilefilms.ui.importFromJSON

class FullMoviePopUp : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_movie)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width: Int = (dm.widthPixels)
        val height: Int = (dm.heightPixels)

        window.setLayout(width, height)

        val selectedMovieId = intent.getStringExtra(selectedMovieIdExtra) ?: ""

        val movieFileId = getResId(selectedMovieId, R.raw::class.java)

        var fullMovie: FullMovie

        fullMovie = if (movieFileId == -1) {
            val movie = allMovies.firstOrNull { it.imdbID == selectedMovieId }
            if (movie != null) FullMovie(movie) else FullMovie()
        } else {
            importFromJSON(application, FullMovie::class.java, movieFileId) ?: FullMovie()
        }

        val posterId = getResId(fullMovie.Poster, R.drawable::class.java)
        full_movie_poster?.setImageResource(if (posterId == -1) R.drawable.ic_round_local_movies_24 else posterId)

        full_movie_title.text = getString(R.string.Title, fullMovie.Title)
        full_movie_year.text = getString(R.string.Year, fullMovie.Year)
        full_movie_genre.text = getString(R.string.Genre, fullMovie.Genre)
        full_movie_director.text = getString(R.string.Director, fullMovie.Director)
        full_movie_actors.text = getString(R.string.Actors, fullMovie.Actors)
        full_movie_country.text = getString(R.string.Country, fullMovie.Country)
        full_movie_language.text = getString(R.string.Language, fullMovie.Language)
        full_movie_production.text = getString(R.string.Production, fullMovie.Production)
        full_movie_released.text = getString(R.string.Released, fullMovie.Released)
        full_movie_runtime.text = getString(R.string.Runtime, fullMovie.Runtime)
        full_movie_awards.text = getString(R.string.Awards, fullMovie.Awards)
        full_movie_rating.text = getString(R.string.Rating, fullMovie.imdbRating)
        full_movie_plot.text = getString(R.string.Plot, fullMovie.Plot)
    }
}