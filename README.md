#### Kotlin Scripting Kickstarter

Kotlin Scripting Kickstarter demonstrates how Kotlin can be used as a scripting language.

Development of scripts can be done in IntelliJ using any libraries maven repositories have to offer - all in a clean, isolated environment.

#### Quick Start

All you need to get going is a JDK installed, a unix-ish environment and an internet connection.

```shell
$ git clone https://github.com/andrewoma/kotlin-scripting-kickstarter kotlin-scripts
$ cd kotlin-scripts/
$ ./gradlew check
$ export PATH=$PATH:`pwd`/kotlin
$ helloworld.kt
$ vi `which helloworld.kt` # Make some changes
$ helloworld.kt # See the results (takes a few seconds to compile)
$ helloworld.kt # Runs in around 100ms
```

#### License
This project is licensed under a MIT license.

[![Build Status](https://travis-ci.org/andrewoma/kotlin-scripting-kickstarter.svg?branch=master)](https://travis-ci.org/andrewoma/kotlin-scripting-kickstarter)
