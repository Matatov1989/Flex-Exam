package com.example.flexexam.adapters

import android.content.Context
import android.nfc.Tag
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flexexam.R
import com.example.flexexam.model.Movie
import com.example.flexexam.util.Constants.POSTER_IMG_URL

class MovieListAdapter(
    diffCallback: DiffUtil.ItemCallback<Movie>,
    private val context: Context,
    private val onItemClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)

        movie?.let {
            Glide
                .with(holder.itemView)
                .load("${POSTER_IMG_URL}${it.posterPath}")
                .centerInside()
                .placeholder(R.drawable.ic_android)
                .into(holder.imageViewPoster)

            holder.textViewTitle.text = it.title
            holder.textViewAvg.text = context.getString(R.string.strRating, it.voteAverage)
            holder.textViewLanguage.text = context.getString(R.string.strLanguage, it.originalLanguage)

            holder.itemView.tag = it
        }
    }

    class MovieViewHolder(itemView: View, private val onItemClick: (Movie) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewLanguage: TextView = itemView.findViewById(R.id.textViewLanguage)
        val textViewAvg: TextView = itemView.findViewById(R.id.textViewAvg)

        init {
            itemView.setOnClickListener { onItemClick.invoke(it.tag as Movie) }
        }
    }
}