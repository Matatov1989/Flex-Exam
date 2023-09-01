package com.example.flexexam.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.flexexam.R
import com.example.flexexam.enums.MovieType
import com.example.flexexam.fragments.detail.MovieDetailsViewModel
import com.example.flexexam.fragments.movie.MoviesViewModel
import com.example.flexexam.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment(){

    lateinit var movieViewModel: MoviesViewModel
    lateinit var movieDetailsViewModel: MovieDetailsViewModel

    private lateinit var progressDialog: Dialog
    var isFavorite: Boolean = false

    lateinit var movieDetail: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        movieDetailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        progressDialog = Dialog(requireContext())

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this.isEnabled = false
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    fun initToolbar(toolbar: Toolbar? = null, title: String? = null, isBackButton: Boolean = false) {

        toolbar?.let { (activity as AppCompatActivity).setSupportActionBar(it) }
        title?.let { (activity as AppCompatActivity).supportActionBar?.title = it }

        if (isBackButton) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

            toolbar?.setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    fun initMenuToolBar(menuFragment: Int) {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(menuFragment, menu)

                if (menuFragment == R.menu.menu_details) {
                    if (isFavorite)
                        menu.getItem(0).icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_star)
                    else
                        menu.getItem(0).icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_border)
                }
            }

            @SuppressLint("NonConstantResourceId")
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.actionFilterPopular -> {
                        movieViewModel.fetchMovies(MovieType.Popular)
                        initToolbar(title = getString(R.string.titlePopularMovies))
                    }
                    R.id.actionFilterCurrentlyAiring -> {
                        movieViewModel.fetchMovies(MovieType.PlayingNow)
                        initToolbar(title = getString(R.string.titlePlayingNowMovies))
                    }
                    R.id.actionFilterFavorites -> {
                        movieViewModel.fetchMovies(MovieType.Favorite)
                        initToolbar(title = getString(R.string.titleFavoriteMovies))
                    }
                    R.id.actionFavorite -> {
                        if (isFavorite) {
                            movieDetailsViewModel.removeFavorite(movieDetail.id)
                            menuItem.icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_star_border)
                            Toast.makeText(activity, getString(R.string.toastRemoveFromFavorite), Toast.LENGTH_LONG).show()
                        } else {
                            movieDetailsViewModel.insertFavorite(movieDetail)
                            menuItem.icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_star)
                            Toast.makeText(activity, getString(R.string.toastAddToFavorite), Toast.LENGTH_LONG).show()
                        }
                    }
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    fun showCustomProgressDialog() {
        progressDialog.setContentView(R.layout.dialog_custom_progress)
        progressDialog.show()
    }
}