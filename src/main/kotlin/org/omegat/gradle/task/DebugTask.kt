package org.omegat.gradle.task

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class DebugTask : RunTask() {
     private val objectFactory: ObjectFactory = project.objects

     @Input
     val debugPort: Property<Int> = objectFactory.property(Int::class.java)

     @TaskAction
     override fun exec() {
          jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=$debugPort")
          super.exec()
     }
}