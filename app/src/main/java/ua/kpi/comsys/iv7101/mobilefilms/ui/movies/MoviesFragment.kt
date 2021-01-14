package ua.kpi.comsys.iv7101.mobilefilms.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies.*
import ua.kpi.comsys.iv7101.mobilefilms.R
import ua.kpi.comsys.iv7101.mobilefilms.ui.DataItems
import ua.kpi.comsys.iv7101.mobilefilms.ui.allMovies
import ua.kpi.comsys.iv7101.mobilefilms.ui.importFromJSON

const val selectedMovieIdExtra = "SELECTED_MOVIE_ID"

class MoviesFragment : Fragment() {

    private lateinit var moviesAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movies, container, false)

        allMovies = importFromJSON(root.context, DataItems::class.java, R.raw.movies)?.Search?.toMutableList() ?: mutableListOf()
        moviesAdapter = MovieListAdapter(allMovies){movie -> showFullMovieInfo(movie) }

        val recyclerView: RecyclerView = root.findViewById(R.id.movie_list)
        recyclerView.adapter = moviesAdapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabAddMovie.setOnClickListener {innerView ->
            onFabAddMovieClicked(innerView)
        }

        movie_search.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val recyclerView: RecyclerView = view.findViewById(R.id.movie_list)
                val adapter = recyclerView.adapter as MovieListAdapter
                adapter.filter.filter(newText) {
                    emptyListLabel.visibility = if (it == 0) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
                return false
            }

        })

    }

    override fun onResume() {
        super.onResume()
        moviesAdapter.notifyDataSetChanged()
    }

    private fun showFullMovieInfo(movie: Movie) {
        val intent = Intent(activity?.applicationContext, FullMoviePopUp::class.java)
        intent.putExtra(selectedMovieIdExtra, movie.imdbID)
        startActivity(intent)
    }
    private fun onFabAddMovieClicked (view: View) {
        val intent = Intent(activity?.applicationContext, AddMoviePopUp::class.java)
        startActivity(intent)
    }
}