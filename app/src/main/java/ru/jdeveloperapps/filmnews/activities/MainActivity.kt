package ru.jdeveloperapps.filmnews.activities

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import dagger.hilt.android.AndroidEntryPoint
import ru.jdeveloperapps.filmnews.R
import ru.jdeveloperapps.filmnews.adapters.TvShowAdapter
import ru.jdeveloperapps.filmnews.databinding.ActivityMainBinding
import ru.jdeveloperapps.filmnews.other.Resourse
import ru.jdeveloperapps.filmnews.viewModels.TvShowViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: TvShowViewModel
    private val tvShowAdapter = TvShowAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)

        activityMainBinding.tvShowsRecyclerView.apply {
            setHasFixedSize(true)
            tvShowAdapter.setOnClickListener { id, imageURL, imageView ->

                val optons = ActivityOptions.makeSceneTransitionAnimation(this@MainActivity,
                imageView, imageView.transitionName)

                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("id", id)
                    putExtra("image_thumbnail", imageURL)
                }
                startActivity(intent, optons.toBundle())
            }
            adapter = tvShowAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!activityMainBinding.tvShowsRecyclerView.canScrollVertically(1)) {
                        if (viewModel.currentPage <= viewModel.totalAvailablePages) {
                            viewModel.getMostPopularTv()
                        }
                    }
                }
            })
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        viewModel.mostPopularTVLiveData.observe(this, Observer { responce ->
            when (responce) {
                is Resourse.Loading -> {
                    setLoadingState(true)
                }
                is Resourse.Success -> {
                    setLoadingState(false)
                    responce.data?.let { tvSowsResponce ->
                        tvShowAdapter.differ.submitList(tvSowsResponce.tv_shows.toList())
                    }
                }
                is Resourse.Error -> {
                    setLoadingState(false)
                    responce.message?.let { message ->
                        Toast.makeText(this, "On error occured: ${message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

    }

    private fun setLoadingState(visible: Boolean) {
        if (viewModel.currentPage == 1) {
            activityMainBinding.isLoading = visible
        } else {
            activityMainBinding.isLoading = false
            activityMainBinding.isLoadingMore = visible
        }
    }
}