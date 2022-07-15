package com.example.myapplication.ui.home

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mediaaccelerator.StoryModel
import com.example.mediaaccelerator.states.StoryContentState
import com.example.mediaaccelerator.states.StoryControlState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import wseemann.media.FFmpegMediaMetadataRetriever

class HomeViewModel : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiContentState = MutableStateFlow(StoryContentState.Loading)
    // The UI collects from this StateFlow to get its state updates
    val uiContentState: StateFlow<StoryContentState> get() = _uiContentState

    // Backing property to avoid state updates from other classes
    private val _uiControlState = MutableStateFlow(StoryControlState())
    // The UI collects from this StateFlow to get its state updates
    val uiControlState: StateFlow<StoryControlState> get() = _uiControlState

    fun updateData(list: List<StoryModel>){
        _uiControlState.value.copy(list = list)
    }
    fun getDuration(uri : String): Int {
        val mFFmpegMediaMetadataRetriever: FFmpegMediaMetadataRetriever =
            FFmpegMediaMetadataRetriever()
        mFFmpegMediaMetadataRetriever.setDataSource(uri)
        val mVideoDuration: String =
            mFFmpegMediaMetadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION)
        return mVideoDuration.toInt()
        Log.e("Duration = ", mVideoDuration)
    }
}