package com.example.secretimage.resentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ImageScreen(photoView: PhotoView = viewModel()) {
    val imageList = photoView.images.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageList.forEach { imageBitmap ->
            Image(
                bitmap = imageBitmap.image.asImageBitmap(),
                contentDescription = imageBitmap.name,
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )
        }
    }
}
