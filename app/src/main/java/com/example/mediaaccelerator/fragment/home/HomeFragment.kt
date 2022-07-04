package com.example.mediaaccelerator.fragment.home

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mediaaccelerator.R
import com.example.mediaaccelerator.StoryModel
import com.example.mediaaccelerator.states.StoryContentState
import com.example.mediaaccelerator.ui.HeadStoryItem
import com.example.myapplication.ui.home.HomeViewModel
import io.ak1.pix.helpers.PixBus
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import io.ak1.pix.utility.ARG_PARAM_PIX
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalMotionApi::class)
class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
    private var dialogFragment: DialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                HeadStoryItem(
                    onContentChange = ::onContentChange
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        viewModel.uiContentState.onEach { onContentChange(it) }.launchIn(lifecycleScope)

    }

    private fun onContentChange(contentState: StoryContentState) {
        when (contentState) {

            StoryContentState.OnClickAddNewStory -> OnClickAddNewStory()
            else -> Unit
        }
    }

    private fun OnClickAddNewStory() {
        val options = Options().apply {
            ratio = Ratio.RATIO_AUTO                                    //Image/video capture ratio
            count =
                5                                                   //Number of images to restrict selection count
            spanCount = 4                                               //Number for columns in grid
            path =
                "Pix/Camera"                                         //Custom Path For media Storage
            isFrontFacing =
                false                                       //Front Facing camera on start
            videoOptions.videoDurationLimitInSeconds =
                10                            //Duration for video recording
            mode =
                Mode.All                                             //Option to select only pictures or videos or both
            flash =
                Flash.Auto                                          //Option to select flash type
            preSelectedUrls = ArrayList<Uri>()                          //Pre selected Image Urls
        }

        var bundle = bundleOf(ARG_PARAM_PIX to options)
        findNavController().navigate(R.id.CameraFragment, bundle)
        PixBus.results {
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> {
                    val list = arrayListOf<StoryModel>()
                    it.data.map {
                        list.add(
                            StoryModel(
                                path = getRealPathFromURI(it),
                                isVideo = it.path?.contains("mp.4") == true
                            )
                        )
                    }

                    val bundle = Bundle()
                    bundle.putSerializable("data", list)
                    requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                            .navigate(R.id.navigation_story, bundle)

                }
                PixEventCallback.Status.BACK_PRESSED -> requireActivity().supportFragmentManager.popBackStack()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? = requireActivity().getContentResolver().query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
}