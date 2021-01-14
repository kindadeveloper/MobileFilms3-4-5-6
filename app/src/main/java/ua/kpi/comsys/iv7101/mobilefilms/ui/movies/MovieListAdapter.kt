package ua.kpi.comsys.iv7101.mobilefilms.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.iv7101.mobilefilms.R
import ua.kpi.comsys.iv7101.mobilefilms.ui.getResId
import java.util.*

class MovieListAdapter(private var list: MutableList<Movie>,  private val clickListener: (Movie) -> Unit)
    : RecyclerView.Adapter<MovieViewHolder>(), Filterable {

    private val listFull: List<Movie> = list.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Movie = list[position]
        holder.bind(movie)
        val deleteButton: AppCompatImageButton = holder.itemView.findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
        }
        holder.itemView.setOnClickListener { clickListener(movie) }
    }

    override fun getItemCount(): Int = list.size

    override fun getFilter(): Filter {
        return movieFilter
    }

    private val movieFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()

            results.values = if (constraint == null || constraint.isEmpty()) {
                listFull.toList()
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                listFull.filter { it.Title.toLowerCase(Locale.getDefault()).trim().contains(filterPattern) }
            }

            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            list = results?.values as MutableList<Movie>
            notifyDataSetChanged()
        }

    }
}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_movie, parent, false)){

    private var titleView: TextView? = null
    private var yearView: TextView? = null
    private var typeView: TextView? = null
    private var posterView: ImageView? = null
    init {
        titleView = itemView.findViewById(R.id.list_movie_title)
        yearView = itemView.findViewById(R.id.list_movie_year)
        typeView = itemView.findViewById(R.id.list_movie_type)
        posterView = itemView.findViewById(R.id.list_movie_poster)
    }

    fun bind(movie: Movie) {
        titleView?.text = movie.Title
        yearView?.text = movie.Year
        typeView?.text = movie.Type
        val posterId = getResId(movie.Poster, R.drawable::class.java)
        posterView?.setImageResource(if (posterId == -1) R.drawable.ic_round_local_movies_24 else posterId)
    }
}

