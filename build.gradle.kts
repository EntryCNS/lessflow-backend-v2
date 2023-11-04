plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("jvm") version PluginVersions.JVM_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
}

group = "com.dgswcns"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(Dependencies.KOTLIN_REFLECT)
    implementation(Dependencies.JACKSON)

    implementation(Dependencies.SPRING_WEBFLUX)

    implementation(Dependencies.KOTLIN_REACTOR)
    implementation(Dependencies.COROUTINES)

    annotationProcessor(Dependencies.CONFIGURATION_PROCESSOR)

    implementation(Dependencies.MONGODB_REACTIVE)
    implementation(Dependencies.SPRING_MONGODB)

    //implementation(Dependencies.SPRING_SECURITY)

    implementation(Dependencies.JWT)

    implementation(Dependencies.SPRING_TEST)

    implementation(Dependencies.JSOUP)

    implementation(Dependencies.KOMORAN)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
