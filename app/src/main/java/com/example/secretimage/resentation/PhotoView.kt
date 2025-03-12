package com.example.secretimage.resentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secretimage.domain.repository.ImageDataSource
import com.example.secretimage.model.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.secretimage.model.ImageBitmap
import com.example.secretimage.model.toImageBitmap

class PhotoView(private val imageDataSource: ImageDataSource):ViewModel() {

    private val _images = MutableStateFlow<List<ImageBitmap>>(emptyList())
    val images: StateFlow<List<ImageBitmap>> = _images // Expose as immutable StateFlow

    init {
        viewModelScope.launch {
            imageDataSource.getImage().collectLatest { imageList ->
                val bitmapList = imageList.mapNotNull { it.toImageBitmap() }
                _images.value = bitmapList
            }
        }
        }
}