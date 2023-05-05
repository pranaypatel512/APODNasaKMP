package com.example.common.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import com.example.common.domain.model.APODPictureItem
import com.example.common.ui.navigation.rememberViewModel
import com.example.common.utils.AsyncImageLoader

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               onItemSelected: (APODPictureItem) -> Unit = {}){
    val viewModel: HomeScreenViewModel =
        rememberViewModel(HomeScreenViewModel::class) { savedState -> HomeScreenViewModel(savedState) }
    val scrollState = rememberScrollState()

    val homeUiState = viewModel.state.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        if (homeUiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (!homeUiState.error.isNullOrEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Error:\n${homeUiState.error}",
                textAlign = TextAlign.Center
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //region Now Playing Movies
                homeUiState.apodPictureItemList?.let {

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(items = it) { item ->
                            pictureItem(
                                item = item,
                                // isLoading = homeUiState.isLoading,
                                onItemSelected = onItemSelected
                            )
                        }
                    }
                }
                //endregion
            }
        }
    }

}

@Composable
fun pictureItem(item:APODPictureItem,onItemSelected: (APODPictureItem) -> Unit){
    AsyncImageLoader(imageUrl=if(item.isVideo())item.thumbnail_url else item.url,contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
        loadingPlaceHolder = {},
        errorPlaceHolder = {},
        alignment = Alignment.Center,
        alpha = 1.0f,
        coloFilter = null,
        filterQuality = FilterQuality.Medium)
}