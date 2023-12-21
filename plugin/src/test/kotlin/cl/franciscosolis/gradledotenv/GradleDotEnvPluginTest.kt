package cl.franciscosolis.gradledotenv

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull

class GradleDotEnvPluginTest {

    @Test
    fun `plugin registers env extension`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("cl.franciscosolis.gradledotenv")

        // Verify the result
        assertNotNull(project.extensions.findByName("env"))
    }
}
