# Android Architecture Components Demo

Simple demo project to test [Android Jetpack][jetpack] and [Architecture Components][arch], using [Deezer Open API][deezer-api] as a REST/JSON provider.

## Features

![User Info Screen](art/user_info_capture.gif)

### Current

* General architecture as per [Google Arch Components][arch] best pratices.
* Retieves user info and all favorites albums for the user. User is currently defined in gradle.properties, and only albums are displayed so far, in a not so pretty manner.
* All retrieved data is stored locally, and silently synced on each request, for a more reactive UI.
* Navigation to album details when an album is clicked/tapped in the album list, and some infor about the album are displayed.

### Planned (ordered)
* Display albums track list and prettify album details display.
* Loading feedback to avoid blank screen on startup.
* More intelligent data fetch from network. Currently, data is always fetched from local db then network no matter what. Some rate limiter (every 10 minutes or so could be a good idea)
* Network errors/issues management and retry.
* Better synchronization for user favorite albums, so that we can only load displayed data (infinite list loading)
* Ability to change selected user, by searching users through the existing REST endpoint.
* Make the UI more responsive based on device form factor.

### Challenges
* Inifinite list loading and user albums synchronization. Not too challenging unless we want to add a sort order different from Open API default order (items are ordered by id asc by default in Deezer API and existing sort orders does not seem effective so far).
* Entities rearchitecture will be necessary, as per Deezer Open API structure : all nested lists are encapsulated in a **_data_** or **_error_** object, meaning that a naive retrofit implementation as currently designed is not very optimal (we're currently exposing _technical_ objects in entities).
* Better entity encapsulation in VM with the use of _**Transformations**_.

## Technical info and dependencies

### Common

* It's plain old java. I know it. Next app will be using Kotlin. Some code have been ported from Kotlin, I figured this was the best way to dive into Kotlin syntax without being too painful. Well, that was quite painful at times anyway (Kotlin is more powerful and less verbose), but it was very instructive as well ;)

* Data Fetch/Sync architecture as per Google Arch Components recommandations
![Data access architecture schema](art/data_sync_architecture.png)

### Jetpack and Architecture components

* [Androidx][androidx] to replace support-libs
* [Android Architecture Components][arch]
   * [LiveData][live-data] Notify views when underlying database changes
   * [Data Binding][data-binding] Declaratively bind observable data to UI elements
   * [ViewModel][viewmodel] Manage UI-related data in a lifecycle-conscious way
   * [Navigation][navigation] Handle everything needed for in-app navigation, along with SafeArgs.
   * [Room][room] Fluent SQLite database access
   
### External libraries
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication and automatic JSON parsing (through [gson][gson])
* [Glide][glide] for image loading
* [Gson-Flatten][flatten] for simple nested objects flattening when parsing JSON

## Inspiration
* [GithubBrowser][google-github-sample] sample from Google Samples was a great help to discover Android Arch Compononents. Yes, it's Kotlin and I'm using Java here. This was a great opportunity not only to understand better Kotlin, but also (and more importantly), to understand the code I was reusing, as a mere copy/paste has little interest.

[deezer-api]: https://developers.deezer.com/api
[jetpack]: https://developer.android.com/jetpack/androidx
[androidx]: https://developer.android.com/jetpack/androidx
[arch]: https://developer.android.com/arch
[live-data]: https://developer.android.com/topic/libraries/architecture/livedata
[data-binding]: https://developer.android.com/topic/libraries/data-binding/
[viewmodel]: https://developer.android.com/topic/libraries/architecture/viewmodel
[navigation]: https://developer.android.com/topic/libraries/architecture/navigation.html
[room]: https://developer.android.com/topic/libraries/architecture/room
[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[gson]: https://github.com/google/gson
[glide]: https://github.com/bumptech/glide
[flatten]: https://github.com/Tishka17/gson-flatten
[google-github-sample]: https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample
