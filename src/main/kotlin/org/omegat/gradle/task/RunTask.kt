package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction
import java.io.File

open class RunTask : BaseTask()  {
    @TaskAction
    override fun exec() {
        argList.add("--config-dir=${File(project.buildDir, "omegat")}")
        projectDir?.let { argList.add(it) }
        super.exec()
    }
}
