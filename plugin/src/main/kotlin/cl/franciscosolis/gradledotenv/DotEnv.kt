package cl.franciscosolis.gradledotenv

import org.gradle.api.Project
import java.io.File
import java.nio.charset.Charset

class DotEnv(project: Project) {

    private val variables = mutableMapOf<String, String>()

    init {
        val envFile = File(project.rootDir, determineSystemEnvFile())
        if(envFile.exists()) {
            loadEnvFile(envFile)
        }

        variables.putAll(System.getenv())
    }

    private fun loadEnvFile(file: File) {
        file.readLines(Charset.defaultCharset())
            .filter { it.isNotBlank() && !it.startsWith(';') && !it.startsWith('#') }
            .forEach { line ->
                val keyValue = line.split("=", limit = 2)
                if (keyValue.size == 2) {
                    val (key, value) = keyValue
                    variables[key.trim()] = removeQuotes(value.trim())
                } else if (keyValue.size == 1) {
                    variables[keyValue[0].trim()] = ""
                }
            }
    }

    private fun removeQuotes(value: String): String = if (value.startsWith('"') && value.endsWith('"')) {
        value.substring(1, value.length - 1)
    } else {
        value
    }

    private fun determineSystemEnvFile(): String = (System.getenv("ENV") ?: System.getProperty("ENV"))?.let {
        ".env.${it.lowercase()}"
    } ?: ".env"

    fun get(key: String): String? = variables[key]
}
