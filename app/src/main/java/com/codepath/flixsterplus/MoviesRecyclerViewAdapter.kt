package com.codepath.flixsterplus

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MoviesRecyclerViewAdapter(
    private val movies: List<Movie>,
    private val mListener : OnListFragmentInteractionListener?
    )
    : RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesRecyclerViewAdapter.MovieViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.now_playing_movie_fragment, parent, false)
            return MovieViewHolder(view)
        }

        inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView){
            var mItem: Movie? = null
            val mMovieTitle: TextView = mView.findViewById<View>(R.id.movie_title) as TextView
            val mMovieDesc: TextView = mView.findViewById<View>(R.id.movie_description) as TextView
            val mImage: ImageView = mView.findViewById<View>(R.id.imageView) as ImageView

            override fun toString(): String {
                return mMovieTitle.toString() + " '" + mMovieDesc.text + "'"
            }
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movies[position]

            holder.mItem = movie
            holder.mMovieTitle.text = movie.title
            holder.mMovieDesc.text = movie.description
            Glide.with(holder.mView)
                .load("https://image.tmdb.org/t/p/w500/"+movie.movieImageUrl)
                .centerInside()
                .into(holder.mImage)

            holder.mView.setOnClickListener {
                holder.mItem?.let { movie ->
                    mListener?.onItemClick(movie)
                }
            }
        }

        override fun getItemCount(): Int {
            return movies.size
        }
    }
