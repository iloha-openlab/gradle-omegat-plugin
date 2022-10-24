package org.omegat.gradle.config

import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.api.tasks.util.PatternFilterable


open class PluginExtension(project: Project) {
    var projectDir: String? = null
    var version: String? = null
    var pluginClass: String? = null
    var debugPort: Int? = null

    val manifest: OmegatManifest = OmegatManifest(project)

    var omegatGroup: String? = null
    var omegatProduct: String? = null

  /**
   * When packing the dependencies of the `packIntoJar` configuration into the distribution *.jar,
   * this closure is applied to the file tree. If you want to exclude certain files in your dependencies into
   * your release, you can modify this.
   * By default, the `/META-INF/` directory of dependencies is discarded.
   *
   * **Default value:** `{ it.exclude("META-INF/**/*") }`
   * @see org.gradle.api.file.FileTree.matching(groovy.lang.Closure)
   * @see PatternFilterable
   */
    var packIntoJarFileFilter: (PatternFilterable) -> PatternFilterable = {
        it.exclude("META-INF/**/*", "module-info.class")
    }

    /**
     * Set the [packIntoJarFileFilter] with a Groovy [Closure]
     */
    fun packIntoJarFileFilter(closure: Closure<PatternFilterable>) {
        packIntoJarFileFilter = { closure.call(it) }
    }
}
