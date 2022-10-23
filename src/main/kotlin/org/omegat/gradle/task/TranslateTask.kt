package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction

open class TranslateTask : BaseTask() {
    @TaskAction
    override fun exec() {
        if (projectDir == null) { // set default project root
            projectDir = project.rootDir.path
        }
        argList.apply {
            add("--mode=console-translate")
            add(projectDir!!)
        }
        super.exec()
    }
}