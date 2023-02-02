# <p align="center"> News App :newspaper: </p>

<!-- Video -->
<div align="center">
  <video src="https://user-images.githubusercontent.com/79931228/216049783-b277d02b-69f4-491b-8c59-87e5c4455b3f.mp4"/>
</div>

<!-- Screenshots -->
## 📸 Screenshots
<p align="center">
  <img src="https://user-images.githubusercontent.com/79931228/216050808-19097692-8979-4530-bfb4-6356e6d9c067.png"/>
  <img src="https://user-images.githubusercontent.com/79931228/216050814-9caa32e1-cb3c-4141-bbb0-40f132187e53.png"/> <br>
  <img src="https://user-images.githubusercontent.com/79931228/216050818-6940543b-6085-4760-8623-2878a73cfbbd.png"/> <br>
  <img src="https://user-images.githubusercontent.com/79931228/216050820-02221ba0-63b8-4909-967c-6b9202bbb7ce.png"/> 
  <img src="https://user-images.githubusercontent.com/79931228/216050804-c60560ea-5aa8-4ac8-abdf-f823b48043c0.png"/>
</p>

<!-- Technologies -->
## :point_down: Structures Used
- MVVM
- Navigation
- Dependency Injection | Hilt
- Coroutine
- Retrofit
- Room
- ViewBinding
- Glide
- Parcelable

## :pencil2: Dependency
```
    // Navigation Components
    implementation "androidx.navigation:navigation-fragment-ktx:2.5.3"
    implementation "androidx.navigation:navigation-ui-ktx:2.5.3"

    // Room
    implementation "androidx.room:room-runtime:2.5.0"
    kapt  "androidx.room:room-compiler:2.5.0"
    implementation "androidx.room:room-ktx:2.5.0"

    // Coroutine
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'

    // Coroutine Lifecycle Scope
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation "com.squareup.okhttp3:logging-interceptor:4.5.0"

    // Glide
    implementation 'com.github.bumptech.glide:glide:4.14.2'
    kapt  'com.github.bumptech.glide:compiler:4.14.2'

    // DI
    implementation "com.google.dagger:hilt-android:2.44"
    kapt "com.google.dagger:hilt-compiler:2.44"
```

app build.gradle:

```
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'androidx.navigation.safeargs.kotlin'
    id 'kotlin-kapt'
    id 'com.google.dagger.hilt.android'
    id 'kotlin-parcelize'
}

buildFeatures {
     viewBinding true
 }
```
project build.gradle:

```
plugins {
    id 'com.android.application' version '7.4.0' apply false
    id 'com.android.library' version '7.4.0' apply false
    id 'org.jetbrains.kotlin.android' version '1.7.21' apply false
    id 'androidx.navigation.safeargs' version '2.5.3' apply false
    id 'com.google.dagger.hilt.android' version '2.44' apply false
}
```

<!-- Manifest File -->
## :exclamation: Manifest File
```
<uses-permission android:name="android.permission.INTERNET" />
```

<!-- API -->
## :point_down: API
https://newsapi.org/

