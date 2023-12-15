package org.omegat.gradle.config

import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.util.PatternFilterable


open class PluginExtension(project: Project) {
    val manifest: OmegatManifest = OmegatManifest(project)

    private val objectFactory: ObjectFactory = project.objects
    val version: Property<String> = objectFactory.property(String::class.java)
    val pluginClass: Property<String> = objectFactory.property(String::class.java)
    val debugPort: Property<Int> = objectFactory.property(Int::class.java)
    val omegatGroup: Property<String> = objectFactory.property(String::class.java)
    val omegatProduct: Property<String> = objectFactory.property(String::class.java)

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

    fun version(value: String) {
        version.set(value)
    }

    fun pluginClass(className: String) {
        pluginClass.set(className)
    }

    fun pluginClass(className: Provider<String>) {
        pluginClass.set(className)
    }

    fun debugPort(port: Int) {
        debugPort.set(port)
    }

    fun omegatGroup(value: String) {
        omegatGroup.set(value)
    }

    fun omegatProduct(value: String) {
        omegatProduct.set(value)
    }

    fun omegatVersion(value: String) {
        version.set(value)
    }
}
