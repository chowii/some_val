# AndroidBase
[![N|Solid](https://media.licdn.com/media/p/2/000/1b0/027/2459ef1.png)](https://nodesource.com/products/nsolid)
# Android Base App
This project is meant to give a well defined direction and structure in the Android development in Sentia.
This document is meant to be constatly updated

The project highly relies on:
  - [Android Architecture components](https://developer.android.com/topic/libraries/architecture/guide.html)
   Of which I recommend the reading, furthemore this project has a brance\h `sample` to show the basic functionality of the architecture.
  In simple words, the guideline is: every UI event should reside in the UI controller (Fragment, Activity)
  The view Model is lifecycle aware and is cached until UI destruction
  For common problems solution (as in Paging and lifecycle aware) rely on the android architecture solutions
  Repository is an abstraction on the source of the data
  Dependency injection use Kodein and allows us to have mocks in the unit Tests
  To test the Ui instead, we have to use Espresso or Robolectric.

## Libraries
  - [Kotlin](https://kotlinlang.org/)
  - [Anko commons](https://github.com/Kotlin/anko/wiki) (logging,dialogs,intents etc.. )
  - [Anko Courotines](https://github.com/Kotlin/anko/wiki/Anko-Coroutines) optional on need
  - [Data binding](https://developer.android.com/topic/libraries/data-binding/index.html)
  - [KodeIn for DI](https://github.com/SalomonBrys/Kodein) 
  - [Retrofit](http://square.github.io/retrofit/)
  -  Rx
  
## Rx features
  -[Rx Android](https://github.com/ReactiveX/RxAndroid) 
  -[Rx Java](https://github.com/ReactiveX/RxJava)
  -[Rx Kotlin](https://github.com/ReactiveX/RxKotlin)
  -[LiveData stream conversion](https://developer.android.com/reference/android/arch/lifecycle/LiveDataReactiveStreams.html)
  -Rx Bindings: optional see commented code in build.gradle

### Favourite inhouse Libraries for common problems
  -[RxGallery](com.github.marchinram:RxGallery) Photo Picker
  -[Glide](https://github.com/bumptech/glide) Image Loading