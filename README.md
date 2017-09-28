# Whogoes

[![CI Status](https://circleci.com/gh/diegolmendonca/whogoes.svg?style=shield&circle-token=:circle-token)](https://circleci.com/gh/diegolmendonca/whogoes)

Whogoes is a solution that helps you finding potential people who are either attending or interested or have declined an specific facebook event.
Usually, for big events, Facebook  hides the search bar which allows us to query by name. Imagine an event with 20k interested people. The only option to search for someone is to scroll the interested list until the end and then perform a browser-based search. This will probably take ages, if your browser does not freeze before the operation is completed.


# The feature
Whogoes is supposed to be very trivial. It works in 3 steps:

  - Screen 1 : Type an event name
  - Screen 2 : Select the exact event
  - Screen 3 : Type a person name
  - That is it , you will see on the fly everyone who matches the name you typed.

# Constraints
The event must be visible to you and searchable. Not all events have this configuration.
Whogoes completely relies on Facebook API. Sometimes, it responds lightinig fast, sometimes not. Please bear in mind that very big events may take a while to iterate along all the interested/attending/declined list.
For instance, an event with 100k people is taking between 3-5 minutes. Please be patient!

# Technical aspects

Please read this section if you are interested in the technical details about this project.
Note that this is an open source project, please feel free to contribute.

This app was born because I wanted to learn new technologies. That is it. In the end of day, I thought someone could take advantage of this solution. Hence, it has been published to Play Store.
Technologies/Frameworks I wanted to learn and exercise:

* [Kotlin] - https://kotlinlang.org/
* [Dagger] - https://google.github.io/dagger/
* [Coroutines] - https://kotlinlang.org/docs/reference/coroutines.html
* [RxJava] - https://github.com/ReactiveX/RxJava
* [facebook login integration]

If you check source code, you may see that it is not completely uniform. This was on purpose, exactly to test all the technologies listed above.
For instance, inside [AttendeeActivity], I am consuming the facebook API by subscribing to the response, using RXJAVA + RETROFIT.
On the other hand, [EventFinderResponseActivity] used OKHTTP + coroutines to consume another facebook API.
I dont have plans to follow only one approach in the future. I hope this source code may help other developers who want to find working examples for both approached, as it took some time for me to make them work.

``
# Development

Want to contribute? Great!

Send us suggestions or simply open your pull request

# Todos

 - Write Tests
 - A few technical debts, which are described inside the source code comments
 - Suggestions?


License
----

MIT


**Free Software**
