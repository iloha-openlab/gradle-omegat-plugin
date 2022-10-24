package org.omegat.gradle.task

import org.gradle.api.tasks.TaskAction

open class StatsTask: BaseTask()  {
    @TaskAction
    override fun exec() {
        if (projectDir == null) { // set default project root
            projectDir = project.rootDir.path
        }
        argList.apply {
            add("--mode=console-stats")
            add("--stats-type=json")
            add("--output-file=omegat/project_stats.json")
            add(projectDir!!)
        }
        super.exec()
    }
}