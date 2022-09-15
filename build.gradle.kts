import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.10"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.3")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10")
    runtimeOnly("org.postgresql:postgresql:42.5.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.3")
    //bot
    implementation("org.telegram:telegrambots-spring-boot-starter:6.1.0")
    implementation("org.springframework.boot:spring-boot-starter-security:2.7.3")
    implementation("org.mapstruct:mapstruct:1.5.2.Final")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation ("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.4")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("org.hashids:hashids:1.0.3")
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation ("org.springdoc:springdoc-openapi-ui:1.6.11")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
