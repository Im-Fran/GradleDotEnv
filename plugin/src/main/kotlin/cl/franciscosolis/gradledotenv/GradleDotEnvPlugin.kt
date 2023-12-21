package cl.franciscosolis.gradledotenv

import org.gradle.api.Project
import org.gradle.api.Plugin

class GradleDotEnvPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        // Read the all env variables from the '.env' file at the root of the project and from the system environment variables.
        val env = DotEnv()

        // Add the 'env' extension to the project.
        project.extensions.add("env", env.environments())
    }
}
