package com.example.mediaaccelerator

import com.example.mediaaccelerator.states.StoryControlState
import java.io.Serializable
import java.time.Duration


data class StoryModel(
    val path: String? = null,
    val isVideo :Boolean = false,
    val duration: Int = 20_000
): Serializable