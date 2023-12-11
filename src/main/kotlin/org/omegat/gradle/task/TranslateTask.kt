package org.omegat.gradle.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

open class TranslateTask : BaseTask() {
    @Option(option = "team", description = "Configures team mode.")
    @Input
    var team: String = "false";

    @TaskAction
    override fun exec() {
        if (projectDir == null) { // set default project root
            val objectFactory = project.objects
            projectDir = objectFactory.directoryProperty().apply {
                project.rootDir.path
            }
        }
        argList.apply {
            add("--mode=console-translate")
            if (!"true".equals(team, true)) {
                add("--no-team")
            }
            when {
                configDir != null -> add("--config-dir=${configDir!!.get()}")
                else -> add("--config-dir=${File(project.buildDir, "omegat")}")
            }
            add(projectDir!!.get().toString())
        }
        super.exec()
    }
}