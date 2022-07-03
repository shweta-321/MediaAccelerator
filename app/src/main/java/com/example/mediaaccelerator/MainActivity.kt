package com.example.mediaaccelerator

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.core.content.ContextCompat
import com.example.mediaaccelerator.ui.HeadStoryItem
import com.example.mediaaccelerator.ui.theme.MediaAcceleratorTheme
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.pixFragment
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio

class MainActivity : ComponentActivity() {
    lateinit var viewModel: MainVeiwModel
    val list: ArrayList<Uri> = ArrayList()

    @kotlin.OptIn(ExperimentalMotionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainVeiwModel()
        setContent {
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
            MediaAcceleratorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(contentAlignment = Alignment.TopStart) {
                        HeadStoryItem(
                            state = viewModel.uiControlState.collectAsState().value,
                            onContentChange = {
                             opnePix()
                            }

                        )
                    }
                }
            }
        }
    }

    private fun opnePix() {
        val options = Options().apply {
            ratio = Ratio.RATIO_AUTO                                    //Image/video capture ratio
            count =
                1                                                   //Number of images to restrict selection count
            spanCount = 4                                               //Number for columns in grid
            path =
                "Pix/Camera"                                         //Custom Path For media Storage
            isFrontFacing =
                false                                       //Front Facing camera on start
            videoOptions.videoDurationLimitInSeconds =
                10               //Duration for video recording
            mode =
                Mode.All                                             //Option to select only pictures or videos or both
            flash =
                Flash.Auto                                          //Option to select flash type
            preSelectedUrls = ArrayList<Uri>()                          //Pre selected Image Urls
        }
         val pixFragment = pixFragment(options)
        pixFragment(options){
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> {
                  viewModel.updateData(it.data)
                }
                    PixEventCallback.Status.BACK_PRESSED -> {

                    }
            }
        }.startActivity(pixFragment)
        pixFragment.startActivity(this, options)
    }
}

/*@kotlin.OptIn(ExperimentalFoundationApi::class, ExperimentalMotionApi::class)
@Composable
private fun MyApp() {

    val listState = rememberLazyListState()

    val motionProgress by animateFloatAsState(targetValue = listState.firstVisibleItemIndex.toFloat()) {
        (it + 1) / listState.layoutInfo.totalItemsCount
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentHeight(),

        content = {
            stickyHeader {
                HeadStoryItem(progress = motionProgress.coerceIn(0f, 1f))
            }
        })
}*/

