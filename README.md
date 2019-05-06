# Android Architecture Components Demo

Simple demo project to test [Android Jetpack][jetpack] and [Architecture Components][arch], using [Deezer Open API][deezer-api] as a REST/JSON provider.

### Jetpack and Architecture components

* [Androidx][androidx] to replace support-libs
* [Android Architecture Components][arch]
   * [LiveData][live-data] Notify views when underlying database changes
   * [Data Binding][data-binding] Declaratively bind observable data to UI elements
   * [ViewModel][viewmodel] Manage UI-related data in a lifecycle-conscious way
   * [Navigation][navigation] Handle everything needed for in-app navigation
   * [Room][room] Fluent SQLite database access
   
### External libraries
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication and automatic JSON parsing (through [gson][gson])
* [Glide][glide] for image loading

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
