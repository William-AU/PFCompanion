# PFCompanion #
A multipurpose console based management system for various Tabletop Roleplaying Games.   

## Description ##
PFCompanion primarily focuses on tracking various resources for players and GMs of [Pathfinder](https://paizo.com/pathfinder), although support for other TTRPGs can easily be implemented using the tools included in this repository.

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
This section outlines how to implement new features using the existing framework, and focuses on adding new "scenes", for extending keyboard layout support, see [Known issues](#known-issues)


This project uses a simple MVC architecture, where each "screen" shown to the user is represented by the `application.view.Scene` interface. 
The initial scene shown can be configured in `application.config.SpringConfig`, all other scene changes are handled from the `application.controller.Controller` class.   
### The Scene Interface ###
At the core of each `application.view.Scene` is the `draw()` method, this method is called by the `Controller` each time the scene is updated, find an example implementation from `MainMenuScene` below:
```java
@Override
public void draw() {
    LayoutBuilder builder = new LayoutBuilder(serviceContext);
    builder.setCenter(true)
            .addLine("Select mode and press enter")
            .addOption(optionGrid.getCurrentOption());
    System.out.println(builder.build());
}
```
Note that this implementation simply prints to `System.out`, as the main interface is the terminal session linked to the JVM.   
The above implementation does not draw the title card, this is handled separately at the `Controller` when the `draw()` method is invoked. To disable the default title implement the following method in `Scene`:
```java
/**
* Tells the controller if this view requires the basic title be drawn. Usually only disabled if the view wants to draw a scene specific title instead
* @return True if the controller should draw the main title, false otherwise. Defaults to true.
*/
default boolean shouldDrawTitle() {
    return true;
```

Each scene implements a `confirm()` method, which is called whenever the user presses enter, it is the responsibility of the Scene to validate this input, and can then change the shown Scene by returning another one, or null to maintain the current scene.
The following is an example implementation of a `confrim()` method:
```java
@Override
public Scene confirm() {
    // ID 0 = Player
    // ID 1 = GM
    return switch (optionGrid.getCurrentOption().getId()) {
        case "0" -> new CharacterSelectionScene();
        case "1" -> new GMSelectionScene();
        default -> null;
    };
}
```
In the above method, the Scene holds some state information about the currently selected menu element, it then changes the current view based on this state by returning a different Scene.   

In order to handle keyboard input, Scenes have to implement an `inputkey(ListenerKey key)` method, which handles arrow keys, as well as most functional keys like ESC, Tab, and Backspace.
An example implementation using the built in `application.view.strategies.OptionMovementStrategy`:
```java
@Override
public boolean inputKey(ListenerKey key) {
    return optionMovementStrategy.handleMove(optionGrid, key);
}
```
If you wish to get more detailed letter input, you can additionally implement the following methods:
```java
/**
* Updates the view with the given char input, return true if anything changed
* @param ch The char pressed
* @return True if the char changed anything about the view state, false otherwise. Defaults to false as to be consistent with {@link #shouldAcceptLetters()}
*/
default boolean inputChar(char ch) {
    return false;
};
```
```java
/**
* Tells the controller if this view is accepting letter input, usually for text input
* @return True if the current view is accepting letters, false otherwise. Defaults to false.
*/
default boolean shouldAcceptLetters() {
    return false;
}
```
Note that both of these methods must be implemented to support this feature.

### The Option Interface ###
When designing Scenes, it is often necessary to handle menu buttons and other interactive objects. To support this, you can use the `application.view.options.Option` class.   
An `Option` represents an interactive menu element, and has two implementations:
* `SimpleOption` represents an immutable menu element. It has a Label which can be rendered on the scene, and an ID which uniquely identifies the option in the scene.
* `MutableOption` represents a mutable meun element, and consists of a multiple `SimpleOption` elements connected by a circular doubly linked list. Mutable options allow you to easily change both the Label and ID of a menu element in response to user input.

Both `Option` implementations support highlighting to keep track of which option is currently selected by the user.

### The OptionGrid Class ###
To easily keep track of options in your scene, as well as the spacial relation between these options, you can construct a `application.view.options.OptionGrid`.   
An OptionGrid defines the rough layout of options, and supports easily changing the currently highlighted option using the `moveRight()`, `moveDown()`, `moveLeft()`, and `moveUp()` methods. The grid handles both `SimpleOption` and `MutableOption` implementations of `Option`

### Builders and Strategies ###
Manually handling `Option` and `OptionGrid` implementations can quickly get hairy. At the same time, writing code to display options can easily get repetitive.  
The `OptionGridBuilder` and `LayoutBuilder` found in `application.view.builders` aim to combat these problems. `OptionGridBuilder` allows you to quickly create a grid of options, and can be used like this:
```java
OptionGridBuilder builder = new OptionGridBuilder();
builder.addMutableOptionAtPos(0, 0,
        new SimpleOption("<Player>", "0"),
        new SimpleOption("<GM>", "1"));
this.optionGrid = builder.build();
```
This `OptionGrid` can then be rendered using a `LayoutBuilder` in the `draw()` method of the scene as follows:
```java
@Override
public void draw() {
    LayoutBuilder builder = new LayoutBuilder(serviceContext);
    builder.setCenter(true)
            .addLine("Select mode and press enter")
            .addOption(optionGrid.getCurrentOption());
    System.out.println(builder.build());
}
```

### The Service Context ###
Some scene features might require access to services as found in `application.services`. This currently includes methods to render text based on the users color choice, as well as information about the terminal, importantly, these things are used by some Builders. All of these services are found in the `application.storage.services.ServiceContext` class.

If you need access to these classes, implement either of the following `Scene` methods:
```java
default void setController(Controller controller) {
};

default void setServiceContext(ServiceContext serviceContext) {
};
```
These methods will be called by the `Controller` when a scene is changed.


## License ##

This project is licensed under the MIT License - see the LICENSE.md file for details
