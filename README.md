### Crypto Tracker

A general cryptocurrency tracking application written in Kotlin for the Android platform.[Nomics](https://p.nomics.com/cryptocurrency-bitcoin-api) were used to pull the data.Authentication and a comment system for user communication.User is able to find out various informations about the chosen currencies.

**Features**

-Create new account / Sign-in

-Turkish/English language selection

-Informations about the trending crypto currencies

-Graph data about the chosen currency including the last 30 days

-A commenting system for users to communicate 


**Used Libraries**

Retrofit

Firebase(Firestore,Authentication)

MpAndroidChart

Glide

Gson

---

*Installation*

Enable view binding in your app level gradle file.

```kotlin
buildFeatures{
    viewBinding true
}
```

*Dependencies*

In your project level gradle,add the following code;

```kotlin
classpath 'com.google.gms:google-services:4.3.10'
```

And add this in your app level gradle;

```kotlin
plugins {
    id 'com.google.gms.google-services'
}
implementation 'com.google.firebase:firebase-database-ktx:20.0.1'
implementation 'com.google.firebase:firebase-firestore-ktx:23.0.3'
implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
implementation 'com.google.code.gson:gson:2.8.7'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.github.bumptech.glide:glide:4.12.0'
annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
implementation platform('com.google.firebase:firebase-bom:28.3.1')
implementation 'com.google.firebase:firebase-analytics-ktx'
implementation 'com.google.firebase:firebase-auth-ktx'
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
```

You can find information about Firebase configuration [here](https://firebase.google.com/docs/android/setup).To get your API key you need to go to  [Nomics](https://p.nomics.com/cryptocurrency-bitcoin-api) and follow instructions.

---

**Author**

[altanmehmet](https://github.com/altanmehmet)


