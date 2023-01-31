package com.example.youtubeapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.youtubeapi.adapters.YoutubeAdapter
import com.example.youtubeapi.databinding.ActivityMainBinding
import com.example.youtubeapi.utils.NetworkHelper
import com.example.youtubeapi.utils.YoutubeResource
import com.example.youtubeapi.utils.hide
import com.example.youtubeapi.utils.show
import com.example.youtubeapi.viewmodels.ViewModelFactory
import com.example.youtubeapi.viewmodels.YoutubeViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityMainBinding
    private lateinit var youtubeViewModel: YoutubeViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var job: Job
    private val TAG = "MainActivity"
    private lateinit var youtubeAdapter: YoutubeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()
        networkHelper = NetworkHelper(this)
        youtubeViewModel = ViewModelProvider(this, ViewModelFactory(networkHelper))[YoutubeViewModel::class.java]

        youtubeAdapter = YoutubeAdapter(object : YoutubeAdapter.OnItemClickListener {
            override fun onItemClick(videoId: String) {
                val intent = Intent(this@MainActivity, YoutubeActivity::class.java)
                intent.putExtra("video_id", videoId)
                startActivity(intent)
            }
        })
        binding.rv.adapter = youtubeAdapter
        binding.progress.show()

        launch {
            youtubeViewModel.getStateVideos()
                .collect {
                        when(it) {
                            is YoutubeResource.Loading -> {
                                binding.progress.show()
                            }
                            is YoutubeResource.Error -> {
                                binding.progress.hide()
                            }
                            is YoutubeResource.Success -> {
                                binding.progress.hide()
                                youtubeAdapter.submitList(it.youtubeData.items)
                            }
                        }

                }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}