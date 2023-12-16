package org.omegat.gradle.task

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.*
import org.gradle.api.tasks.options.Option


@CacheableTask
open class BaseTask : JavaExec() {
    @Internal
    protected val argList = mutableListOf<String>()
    @Internal
    protected val rootDir: String
    @Input
    var configDir: String

    @Option(option = "projectDir", description = "Project root directory")
    @Optional
    @Input
    var projectDir: String? = null

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
        rootDir = project.rootDir.path
        configDir = project.layout.buildDirectory.file("omegat").get().toString()
    }

    @TaskAction
    override fun exec() {
        maxHeapSize = "2048M"
        classpath = project.configurations.getByName("omegat")
        argList.also { args = it }
        super.exec()
    }
}