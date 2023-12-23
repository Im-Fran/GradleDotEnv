package cl.franciscosolis.gradledotenv

import java.io.File
import kotlin.test.assertTrue
import kotlin.test.Test
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.io.TempDir
import java.util.UUID

/**
 * Test that the extension 'env' is present
 */
class GradleDotEnvPluginFunctionalTest {

    @field:TempDir
    lateinit var projectDir: File

    private val buildFile by lazy { projectDir.resolve("build.gradle.kts") }
    private val settingsFile by lazy { projectDir.resolve("settings.gradle.kts") }
    private val envFile by lazy { projectDir.resolve(".env") }

    @Test
    fun hasEnvExtension() {
        val envs = mapOf("FOO" to "BAR", "UUID" to UUID.randomUUID().toString())
        // Set up the test build
        envFile.writeText("""
            FOO=${envs["FOO"]}
            UUID=${envs["UUID"]}
        """.trimIndent())
        settingsFile.writeText("")
        buildFile.writeText("""
            plugins {
                id("cl.franciscosolis.gradledotenv")
            }
            
            println("UUID=${'$'}{env["UUID"]}")
            println("FOO=${'$'}{env["FOO"]}")
        """.trimIndent())

        // Run the build
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withEnvironment(envs)
        runner.withProjectDir(projectDir)
        val result = runner.build()

        // Verify the result
        assertTrue(result.output.contains("BUILD SUCCESSFUL"))
        assertTrue(result.output.contains("UUID=${envs["UUID"]}"))
        assertTrue(result.output.contains("FOO=${envs["FOO"]}"))
    }
}
