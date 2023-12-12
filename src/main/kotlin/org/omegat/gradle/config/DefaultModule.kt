package org.omegat.gradle.config

import org.gradle.api.Project
import org.omegat.gradle.OmegatPlugin
import java.util.Properties

class DefaultModule(project: Project) {

    init {
        project.repositories.mavenCentral()
    }

    fun getDependencies(extension: PluginExtension): List<String> {
        val props = Properties()
        props.load(OmegatPlugin::class.java.getResourceAsStream("omegat.properties"))
        val vldockingVersion = props.getProperty("vldockingVersion").toString()
        return listOf("${extension.omegatGroup.get()}:${extension.omegatProduct.get()}:${extension.version.get()}", "org.omegat:vldocking:${vldockingVersion}")
    }
}