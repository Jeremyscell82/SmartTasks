package com.lloydsbyte.smarttasks.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lloydsbyte.smarttasks.MainActivity
import com.lloydsbyte.smarttasks.R
import com.lloydsbyte.smarttasks.ui.MainWallpaper
import com.lloydsbyte.smarttasks.ui.theme.PreviewWithTheme
import com.lloydsbyte.smarttasks.ui.theme.SmartTasksTheme
import kotlinx.coroutines.delay
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
class SplashScreen: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        
        enableEdgeToEdge()
        setContent {
            SmartTasksTheme {
                MainWallpaper()
                SplashScreen()
            }
        }
    }

    @PreviewWithTheme
    @Composable
    fun PreviewSplashScreen() {
        SmartTasksTheme {
            MainWallpaper()
            SplashScreen()
        }
    }

    @SuppressLint("NotConstructor")
    @Composable
    fun SplashScreen() {
        
        var showLogo by remember {
            mutableStateOf(false)
        }
        
        var showNotePad by remember {
            mutableStateOf(false)
        }
        
        LaunchedEffect(key1 = Unit) {
            delay(600)
            showLogo = true
        }
        
        LaunchedEffect(key1 = Unit) {
            delay(3000)
            launchApp()
        }

        Box(modifier = Modifier.fillMaxSize()){

            AnimatedVisibility(
                visible = showLogo,
                enter = slideInVertically(
                    animationSpec = tween(durationMillis = 600, delayMillis = 300),
                    initialOffsetY = { fullHeight -> -fullHeight }
                ),
                exit = fadeOut()) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)){

                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.logo), contentDescription = "App Image")
                }
                showNotePad = true
            }

            AnimatedVisibility(
                visible = showNotePad,
                enter = slideInVertically(
                    animationSpec = tween(durationMillis = 600, delayMillis = 800),
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = fadeOut()) {
                Box(modifier = Modifier
                    .fillMaxSize()){

                    Image(
                        modifier = Modifier
                            .width(440.dp)
                            .align(Alignment.BottomCenter)
                            .padding(12.dp, 0.dp)
                            .aspectRatio(1f),
                        painter = painterResource(id = R.drawable.intro_illustration), contentDescription = "App Image")
                }
            }
        }


        
    }
    
    
    fun launchApp() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
    
}