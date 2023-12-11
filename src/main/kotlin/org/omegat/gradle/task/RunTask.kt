package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction
import java.io.File

open class RunTask : BaseTask()  {
    @TaskAction
    override fun exec() {
        argList.apply {
            when {
                configDir != null -> add("--config-dir=${configDir!!.get()}")
                else -> add("--config-dir=${File(project.buildDir, "omegat")}")
            }
            projectDir?.let { add(it.get().toString()) }
        }
        super.exec()
    }
}
