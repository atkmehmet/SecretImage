package com.example.secretimage.resentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secretimage.domain.repository.ImageDataSource
import com.example.secretimage.model.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhotoView(private val imageDataSource: ImageDataSource):ViewModel() {

    private val _images = MutableStateFlow<List<Image>>(emptyList())
    val images: StateFlow<List<Image>> = _images // Expose as immutable StateFlow

    init {
        viewModelScope.launch {
            imageDataSource.getImage().collectLatest { imageList ->
                _images.value = imageList
            }
        }
    }
}