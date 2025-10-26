# NeuroDose Android - Quick Start Guide

This guide will help you get started with NeuroDose Android development in under 30 minutes.

---

## Prerequisites

### Required Software

1. **Android Studio** (Hedgehog 2023.1.1 or later)
   - Download: https://developer.android.com/studio
   - Includes: Android SDK, Gradle, Emulator

2. **Java Development Kit (JDK)** 17 or later
   - Usually bundled with Android Studio
   - Verify: `java -version`

3. **Git** (for version control)
   - Download: https://git-scm.com/
   - Verify: `git --version`

### Recommended Hardware

- **RAM**: 8GB minimum, 16GB recommended
- **Storage**: 10GB free space for Android SDK and emulator
- **CPU**: Multi-core processor (for emulator performance)

---

## Step 1: Install Android Studio (15 minutes)

### Windows:

1. Download Android Studio from https://developer.android.com/studio
2. Run the installer (.exe file)
3. Follow the setup wizard:
   - Choose "Standard" installation
   - Accept licenses
   - Wait for SDK components to download

### macOS:

1. Download Android Studio (.dmg file)
2. Drag to Applications folder
3. Open Android Studio
4. Follow setup wizard

### Linux:

```bash
# Extract the archive
tar -xzf android-studio-*.tar.gz

# Move to /opt
sudo mv android-studio /opt/

# Run Android Studio
/opt/android-studio/bin/studio.sh
```

### Verify Installation:

1. Open Android Studio
2. Go to **Tools → SDK Manager**
3. Verify these are installed:
   - Android SDK Platform 34 (Android 14)
   - Android SDK Build-Tools
   - Android Emulator
   - Android SDK Platform-Tools

---

## Step 2: Set Up Android Emulator (5 minutes)

### Create Virtual Device:

1. In Android Studio, go to **Tools → Device Manager**
2. Click **Create Device**
3. Select **Phone → Pixel 6** (or similar modern device)
4. Click **Next**
5. Select **System Image**: 
   - Release Name: **UpsideDownCake** (API 34, Android 14)
   - ABI: **x86_64** (faster on most computers)
   - Click **Download** if not already installed
6. Click **Next**, then **Finish**

### Test Emulator:

1. Click the **Play** button next to your virtual device
2. Wait for emulator to boot (1-2 minutes first time)
3. You should see Android home screen

**Tip**: Keep emulator running during development for faster testing.

---

## Step 3: Create NeuroDose Project (5 minutes)

### Option A: Using Android Studio UI

1. Open Android Studio
2. Click **New Project**
3. Select **Empty Activity** (under "Phone and Tablet")
4. Configure project:
   - **Name**: NeuroDose
   - **Package name**: com.neurodose.app
   - **Save location**: Choose your workspace directory
   - **Language**: Kotlin
   - **Minimum SDK**: API 26 (Android 8.0) - covers 87% of devices
   - **Build configuration language**: Kotlin DSL (build.gradle.kts)
5. Click **Finish**
6. Wait for Gradle sync to complete

### Option B: Using Command Line

```bash
# Navigate to your workspace
cd ~/Desktop/NeuroDose

# Android Studio will create the project structure
# Use the UI method above for initial setup
```

---

## Step 4: Configure Dependencies (5 minutes)

### Update `build.gradle.kts` (Module: app)

Replace the dependencies block with:

```kotlin
dependencies {
    // Jetpack Compose BOM (Bill of Materials)
    val composeBom = platform("androidx.compose:compose-bom:2024.01.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Room Database
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Hilt Dependency Injection
    val hiltVersion = "2.50"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
```

### Update `build.gradle.kts` (Module: app) - Plugins

At the top of the file, update plugins:

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    id("com.google.dagger.hilt.android")
}
```

### Update `build.gradle.kts` (Project level)

Add to plugins block:

```kotlin
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
}
```

### Sync Gradle

1. Click **Sync Now** in the banner that appears
2. Wait for Gradle to download dependencies (2-3 minutes)
3. Verify no errors in Build output

---

## Step 5: Run Your First Build (2 minutes)

### Build and Run:

1. Make sure emulator is running (or connect physical device)
2. Click the **Run** button (green play icon) in toolbar
3. Select your device/emulator
4. Wait for app to build and install
5. App should launch showing "Hello Android!"

**Success!** You now have a working Android development environment.

---

## Project Structure Overview

After setup, your project should look like this:

```
NeuroDose/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/neurodose/app/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── (we'll add more here)
│   │   │   ├── res/
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   └── drawable/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts
```

---

## Next Steps

Now that your environment is set up, you're ready to start building NeuroDose!

### Immediate Next Steps:

1. **Review the roadmap**: Read `ROADMAP.md` for detailed development plan
2. **Understand the math**: Read `PHARMACOKINETIC_MODELING.md` for calculation details
3. **Start Phase 1**: Begin implementing the modular architecture

### Phase 1 Tasks (Week 1):

- [ ] Create modular package structure (data/, domain/, ui/, utils/)
- [ ] Set up Hilt Application class
- [ ] Implement Material Design 3 theme
- [ ] Configure navigation graph
- [ ] Create placeholder screens

### Useful Commands:

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Clean build
./gradlew clean

# Check for dependency updates
./gradlew dependencyUpdates
```

---

## Troubleshooting

### Common Issues:

**1. Gradle Sync Failed**
- Solution: Check internet connection, try **File → Invalidate Caches → Restart**

**2. Emulator Won't Start**
- Solution: Enable virtualization in BIOS (VT-x for Intel, AMD-V for AMD)
- Alternative: Use physical Android device with USB debugging enabled

**3. "SDK Location Not Found"**
- Solution: Set `ANDROID_HOME` environment variable to SDK path
  - Windows: `C:\Users\<username>\AppData\Local\Android\Sdk`
  - macOS: `~/Library/Android/sdk`
  - Linux: `~/Android/Sdk`

**4. Build Takes Too Long**
- Solution: Increase Gradle memory in `gradle.properties`:
  ```
  org.gradle.jvmargs=-Xmx4096m
  ```

**5. KSP Plugin Not Found**
- Solution: Make sure KSP version matches Kotlin version (1.9.22)

---

## Development Tips

### Productivity Shortcuts (Android Studio):

- **Ctrl+Space** (Cmd+Space on Mac): Code completion
- **Ctrl+B**: Go to declaration
- **Shift+Shift**: Search everywhere
- **Ctrl+Alt+L**: Reformat code
- **Alt+Enter**: Show quick fixes

### Best Practices:

1. **Keep emulator running**: Faster testing
2. **Use Logcat**: Monitor app logs (View → Tool Windows → Logcat)
3. **Enable auto-import**: Settings → Editor → Auto Import
4. **Use Git**: Commit frequently with meaningful messages
5. **Test on real device**: Emulator doesn't catch all issues

---

## Resources

### Official Documentation:
- Android Developers: https://developer.android.com/
- Jetpack Compose: https://developer.android.com/jetpack/compose
- Kotlin: https://kotlinlang.org/docs/home.html

### Community:
- Stack Overflow: https://stackoverflow.com/questions/tagged/android
- Reddit: r/androiddev
- Kotlin Slack: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up

### Learning:
- Android Basics with Compose: https://developer.android.com/courses/android-basics-compose/course
- Kotlin Koans: https://play.kotlinlang.org/koans/overview

---

## Ready to Code?

You're all set! Your development environment is configured and ready.

**Next command to run:**

```bash
# Open the project in Android Studio
# Then say: "I'm ready to start Phase 1 - let's create the modular architecture!"
```

Happy coding! 🚀

