package com.example.mediaaccelerator

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.mediaaccelerator.states.StoryContentState
import com.example.mediaaccelerator.states.StoryControlState
import kotlinx.coroutines.flow.*

class MainVeiwModel () : ViewModel() {


    // Backing property to avoid state updates from other classes
    private val _uiContentState = MutableStateFlow(StoryContentState.Loading)
    // The UI collects from this StateFlow to get its state updates
    val uiContentState: StateFlow<StoryContentState> get() = _uiContentState

    // Backing property to avoid state updates from other classes
    private val _uiControlState = MutableStateFlow(StoryControlState())
    // The UI collects from this StateFlow to get its state updates
    val uiControlState: StateFlow<StoryControlState> get() = _uiControlState

    fun updateData(list: List<Uri>){
        _uiControlState.value.copy(list = list)
    }

}