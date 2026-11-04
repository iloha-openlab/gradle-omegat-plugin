import java.io.FileInputStream
import java.util.Properties

plugins {
    signing
    kotlin("jvm") version "2.4.21"
    id("java-gradle-plugin")  // for plugin authoring
    id ("maven-publish")  // for metadata
    id("com.gradle.plugin-publish") version "2.2.1"  // to publish to plugin portal
    id("com.palantir.git-version") version "3.0.0" apply false
}

val dotgit = project.file(".git")
if (dotgit.exists()) {
    apply(plugin = "com.palantir.git-version")
    val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
    val details = versionDetails()
    val baseVersion = details.lastTag.substring(1)
    version = when {
        details.isCleanTag -> baseVersion
        else -> baseVersion + "-" + details.commitDistance + "-" + details.gitHash + "-SNAPSHOT"
    }
} else {
    val gitArchival = project.file(".git-archival.properties")
    val props = Properties()
    props.load(FileInputStream(gitArchival))
    val versionDescribe = props.getProperty("describe")
    val regex = "^v\\d+\\.\\d+\\.\\d+$".toRegex()
    version = when {
        regex.matches(versionDescribe) -> versionDescribe.substring(1)
        else -> versionDescribe.substring(1) + "-SNAPSHOT"
    }
}

group = "org.omegat"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly(gradleApi())
}

gradlePlugin {
    vcsUrl.set("https://codeberg.org/miurahr/gradle-omegat-plugin.git")
    website.set("https://codeberg.org/miurahr/gradle-omegat-plugin")
    plugins.create("omegatPlugin") {
        id = "org.omegat.gradle"
        implementationClass = "org.omegat.gradle.OmegatPlugin"
        displayName = "OmegaT"
        tags.set(listOf("OmegaT", "translation", "plugin development"))
        description = "OmegaT plugin for Gradle allow users to run translation tasks with gradle," +
                " which intend to help task automation on CI/CD platform such as Github actions, or travis-ci." +
                "The plugin also provide a dependency management, manifest generation, omegat runner with debugger" +
                " and fat-jar generation for omegat-plugin development."
    }
}

// publish plugin into local repository
publishing {
  publications.withType(MavenPublication::class).all {
    if (name == "pluginMaven") {
      artifactId = "gradle-omegat-plugin"
    }
  }
}

val signKey = listOf("signingKey", "signing.keyId", "signing.gnupg.keyName").find {project.hasProperty(it)}
tasks.withType<Sign> {
    onlyIf { signKey != null && !project.version.toString().endsWith("-SNAPSHOT") }
}

signing {
    when (signKey) {
        "signingKey" -> {
            val signingKey: String? by project
            val signingPassword: String? by project
            useInMemoryPgpKeys(signingKey, signingPassword)
        }

        "signing.keyId" -> {
            val keyId: String? by project
            val password: String? by project
            val secretKeyRingFile: String? by project // e.g. gpg --export-secret-keys > secring.gpg
            useInMemoryPgpKeys(keyId, password, secretKeyRingFile)
        }

        "signing.gnupg.keyName" -> {
            useGpgCmd()
        }
    }
}
