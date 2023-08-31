package com.example.flexexam.fragments.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.flexexam.R
import com.example.flexexam.adapters.MovieListAdapter
import com.example.flexexam.data.MovieUiState
import com.example.flexexam.databinding.FragmentMoviesBinding
import com.example.flexexam.enums.MovieType
import com.example.flexexam.fragments.BaseFragment
import kotlinx.coroutines.launch


class MoviesFragment : BaseFragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var movieAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar, getString(R.string.titlePopularMovies))
        initMenuToolBar(R.menu.menu_movie)
        initObserve()
        movieViewModel.getMovies(MovieType.Popular)
    }

    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.movieLiveData.collect { uiState ->
                    when(uiState) {
                        is MovieUiState.Success -> {
                            val movies = uiState.movies
                            movieAdapter = MovieListAdapter(
                                requireContext(),
                                movies,
                                onItemClick = { selectedProduct ->
                                    val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(selectedProduct)
                                    findNavController().navigate(action)
                                }
                            )
                            binding.recyclerViewMovie.adapter = movieAdapter
                        }
                        is MovieUiState.Error -> {
                            Toast.makeText(context, "Error: ${uiState.exception}", Toast.LENGTH_LONG).show()
                        }
                        is MovieUiState.Loading -> {
                            if (uiState.isLoad)
                                showCustomProgressDialog()
                            else
                                hideProgressDialog()
                        }
                    }
                }
            }
        }
    }
}