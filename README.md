# Gradle Dot Env Plugin
![GitHub Workflow Status (with event)](https://img.shields.io/github/actions/workflow/status/Im-Fran/GradleDotEnv/test.yml?logo=github&label=Workflow+Status)
![GitHub](https://img.shields.io/github/license/Im-Fran/GradleDotEnv?label=License)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/Im-Fran/GradleDotEnv?label=Release)

A Gradle plugin to load environment variables from a .env file.

## Installation
```kotlin
// Kotlin
plugins {
    id("cl.franciscosolis.gradledotenv") version "1.0.0"
}
```

```groovy
// Groovy
plugins {
    id 'cl.franciscosolis.gradledotenv' version '1.0.0'
}
```

## Usage
The plugin adds a map called `env` to the project's `extra` property.
This map contains all the environment variables loaded from the .env file and the system environment variables. The system environment variables have priority over the .env file variables.
```kotlin
// Kotlin
println(env["ENV_VAR"])
```

```groovy
// Groovy
println env["ENV_VAR"]
```

## Support for different environments
The plugin supports different environments
by using different .env files by using the `ENV` system environment variable,
if is not present it will try to use the `ENV` system property.
If none of them are present, it will just use the default `.env` file.

So for example, if you have the following file structure:
```
├── .env
├── .env.dev
├── .env.prod
├── .env.test
├── build.gradle.kts
└── src
    └── main
        └── kotlin
            └── Main.kt
```

And you run the following command:
```bash
ENV=dev ./gradlew run
```

The plugin will load the variables from the `.env.dev` file.

## License
This project is licensed under the GNU GPLv3 License - see the [LICENSE](https://github.com/Im-Fran/GradleDotEnv/blob/master/LICENSE) file for details.
