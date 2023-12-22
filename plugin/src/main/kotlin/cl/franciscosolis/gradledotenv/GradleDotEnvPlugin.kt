package cl.franciscosolis.gradledotenv

import org.gradle.api.Project
import org.gradle.api.Plugin

class GradleDotEnvPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val env = DotEnv(project)

        project.extensions.create("env", DotEnvPluginExtension::class.java, env)
    }
}

open class DotEnvPluginExtension(private val env: DotEnv) {
    operator fun get(key: String): String? = env.get(key)
}
