package com.example.flexexam.fragments.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.flexexam.R
import com.example.flexexam.databinding.FragmentMovieDetailsBinding
import com.example.flexexam.fragments.BaseFragment


class MovieDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar, getString(R.string.titleDetails), true)
        getMovieDetails()
        initObserve()
    }

    override fun onResume() {
        super.onResume()
        movieDetailsViewModel.isFavorite(movieDetail.id)
    }

    private fun initObserve() {
        movieDetailsViewModel.isFavoriteLiveData.observe(viewLifecycleOwner, Observer { isFavoriteMovie ->
            isFavorite = isFavoriteMovie
            initMenuToolBar(R.menu.menu_details)
        })
    }

    private fun getMovieDetails() {
        arguments?.let {
            movieDetail = MovieDetailsFragmentArgs.fromBundle(it).MovieDetails
            binding.movie = movieDetail
        }
    }
}