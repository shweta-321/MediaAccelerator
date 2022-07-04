package com.example.mediaaccelerator

import com.example.mediaaccelerator.states.StoryControlState
import java.io.Serializable


data class StoryModel(
    val path: String? = null,
    val isVideo :Boolean = false
): Serializable