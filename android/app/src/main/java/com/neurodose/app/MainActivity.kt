package com.neurodose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.neurodose.app.ui.navigation.NeuroDoseNavGraph
import com.neurodose.app.ui.theme.NeuroDoseTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity is the entry point of the NeuroDose app.
 *
 * @AndroidEntryPoint tells Hilt to inject dependencies into this activity.
 * This allows us to use @Inject to get dependencies throughout the activity.
 *
 * The activity sets up:
 * - Jetpack Compose for the UI
 * - Material Design 3 theme
 * - Navigation graph for screen navigation
 * - Edge-to-edge display (uses full screen including system bars)
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeuroDoseTheme {
                NeuroDoseApp()
            }
        }
    }
}

/**
 * NeuroDoseApp is the root composable for the app.
 *
 * This composable sets up:
 * - The navigation controller
 * - The navigation graph
 * - The surface (background)
 */
@Composable
fun NeuroDoseApp() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NeuroDoseNavGraph(navController)
    }
}