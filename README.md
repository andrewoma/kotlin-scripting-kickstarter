#### Kotlin Scripting Kickstarter

Kotlin Scripting Kickstarter demonstrates how [Kotlin](http://kotlin.jetbrains.org/) can be used as a scripting language.

Clone this project as a starting point for developing scripts in Kotlin.

#### Kickstarter Features

* Open the project in IntelliJ and develop scripts with full IDE support (completion, compilation, testing and debugging).
* Run scripts as standard unix scripts on the command line. e.g. `#!/usr/bin/env kotlin-script.sh` 
* Edit scripts using any editor and they will be automatically compiled and cached when run.
* Use gradle to include libraries available in maven repositories in your scripts.
* Automatically bootstrap the environment. Just run any script and the Kotlin runitme and library dependencies
  will be downloaded automatically.

#### Quick Start

All you need to get going is a JDK installed and a unix-ish environment.

```shell
$ git clone https://github.com/andrewoma/kotlin-scripting-kickstarter kotlin-scripts
$ cd kotlin-scripts/
$ ./gradlew check copyToLib
$ export PATH=$PATH:`pwd`/kotlin
$ helloworld.kt
$ vi `which helloworld.kt` # Make some changes
$ helloworld.kt # See the results (takes a few seconds to compile)
$ helloworld.kt # Runs in around 100ms now that it is pre-compiled
```

Now open the cloned project in IntelliJ and edit and run the scripts within the IDE.

#### Examples

See [histogram.kt](/kotlin/histogram.kt) for a sample script that generates a chart using
`arg4j` for command line parsing and `jfreechart` for chart generation.

#### How it works

* The project itself is just a standard gradle project. It uses the [gradle wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html) to bootstrap, automatically 
  installing gradle, downloading libraries and compiling.
* Kotlin natively supports scripting to a limited extent, so it is valid to use `#!` [(shebang)](http://en.wikipedia.org/wiki/Shebang_(Unix)) directives at the top of kotlin source files.
* The kickstarter exploits this to call [kotlin-script.sh](/kotlin/kotlin-script.sh) to launch scripts. The script runner checks to see if the script being run has been modified and if so, forces recompilation via gradle. Otherwise, the script is launched immediately.

#### Limitations
* Scripts must end with the `.kt` suffix as IntelliJ uses extensions to detect file types.
* Scripts must be declared in their own unique package or the `main` method will conflict with other scripts.
* The gradle koltin plugin seems to require that kotlin sources live in a directory called `kotlin`  

#### Stuck behind an authenticating proxy?

Both `git` and `gradle` need to be configured to work via a proxy. 

For `git`, add the following to `~/.gitconfig `:
```
[http]
	proxy = http://<user>:<password>@<proxy_host>:<proxy_port>
```

For `gradle`, the following seems to work with both `gradle` and `gradlew`:
```
export GRADLE_OPTS='-Dhttp.proxyHost=<proxy_host> -Dhttp.proxyPort=<proxy_port> -Dhttp.proxyUser=<user> -Dhttp.proxyPassword=<password>'
```

#### Alternatives
[kotlin-script](https://github.com/andrewoma/kotlin-script) provides traditional scripting support for Kotlin. The main advantage of the kickstarter approach is that supports full IDE development.

#### License
This project is licensed under a MIT license.

[![Build Status](https://travis-ci.org/andrewoma/kotlin-scripting-kickstarter.svg?branch=master)](https://travis-ci.org/andrewoma/kotlin-scripting-kickstarter)
