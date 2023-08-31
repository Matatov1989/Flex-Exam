package com.example.flexexam.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flexexam.R
import com.example.flexexam.model.Movie
import com.example.flexexam.util.Constants.POSTER_IMG_URL

class MovieListAdapter (
    private val context: Context,
    private val movies: List<Movie>,
    private val onItemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListAdapter.MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.MovieViewHolder, position: Int) {
        Glide
            .with(holder.itemView)
            .load("${POSTER_IMG_URL}${movies[position].posterPath}")
            .centerCrop()
            .placeholder(R.drawable.ic_android)
            .into(holder.imageViewPoster)

        holder.textViewTitle.text = movies[position].title
        holder.textViewAvg.text = context.getString(R.string.strRating, movies[position].voteAverage)
        holder.textViewLanguage.text = context.getString(R.string.strLanguage, movies[position].originalLanguage)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(movies[position])
        }
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewLanguage: TextView = itemView.findViewById(R.id.textViewLanguage)
        val textViewAvg: TextView = itemView.findViewById(R.id.textViewAvg)
    }
}