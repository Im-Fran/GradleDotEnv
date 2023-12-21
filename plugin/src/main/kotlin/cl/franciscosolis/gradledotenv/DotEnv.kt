package cl.franciscosolis.gradledotenv

import java.io.File
import java.nio.charset.Charset

class DotEnv {

    private val variables = mutableMapOf<String, String>()

    init {
        val environment = System.getenv("ENV") ?: System.getProperty("ENV")
        val file = File(".env.${environment?.lowercase() ?: ""}").let {
            if (it.exists()) {
                it
            } else {
                File(".env")
            }
        }

        if (file.exists()) {
            file.readLines(Charset.defaultCharset())
                .filter { it.isNotBlank() && !it.startsWith(';') && !it.startsWith('#') }.associateTo(variables) {
                val (key, value) = it.split("=")
                key to value
            }
        }

        variables.putAll(System.getenv())
    }

    fun environments(): Map<String, String> = variables.toMap()
}