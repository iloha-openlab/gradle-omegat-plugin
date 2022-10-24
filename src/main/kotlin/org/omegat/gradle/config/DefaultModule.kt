package org.omegat.gradle.config

import org.gradle.api.Project
import org.omegat.gradle.OmegatPlugin
import java.util.Properties

class DefaultModule(project: Project) {
    private val props = Properties()

    init {
        props.load(OmegatPlugin::class.java.getResourceAsStream("omegat.properties"))
        project.repositories.mavenCentral()
    }

    fun getDependencies(extension: PluginExtension): List<String> {
        val omtGroup = extension.omegatGroup ?: "org.omegat"
        val omtProd = extension.omegatProduct ?: "omegat"
        val omtVersion = extension.version ?: props.getProperty("omegatVersion").toString()
        val vldockingVersion = props.getProperty("vldockingVersion").toString()
        return listOf("${omtGroup}:${omtProd}:${omtVersion}", "org.omegat:vldocking:${vldockingVersion}")
    }
}