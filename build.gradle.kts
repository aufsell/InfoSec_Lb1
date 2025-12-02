import com.github.spotbugs.snom.SpotBugsTask
import com.github.spotbugs.snom.Confidence

plugins {
    java
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.spotbugs") version "6.4.5"
}

group = "org.itmo.infosec"
version = "0.0.1-SNAPSHOT"
description = "InfoSec_Lab1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.apache.commons:commons-lang3:3.20.0")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
}
configurations.all {
    resolutionStrategy {
        force("org.apache.commons:commons-lang3:3.20.0")
    }
}
buildscript {
    configurations.all {
        resolutionStrategy {
            force("org.apache.commons:commons-lang3:3.20.0")
        }
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}

spotbugs {
    toolVersion.set("4.9.8")
    reportLevel.set(Confidence.LOW)
    ignoreFailures.set(true)
    showStackTraces.set(true)
    showProgress.set(true)
}

tasks.withType<SpotBugsTask>().configureEach {

    reports.create("html") {
        required.set(true)
        outputLocation.set(layout.buildDirectory.file("reports/spotbugs/${this@configureEach.name}.html"))
    }
    reports.create("xml") {
        required.set(false)
    }
}
