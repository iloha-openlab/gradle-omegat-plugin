package org.omegat.gradle.task

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option


@CacheableTask
open class BaseTask : JavaExec() {
    @Input
    protected val argList = mutableListOf<String>()

    @Option(option = "configDir", description = "Project root directory")
    @Input
    var projectDir: String? = null

    @Option(option = "configDir", description = "Configuration directory")
    @Input
    var configDir: String? = null

    @Option(option = "team", description = "Configures team mode.")
    @Input
    var team: String = "false";

    init {
        group = "org.omegat"
        mainClass.set("org.omegat.Main")
        val lang = systemProperties["user.language"]?:""
        val country = systemProperties["user.country"]?:""
        val proxy = systemProperties["http.proxyHost"]?:""

        if (!"".equals(lang)) {
            argList.add("-Duser.language=" + lang)
            if (!"".equals(country)) {
                argList.add("-Duser.country=" + country)
            }
        }
        if (!"".equals(proxy)) {
            argList.add("-Dhttp.proxyHost=" + proxy)
        }
    }

    @TaskAction
    override fun exec() {
        maxHeapSize = "2048M"
        classpath = project.configurations.getByName("omegat")
        argList.also { args = it }
        super.exec()
    }
}