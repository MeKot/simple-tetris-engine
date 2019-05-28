simple-tetris-engine
---------------------------------

Simulates a simplified version of tetris based on command line input.

## The code
- Written in Java 8 using Intellij
- Tested via Spock & Groovy (the only external libraries used)
- Version control managed with Git
- Dependencies managed and artifacts built using Gradle
    - The Gradle wrapper is used so it is not necessary for you to have Gradle installed on your machine



### Building a JAR
To build an executable JAR from the root of the project use:
```
./gradlew jar
```

### Running tests
To run all tests from the root of the project use:
```
./gradlew check
```

### Adding new shapes
Adding a new shape to the application should only require adding another enum value to the `Shape` class
with the correct dimensions.  The tests for parsing values from the command line are dynamic so
rerunning tests will test whether or not your new shape can be parsed from the command line properly.

<br/>

#### Extras
By adding the following lines to the `dependencies` block in the `build.gradle.kts` file and re-running tests a directory 
in the `build` dir will be created call `spock-reports` that will contain a nicely formatted
HTML file of the tests that were run.

```
testRuntime("com.athaydes:spock-reports:1.6.2")     // Spock test -> HTML file converter 
testRuntime("org.slf4j:slf4j-simple:1.7.26")        // Slf4j logging implementation 
```