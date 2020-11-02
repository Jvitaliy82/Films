package ru.jdeveloperapps.filmnews.activities

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import ru.jdeveloperapps.filmnews.R
import ru.jdeveloperapps.filmnews.adapters.EpisodeAdapter
import ru.jdeveloperapps.filmnews.adapters.ImageSliderAdapter
import ru.jdeveloperapps.filmnews.databinding.ActivityDetailBinding
import ru.jdeveloperapps.filmnews.databinding.LayoutEpisodesBottomSheetBinding
import ru.jdeveloperapps.filmnews.other.Resourse
import ru.jdeveloperapps.filmnews.viewModels.TvShowViewModel

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var episodesBottomSheetDialog: BottomSheetDialog
    private lateinit var layoutEpisodesBottomSheetBinding: LayoutEpisodesBottomSheetBinding
    private lateinit var viewModel: TvShowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        doInitialization()
    }

    private fun doInitialization() {
        viewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)
        val id = intent.getStringExtra("id")
        imageBack.setOnClickListener {
            onBackPressed()
        }

        buttonWebsite.setOnClickListener {
            activityDetailBinding.tvShow?.let {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(it.url))
                startActivity(intent)
            }
        }

        buttonEpisodes.setOnClickListener {
            activityDetailBinding.tvShow?.let {
                activityDetailBinding.tvShow?.let {
                    episodesBottomSheetDialog = BottomSheetDialog(this)
                    layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(this),
                        R.layout.layout_episodes_bottom_sheet,
                        findViewById(R.id.episodeContainer),
                        false
                    )
                    episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.root)
                    layoutEpisodesBottomSheetBinding.recyclerEpisodes.adapter = EpisodeAdapter(it.episodes)
                    layoutEpisodesBottomSheetBinding.textTitle.text = String.format("Episodes | %s",
                     it.name)
                    layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener {
                        episodesBottomSheetDialog.dismiss()
                    }
                }
                val frameLayout: FrameLayout? = episodesBottomSheetDialog.findViewById(
                    com.google.android.material.R.id.design_bottom_sheet
                )
                frameLayout?.let {
                    val bottomSheetBehavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(it)
                    bottomSheetBehavior.setPeekHeight(Resources.getSystem().displayMetrics.heightPixels)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
                episodesBottomSheetDialog.show()
            }
        }

        viewModel.showDetailLiveData.observe(this, { response ->
            when (response) {
                is Resourse.Loading -> {
                    setLoadingState(true)
                }
                is Resourse.Success -> {
                    setLoadingState(false)
                    response.data?.let { tvShowsResponse ->
                        loadImagesSlider(tvShowsResponse.tvShow.pictures)
                        activityDetailBinding.tvShow = tvShowsResponse.tvShow
                    }
                }
                is Resourse.Error -> {
                    setLoadingState(false)
                    response.message?.let { message ->
                        Toast.makeText(this, "On error occured: ${message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

        activityDetailBinding.textReadMore.setOnClickListener {
            if (activityDetailBinding.textReadMore.text.equals("Read More")) {
                activityDetailBinding.textDescription.maxLines = Integer.MAX_VALUE
                activityDetailBinding.textDescription.ellipsize = null
                activityDetailBinding.textReadMore.text = getString(R.string.read_less)
            } else {
                activityDetailBinding.textDescription.maxLines = 4
                activityDetailBinding.textDescription.ellipsize = TextUtils.TruncateAt.END
                activityDetailBinding.textReadMore.text = getString(R.string.read_more)
            }
        }

        id?.let {
            viewModel.getDetail(it)
        }
    }

    private fun setLoadingState(visible: Boolean) {
        activityDetailBinding.isLoading = visible
    }

    private fun loadImagesSlider(slideImages: List<String>) {
        activityDetailBinding.sliderViewPager.offscreenPageLimit = 1
        activityDetailBinding.sliderViewPager.adapter = ImageSliderAdapter(slideImages)
        activityDetailBinding.sliderViewPager.visibility = View.VISIBLE
        activityDetailBinding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicator(slideImages.size)
        activityDetailBinding.sliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
    }

    private fun setupSliderIndicator(count: Int) {
        val indicators: Array<ImageView?> = arrayOfNulls(count)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageResource(R.drawable.background_slider_indicator_inactive)
            indicators[i]?.layoutParams = layoutParams
            activityDetailBinding.layoutSliderIndicator.addView(indicators[i])
        }
        activityDetailBinding.layoutSliderIndicator.visibility = View.VISIBLE
        setCurrentSliderIndicator(0)
    }

    private fun setCurrentSliderIndicator(position: Int) {
        val childCount = activityDetailBinding.layoutSliderIndicator.childCount
        for (i in 0..childCount) {
            val imageView =
                activityDetailBinding.layoutSliderIndicator.getChildAt(i)
            if (position == i) {
                if (imageView is ImageView) {
                    imageView.setImageResource(R.drawable.background_slider_indicator_active)
                }
            } else {
                if (imageView is ImageView) {
                    imageView.setImageResource(R.drawable.background_slider_indicator_inactive)
                }
            }
        }
    }
}