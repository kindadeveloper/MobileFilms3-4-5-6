package ua.kpi.comsys.iv7101.mobilefilms.ui

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import ua.kpi.comsys.iv7101.mobilefilms.ui.images.ImageSet
import ua.kpi.comsys.iv7101.mobilefilms.ui.movies.Movie
import ua.kpi.comsys.iv7101.mobilefilms.ui.movies.TAG
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

var allMovies = mutableListOf<Movie>()
var allImages = mutableListOf<ImageSet>(ImageSet())

fun <T> importFromJSON(context: Context, classT: Class<T>, fileId: Int): T? {
    var streamReader: InputStreamReader? = null
    var fileInputStream: InputStream? = null
    try {
        fileInputStream = context.resources.openRawResource(fileId)
        streamReader = InputStreamReader(fileInputStream)
        val gson = Gson()
        return gson.fromJson(streamReader, classT)
    } catch (ex: IOException) {
        Log.e(TAG, ex.stackTrace.toString())
        ex.printStackTrace()
    } finally {
        if (streamReader != null) {
            try {
                streamReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (fileInputStream != null) {
            try {
                fileInputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }
    return null
}

fun getResId(resName: String, c: Class<*>): Int {
    val dotIndex = resName.indexOf(".")
    val formattedName = if (dotIndex == -1) resName.toLowerCase(Locale.getDefault()) else resName.substring(0 until dotIndex)
        .toLowerCase(Locale.getDefault())
    return try {
        val idField = c.getDeclaredField(formattedName)
        idField.getInt(idField)
    } catch (e: Exception) {
        e.printStackTrace()
        -1
    }
}

class DataItems {
    var Search: List<Movie> = listOf()
}