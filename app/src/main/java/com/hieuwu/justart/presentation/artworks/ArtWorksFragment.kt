package com.hieuwu.justart.presentation.artworks

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Slide
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.hieuwu.justart.BuildConfig
import com.hieuwu.justart.R
import com.hieuwu.justart.databinding.FragmentArtWorksBinding
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import com.hieuwu.justart.presentation.views.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class ArtWorksFragment : Fragment() {

    @Inject
    lateinit var retrieveArtWorksUseCase: RetrieveArtWorksUseCase
    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    private lateinit var binding: FragmentArtWorksBinding

    private lateinit var viewModel: ArtWorksViewModel

    private var recyclerviewAdapter: ArtWorksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupExitTransition()
        setupReEnterTransition()
    }

    private fun setupExitTransition() {
        exitTransition = transitionTogether {
            duration = LARGE_EXPAND_DURATION / 2
            interpolator = FAST_OUT_LINEAR_IN
            // The app bar.
            this += Slide(Gravity.TOP).apply {
                mode = Slide.MODE_OUT
                addTarget(R.id.app_bar)
            }
            // The grid items.
            this += Explode().apply {
                mode = Explode.MODE_OUT
                excludeTarget(R.id.app_bar, true)
            }
        }
    }

    private fun setupReEnterTransition() {
        reenterTransition = transitionTogether {
            duration = LARGE_COLLAPSE_DURATION / 2
            interpolator = LINEAR_OUT_SLOW_IN
            // The app bar.
            this += Slide(Gravity.TOP).apply {
                mode = Slide.MODE_IN
                addTarget(R.id.app_bar)
            }
            // The grid items.
            this += Explode().apply {
                // The grid items should start imploding after the app bar is in.
                startDelay = LARGE_COLLAPSE_DURATION / 2
                mode = Explode.MODE_IN
                excludeTarget(R.id.app_bar, true)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        recyclerviewAdapter?.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtWorksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        val viewModelFactory = ArtWorksViewModelFactory(retrieveArtWorksUseCase)
        viewModel = ViewModelProvider(this, viewModelFactory)[ArtWorksViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView(binding.artWorksRecyclerView)
        if (savedInstanceState != null) {
            recyclerviewAdapter?.restoreInstanceState(savedInstanceState)
        }
        if (recyclerviewAdapter!!.expectsTransition) {
            postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
        }
        setupWindowListener(view)
    }

    private fun setupWindowListener(view: View) {
        val gridPadding = resources.getDimensionPixelSize(R.dimen.spacing_tiny)
        ViewCompat.setOnApplyWindowInsetsListener(view.parent as View) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.updateLayoutParams<AppBarLayout.LayoutParams> {
                topMargin = systemBars.top
            }
            binding.artWorksRecyclerView.updatePadding(
                left = gridPadding + systemBars.left,
                right = gridPadding + systemBars.right,
                bottom = gridPadding + systemBars.bottom
            )
            insets
        }

        binding.artWorksRecyclerView.addItemDecoration(
            SpaceDecoration(resources.getDimensionPixelSize(R.dimen.spacing_tiny))
        )
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerviewAdapter =
            ArtWorksAdapter(onReadyToTransition = { startPostponedEnterTransition() },
                onClickListener = ArtWorksAdapter.OnClickListener(
                    clickListener = { viewModel.displayPropertyDetails(it) },
                    shareListener = {
                        shareContent(it)
                        Timber.d("Share click")
                    },
                    favouriteListener = { Timber.d("Favourite click") },
                    pinListener = { Timber.d("Pin click") }
                ))
        with(recyclerView) {
            adapter = recyclerviewAdapter
        }
    }

    private fun shareContent(artwork: ArtWorkDo) {
        buildImage(artwork)
    }

    private fun buildShareContent(artwork: ArtWorkDo): String {
        return "${artwork.title}, ${artwork.dimensions}\n${artwork.artistDisplay}, Art Institute of Chicago\n\nShared from " +
                "Just Art by @hieuwu, @dohonghuan"
    }

    private fun buildImage(artwork: ArtWorkDo) {
        val file = File(requireContext().externalCacheDir, File.separator + "artwork.jpg")
        val fout = FileOutputStream(file)
        var bitmap: Bitmap? = null
        var photoUri: Uri? = null
        coroutineScope.launch {
            bitmap = getBitmapFromURL(artwork.imageUrl)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fout)
            fout.flush()
            fout.close()
            file.setReadable(true, false)
            photoUri = FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )

            val intent = Intent().apply {
                action = ACTION_SEND
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                putExtra(Intent.EXTRA_TEXT, buildShareContent(artwork))

                type = "image/jpg"
                putExtra(Intent.EXTRA_STREAM, photoUri)
            }
            with(Dispatchers.Main) {
                startActivity(Intent.createChooser(intent, "Share artwork"));
            }
        }
    }

    private suspend fun getBitmapFromURL(src: String?): Bitmap? {
        var res: Bitmap? = null
        try {
            val url = URL(src)
            val connection =
                url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            res = BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

        return res
    }
}