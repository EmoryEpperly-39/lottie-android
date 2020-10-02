package com.airbnb.lottie.sample.compose.showcase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.ui.tooling.preview.Preview
import com.airbnb.lottie.sample.compose.R
import com.airbnb.lottie.sample.compose.composables.AnimationRow
import com.airbnb.lottie.sample.compose.composables.Loader
import com.airbnb.lottie.sample.compose.composables.LottieComposeScaffoldView
import com.airbnb.lottie.sample.compose.composables.Marquee
import com.airbnb.lottie.sample.compose.findNavController
import com.airbnb.lottie.sample.compose.ui.LottieTheme
import com.airbnb.lottie.sample.compose.utils.mavericksViewModelAndState
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.asMavericksArgs

class ShowcaseFragment : Fragment(), MavericksView {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LottieComposeScaffoldView(requireContext()) {
            ShowcasePage()
        }
    }

    override fun invalidate() {
    }
}


@Composable
fun ShowcasePage() {
    val (_, showcaseState) = mavericksViewModelAndState<ShowcaseViewModel, ShowcaseState>()
    val featuredAnimations = showcaseState.animations
    val scrollState = rememberScrollState()
    val navController = findNavController()
    Stack(
        modifier = Modifier.fillMaxSize()
    ) {
        ScrollableColumn(
            scrollState = scrollState
        ) {
            Marquee("Showcase")
            featuredAnimations()?.data?.forEach { data ->
                AnimationRow(
                    title = data.title,
                    previewUrl = data.preview_url ?: "",
                    previewBackgroundColor = data.bgColor,
                ) {
                    navController.navigate(R.id.player, data.asMavericksArgs())
                }
                Divider(color = Color.LightGray)
            }
        }
        if (featuredAnimations is Uninitialized || featuredAnimations is Loading) {
            Loader(modifier = Modifier.gravity(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LottieTheme {
        ShowcasePage()
    }
}