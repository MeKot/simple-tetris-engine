simple-tetris-engine
---------------------------------

Simulates a simplified version of tetris based on command line input.
This was completed as a take home assignment for a company as part of
their interview process.

<br/>

## Tech stack
- Written in Java 8 using Intellij
- Tested via Spock & Groovy (the only external libraries used)
- Version control managed with Git
- Dependencies managed and artifacts built using Gradle
    - The Gradle wrapper is used so it is not necessary for you to have Gradle installed on your machine


<br/>

## Building & running

### Building a JAR
To build an executable JAR from the root of the project use:
```
./gradlew jar
```
The resulting JAR file will be located in the `build/libs` directory.

### Running the JAR
The executable JAR that is generated is expected to be run in the following way:
```
java -jar simple-tetris-engine.jar <input-file-path> <output-file-path>
```

### Running tests
To run all tests from the root of the project use:
```
./gradlew check
```

<br/>

## Adding shapes
Adding a new shape to the application should only require adding another enum value to the `Shape` class
with the correct dimensions.  The tests for parsing values from the command line are dynamic so
rerunning tests will test whether or not your new shape can be parsed from the command line properly.