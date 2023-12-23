import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    alias(libs.plugins.jvm)
    id("com.gradle.plugin-publish") version "1.2.1"
}

version = "1.0.0"
group = "cl.franciscosolis"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

gradlePlugin {
    website = "https://github.com/Im-Fran/GradleDotEnv"
    vcsUrl = "https://github.com/Im-Fran/GradleDotEnv"

    val env by plugins.creating {
        id = "cl.franciscosolis.gradledotenv"
        implementationClass = "cl.franciscosolis.gradledotenv.GradleDotEnvPlugin"
        displayName = "GradleDotEnv"
        description = "Gradle plugin to load environment variables from .env files"
        version = project.version
        tags = listOf("env", "dotenv", "envfile", "environment", "variables")
    }
}

val functionalTestSourceSet = sourceSets.create("functionalTest") {}

configurations["functionalTestImplementation"].extendsFrom(configurations["testImplementation"])
configurations["functionalTestRuntimeOnly"].extendsFrom(configurations["testRuntimeOnly"])

val functionalTest by tasks.registering(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
    useJUnitPlatform()
}

gradlePlugin.testSourceSets.add(functionalTestSourceSet)

tasks {
    named<Task>("check") {
        dependsOn(functionalTest)
    }

    named<Test>("test") {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }
}