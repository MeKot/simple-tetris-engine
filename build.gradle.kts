import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    java
    groovy
    idea
}

// Tell Gradle what to add to the manifest file for the resulting JAR
tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "te.tetris.TetrisApplication"
    }
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