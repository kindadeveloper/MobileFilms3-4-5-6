package ua.kpi.comsys.iv7101.mobilefilms.ui.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.kpi.comsys.iv7101.mobilefilms.R
import ua.kpi.comsys.iv7101.mobilefilms.ui.DataItems
import ua.kpi.comsys.iv7101.mobilefilms.ui.importFromJSON

const val TAG = "MobileFilms_LOG"

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val _movies = MutableLiveData(importFromJSON(getApplication(), DataItems::class.java, R.raw.movies)?.Search ?: listOf())
    val movies: LiveData<List<Movie>> =_movies

    fun add()
    {

    }

}

