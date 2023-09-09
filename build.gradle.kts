plugins {
    id("java")
}

group = "org.avitech"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple) {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    implementation(libs.log4j.core)
    implementation(libs.h2database.h2)
    implementation(libs.yaml.snakeyaml)

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.test {
    useJUnitPlatform()
}