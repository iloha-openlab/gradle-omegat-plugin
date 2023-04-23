package org.omegat.gradle.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

open class TranslateTask : BaseTask() {
    @Option(option = "team", description = "Configures team mode.")
    @Input
    var team: String = "false";

    @TaskAction
    override fun exec() {
        if (projectDir == null) { // set default project root
            projectDir = project.rootDir.path
        }
        argList.apply {
            add("--mode=console-translate")
            if (!"true".equals(team, true)) {
                add("--no-team")
            }
            add(projectDir!!)
        }
        super.exec()
    }
}