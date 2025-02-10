package com.example.secretimage

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.example.secretimage.ui.theme.SecretImageTheme
import android.Manifest
import android.app.Activity
import androidx.activity.viewModels
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!hasRequiredPermissions()){
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS,0
            )
        }
        setContent {
            SecretImageTheme {

              val scope = rememberCoroutineScope()
              val scaffoldState = rememberBottomSheetScaffoldState()
              val controller = remember {
                  LifecycleCameraController(applicationContext).apply {
                      setEnabledUseCases(
                          CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE
                      )
                  }
              }
                val viewModel = viewModel<MainViewModel>()
                val bitmaps by viewModel.bitmaps.collectAsState()

                BottomSheetScaffold(scaffoldState = scaffoldState, sheetPeekHeight = 0.dp   ,sheetContent ={
                    PhotoBottomSheetContent(bitmaps = bitmaps,
                        modifier = Modifier.fillMaxWidth()
                    )
                   }
                ) {padding->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                    ){
                        CameraPreview(controller = controller,
                            modifier = Modifier.fillMaxSize()
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            
                        }
                    }
                    
                }
            }
        }
    }



    fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                applicationContext, // Use "this" because the function is inside Context
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }

}

