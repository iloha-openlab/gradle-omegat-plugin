package org.omegat.gradle.task

import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.jvm.tasks.Jar
import org.omegat.gradle.config.DefaultModule
import org.omegat.gradle.config.PluginExtension


fun Project.setupOmegatTasks(extension: PluginExtension) {
    extension.omegatGroup.convention("org.omegat")
    extension.omegatProduct.convention("omegat")
    extension.version.convention("6.0.0")
    extension.debugPort.convention(8457)
    tasks.register("translate", TranslateTask::class.java)

    afterEvaluate {
        if (extension.version.get().startsWith("5.8.")
            || extension.version.get().startsWith("6.")
        ) {
            tasks.register("translateStats", StatsTask::class.java)
        }
        if (extension.pluginClass.isPresent) {
            val jarTask = project.tasks.withType(Jar::class.java).getByName("jar")
            tasks.register("runOmegaT", RunTask::class.java) {
                it.dependsOn(jarTask)
            }
            tasks.register("debugOmegaT", DebugTask::class.java) {
                it.debugPort = extension.debugPort.get()
                it.dependsOn(jarTask)
            }
            jarTask.outputs.upToDateWhen { false }
            jarTask.doFirst { task ->
                if (project.hasProperty("plugin.name")) {
                    jarTask.manifest.attributes(
                        extension.manifest.createOmegatPluginJarManifest(
                            extension.pluginClass.get().toString(),
                            project.findProperty("plugin.name").toString()
                        )
                    )
                }
                jarTask.from(
                    task.project.configurations.getByName("packIntoJar").files.map { file ->
                        if (file.isDirectory) {
                            project.fileTree(file)
                        } else {
                            project.zipTree(file)
                        }.matching {
                            extension.packIntoJarFileFilter.invoke(it)
                        }
                    }
                )
                jarTask.duplicatesStrategy = DuplicatesStrategy.WARN
            }
            jarTask.doLast { task ->
                val basename = jarTask.archiveBaseName.getOrElse(project.name)
                val ext = jarTask.archiveExtension.getOrElse("jar")
                project.copy {
                    it.duplicatesStrategy = DuplicatesStrategy.WARN
                    it.from(task.outputs.files)
                    it.rename { _ -> "${basename}.${ext}" }
                    it.into(project.layout.buildDirectory.file("omegat/plugins/").get())
                }
            }
        }
    }
}

fun Project.setupOmegatConfig(extension: PluginExtension) {
    project.configurations.run {
        val config = create("packIntoJar")
        config.setVisible(false).setTransitive(true).description = "The OmegaT configuration for this project."
        create("omegat")
    }
    afterEvaluate {
        val deps = DefaultModule(project).getDependencies(extension)
        project.configurations.run {
            getByName("omegat").apply {
                for (dep in deps) {
                    dependencies.add(project.dependencies.create(dep))
                }
            }
        }
        if (extension.pluginClass.isPresent) {
            project.configurations.run {
                getByName("implementation").apply {
                    extendsFrom(getByName("packIntoJar"))
                }
                deps.forEach { dep ->
                    getByName("implementation").apply {
                        dependencies.add(project.dependencies.create(dep))
                    }
                    getByName("testImplementation").apply {
                        dependencies.add(project.dependencies.create(dep))
                    }
                }
            }
        }
    }
}