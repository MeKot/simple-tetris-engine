import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    java
    groovy
    idea
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    // Spock testing framework
    testImplementation("cglib:cglib-nodep:3.2.12")
    testImplementation("org.objenesis:objenesis:3.0.1")
    testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
}

tasks {
    test {
        testLogging.showExceptions = true
        testLogging.exceptionFormat = TestExceptionFormat.FULL
    }
}


/*
        TODO: Decide if we want to include spock-reports
        // Dependencies
        testRuntime("org.slf4j:slf4j-simple:1.7.26")
        testRuntime("com.athaydes:spock-reports:1.6.2")

        // simpleLogger.properties
        org.slf4j.simpleLogger.defaultLogLevel=ERROR
        org.slf4j.simpleLogger.logFile=System.out
        org.slf4j.simpleLogger.showThreadName=false
        org.slf4j.simpleLogger.levelInBrackets=true
 */