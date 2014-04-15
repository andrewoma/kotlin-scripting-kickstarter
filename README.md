#### Kotlin Scripting Kickstarter

Kotlin Scripting Kickstarter demonstrates how Kotlin can be used as a scripting language.

Cloning the kickstarter project gives you a starting point for developing scripts in Kotlin.

#### Kickstarter Features

* Open the project in IntelliJ and develop scripts with full IDE support (completion, compilation, testing and debugging).
* Run scripts as standard unix scripts on the command line. e.g. `#!/usr/bin/env kotlin-script.sh` 
* Edit scripts using any editor and they will be automatically compiled and cached.
* Use any of the thousands of libraries available in maven repositories in your scripts.
* Automatically bootstrap the environment. Just run any script and the Kotlin runitme and maven dependencies
  will be downloaded automatically.

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

#### Examples

See [histogram.kt](/kotlin/histogram.kt) for a sample script that generates a chart using
some libraries from the maven repository.

#### License
This project is licensed under a MIT license.

[![Build Status](https://travis-ci.org/andrewoma/kotlin-scripting-kickstarter.svg?branch=master)](https://travis-ci.org/andrewoma/kotlin-scripting-kickstarter)
