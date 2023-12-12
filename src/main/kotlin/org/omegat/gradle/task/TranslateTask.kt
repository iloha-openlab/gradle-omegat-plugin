package org.omegat.gradle.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

open class TranslateTask : BaseTask() {
    @TaskAction
    override fun exec() {
        argList.apply {
            add("--mode=console-translate")
            if (!"true".equals(team, true)) {
                add("--no-team")
            }
            add("--config-dir=${configDir}")
            add(projectDir?:rootDir)
        }
        super.exec()
    }
}