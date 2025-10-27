package com.neurodose.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * NeuroDoseApp is the main Application class for the NeuroDose app.
 * 
 * The @HiltAndroidApp annotation triggers Hilt's code generation, which creates
 * the dependency injection container for the entire application.
 * 
 * This must be declared in AndroidManifest.xml as the application class.
 */
@HiltAndroidApp
class NeuroDoseApp : Application()

