object Dependencies{
    // kotlin
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin:${DependencyVersions.JACKSON_VERSION}"

    // webflux
    const val SPRING_WEBFLUX = "org.springframework.boot:spring-boot-starter-webflux"

    //
    const val KOTLIN_REACTOR = "io.projectreactor.kotlin:reactor-kotlin-extensions"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor"

    // validation
    const val SPRING_VALIDATION = "org.springframework.boot:spring-boot-starter-validation"

    // configuration
    const val CONFIGURATION_PROCESSOR = "org.springframework.boot:spring-boot-configuration-processor"

    // database
    const val SPRING_MONGODB = "org.springframework.data:spring-data-mongodb"
    const val MONGODB_REACTIVE = "org.mongodb:mongodb-driver-reactivestreams"

    // test
    const val SPRING_TEST = "org.springframework.boot:spring-boot-starter-test:${PluginVersions.SPRING_BOOT_VERSION}"

    //KOMORAN
    const val KOMORAN = "com.github.shin285:KOMORAN:3.3.4"

    // tomcat-util
    const val TOMCAT_UTIL = "org.apache.tomcat:tomcat-util:${DependencyVersions.TOMCAT_UTIL_VERSION}"
}