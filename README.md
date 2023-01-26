# OLX Clone

<img src="./demo/olx-clone.jpg" height="500" width="800">

A basic OLX Clone project, integrated with Firebase Authentication, Realtime Database and Storage for registering ads data and images. 

### Developers: 
* [Marcos Vinicius Ferreira](https://github.com/marcosnaofazisso) 

## Goals and Rules:
The main goals of OLX Clone project on Android development was to study how to replicate the functionality of listing ads from Firebase Database. This include the ability to register a new ad, save it in "My Ads" acitivty and fetch new ads registered by any user.

The images were saved in Firebase Storage and the carousel was made by using CarouselView library along with Picasso library.

Additionally, the project aim to replicate the user interface of OLX, as well as its user experience, in order to make the app feel familiar to users. 

# Demo

https://user-images.githubusercontent.com/79977399/214863407-b3c906fd-5b0e-4533-9cd8-43f8f1839b24.mp4


Images of some of the functionalities like listing all ads, ads registered by user and creating a new ad, with photos downloaded from gallery.

# Tutorial
Recommended build tools and SDK versions:

Android Studio IDE: 3.5
Android 8.0 / 9.0
Compile 26
Min SDK 15
Max Android SDK Build-Tools 28 (API 9)

    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:2.0.4'
    implementation 'com.android.support:design:28.0.0'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

    //Firebase dependencies
    implementation 'com.google.firebase:firebase-database:11.6.2'
    implementation 'com.google.firebase:firebase-auth:11.6.2'
    implementation 'com.google.firebase:firebase-core:11.6.2'
    implementation 'com.google.firebase:firebase-storage:11.6.2'

    //CurrencyEditText dependencies
    implementation 'com.github.BlacKCaT27:CurrencyEditText:2.0.2'

    //MaskEditText dependencies
    implementation 'com.github.santalu:mask-edittext:1.0.7'

    //Spots Dialog dependencies
    implementation 'com.github.d-max:spots-dialog:1.1@aar'

    //Picasso dependencies
    implementation 'com.squareup.picasso:picasso:2.71828'

    //CarouselView dependencies
    implementation 'com.synnapps:carouselview:0.1.5'


## Thank you!

