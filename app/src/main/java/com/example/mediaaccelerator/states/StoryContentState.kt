package com.example.mediaaccelerator.states

sealed interface StoryContentState{
    object OnClickAddNewStory : StoryContentState
    object Loading : StoryContentState
}