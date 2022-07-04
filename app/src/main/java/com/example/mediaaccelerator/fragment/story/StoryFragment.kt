package com.example.mediaaccelerator.fragment.story

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.example.mediaaccelerator.R
import com.example.mediaaccelerator.StoryModel
import com.example.mediaaccelerator.states.StoryContentState
import com.example.mediaaccelerator.ui.Stories
import com.example.myapplication.ui.home.HomeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File

@OptIn(ExperimentalMotionApi::class)
class StoryFragment : Fragment() {
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val listOfImages = arguments?.getSerializable("data") as ArrayList<StoryModel>

                Stories(numberOfPages = listOfImages.size,
                    onEveryStoryChange = { position ->
                        Log.i("DATA", "Story Change $position")
                    },
                    onComplete = {
                        Log.i("Action", "Completed")
                    }) { index ->
                    Image(painter = rememberImagePainter(File(listOfImages[index].path)), contentDescription = null,
                        contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = HomeViewModel()
        viewModel.uiContentState.onEach { onContentChange(it) }.launchIn(lifecycleScope)
    }

    private fun onContentChange(contentState: StoryContentState) {
        when (contentState) {
            else -> Unit
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}