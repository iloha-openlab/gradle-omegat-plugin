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
        extension.omegatGroup.convention("org.omegat")
        extension.omegatProduct.convention("omegat")
        extension.version.convention(props.getProperty("omegatVersion").toString())
        val vldockingVersion = props.getProperty("vldockingVersion").toString()
        return listOf("${extension.omegatGroup}:${extension.omegatProduct}:${extension.version}", "org.omegat:vldocking:${vldockingVersion}")
    }
}