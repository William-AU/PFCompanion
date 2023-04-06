# PFCompanion #
A multipurpose console based management system for various Tabletop Roleplaying Games.   

## Description ##
PFCompanion primarily focuses on tracking various resources for players and GMs of [Pathfinder](https://paizo.com/pathfinder), although support for other TTRPGs can easily be implemented using the tools included in this repository.

Progress and current state can be 

## Dependencies ##
* [JNativeHook](https://github.com/kwhat/jnativehook) is used to read keyboard input, however, there is a [known issue](https://github.com/kwhat/jnativehook/issues/428) with newer versions of JNativeHook. Until this issue is fixed, this project uses version 2.1.0, which is incompatible with Mac Silicon.
* On MacOS, the terminal attached to the JVM requires accessibility settings for JNativeHook to work.
* Java 17 or greater to support certain language features
* Gradle 7.6 is used as default for compiling the project, although any gradle version compatible with Java 17 should work
* [JColor](https://github.com/dialex/JColor) is used to support color and highlighting in the terminal, the console used must therefore support ANSI to show these colors. This should work out of the box for both MacOS terminal and the basic Windows command prompt.
* [JLine3](https://github.com/jline/jline3) is used to get terminal information, as well as force JANSI mode and raw input.


## Installing ##
### Running the program ###
To run the program, simply get the latest jar from the latest [release](https://github.com/William-AU/PFCompanion/releases), and run it using:
```
java -jar /path/to/PFCompanion.jar 
```

### Compiling the program ###
To compile the program, first run:
```
gradle build
```
And the jar can be found and run at
```
java -jar /build/libs/PFCompanion-VERSION.jar
```
Certain information, such as the database URL can be configured before compilation by creating the file `src/main/resources/secret.txt`, any information written here is then baked into the Jar when built. For more information on this see [Preconfiguration](#Preconfiguration).

## Preconfiguration ##
WIP

## Known issues ##
* In version 2.1.0 of [JNativeHook](https://github.com/kwhat/jnativehook), auto-detection of keyboard layouts is broken, and as such keyboard mappings have to be implemented manually. Currently only (partially) supports ISO-DAN, using other layouts might cause unintended consequences. New keyboard layouts can be manually added by implementing the `application.listeners.keyboardLayouts.KeyboardLayout` interface, and changing the default implementation in `application.config.SpringConfig`   

## Quirks ##
WIP

## Implementing your own functionality ##
WIP

## License ##

This project is licensed under the MIT License - see the LICENSE.md file for details
