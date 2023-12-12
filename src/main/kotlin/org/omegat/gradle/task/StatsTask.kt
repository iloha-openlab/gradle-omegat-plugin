package org.omegat.gradle.task

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class StatsTask : BaseTask()  {

    @TaskAction
    override fun exec() {
        argList.apply {
            add("--mode=console-stats")
            add("--stats-type=json")
            add("--output-file=omegat/project_stats.json")
            if (!"true".equals(team, true)) {
                add("--no-team")
            }
            add("--config-dir=${configDir}")
            add(projectDir?:rootDir)
        }
        super.exec()
    }
}