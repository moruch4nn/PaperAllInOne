plugins {
    kotlin("jvm") version "1.8.21"
}

group = "dev.mr3n"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    implementation("org.objenesis:objenesis:3.3")
}

kotlin {
    jvmToolchain(17)
}