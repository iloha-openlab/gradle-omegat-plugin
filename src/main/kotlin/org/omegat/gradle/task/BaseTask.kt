package org.omegat.gradle.task

import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskAction


@CacheableTask
open class BaseTask : JavaExec() {
    @Input
    protected val argList = mutableListOf<String>()

    @Input
    var projectDir: String? = null

    init {
        group = "org.omegat"
        mainClass.set("org.omegat.Main")
        val lang = systemProperties["user.language"]?:""
        val country = systemProperties["user.country"]?:""
        val proxy = systemProperties["http.proxyHost"]?:""
        val prop = project.properties["no-team"]?:"false"

        if (!"".equals(lang)) {
            argList.add("-Duser.language=" + lang)
            if (!"".equals(country)) {
                argList.add("-Duser.country=" + country)
            }
        }
        if (!"".equals(proxy)) {
            argList.add("-Dhttp.proxyHost=" + proxy)
        }
        if ("true".equals(prop)) {
            argList.add("--no-team")
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