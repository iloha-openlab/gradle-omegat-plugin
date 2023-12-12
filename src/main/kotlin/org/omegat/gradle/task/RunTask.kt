package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction

open class RunTask : BaseTask()  {
    @TaskAction
    override fun exec() {
        argList.apply {
            add("--config-dir=${configDir?:project.layout.buildDirectory.file("omegat").get()}")
            projectDir?.let { add(it) }
        }
        super.exec()
    }
}
