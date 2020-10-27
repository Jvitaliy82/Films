package ru.jdeveloperapps.filmnews.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.jdeveloperapps.filmnews.R
import ru.jdeveloperapps.filmnews.viewModels.TvShowViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: TvShowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)

        viewModel.mostPopularTVShow.observe(this, Observer {
            Toast.makeText(this, "Total Page: ${it.data?.total}", Toast.LENGTH_LONG).show()
        })

        viewModel.getMostPopularTv()

    }
}