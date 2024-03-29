# For WalMart - TMDB API 

## Description 

Android app that utilizes TMDB API & Key ( Key is must due to TMDB policy changes) 

## Covered funtionality 

1. Fetch popular movies 
2. Live Search -  i.e. search as you type 3 or more letters.
3. Detailed page - Added Fav buttom.
4. Fav section displays all your favorites.

Supports both portrait and landscape. 

## Code structure 

MVVM - Based on Android Architecture Components ( Seperated Screens, ViewModels and Models)

 - |
 - |----screens / views]
 - |---- viewmodels
 - |---- model
 - |---- NetworkAPI / Remote 
 - |----  Room DB
 -       |--- DAO
 -       |--- Repository
 -       |---- Database

- |----- utils 
- |----- adapters]
 
 ## Design components used
- [LiveData] 
- [ViewModel]
- [Observrable /  Data binding]
- [Repository for Local & Remote Data]
- [Screens, ViewModel & Model seperation]


## Libraries used

- [OkHttp](https://github.com/square/okhttp) & [Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)
- [Retrofit2](https://github.com/square/retrofit) & [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
- [Picasso](https://github.com/square/picasso) for Image loading 
- [Lifecycle](https://developer.android.com/reference/android/arch/lifecycle/package-summary.html) (ViewModel & LiveData)
- [Room](https://developer.android.com/topic/libraries/architecture/room.html)  
- [DataBinding](https://developer.android.com/topic/libraries/data-binding/index.html)

## Other Support Libraries: 
- [Support Libs] - appcompat, recyclerview, cardview, design
- [DiffUtils] -  android.support.v7.util.DiffUtil;

## Icons & Resources 

- From Android / AOSP 
- From Google Material 
- Android default.
