# Overview

[![Build Status](https://dev.azure.com/miurahr/CodeBerg/_apis/build/status/CodeBerg-gradle-omegat-plugin?branchName=main)](https://dev.azure.com/miurahr/CodeBerg/_build/latest?definitionId=27&branchName=main)

The OmegaT gradle plugin allows you to generate translation files from an original source text with TMX translation memory DB.
It also helps OmegaT plugin developers to make custom plugin easily. 

# Getting Started 

There are two task types in the plugin.

1. Tasks on translation project

2. Tasks for OmegaT plugin development project


## Generate translation on OmegaT team project

Please follow the below steps to add the Gradle OmegaT Plugin to your Gradle build script.

### Step 1: Apply the plugin to your Gradle script

To apply the plugin, please add one of the following snippets to your `build.gradle` file:

```groovy
plugins {
    id 'org.omegat.gradle' version '1.6.0'
}
```
or `build.gradle.kts` in Kotlin;

```kotlin
plugins {
    id("org.omegat.gradle") version "1.6.0"
}
```

### Step 2: (optional) `omegat` configuration closure to your `build.gradle` file

With this configuration, you can put build.gradle file on other than team project root where `omegat.project` file located.

```groovy
omegat {
    projectDir "path/to/omegat/team/project"
}
```
or kotlin
```kotlin
omegat {
    projectDir = "path/to/omegat/project"
}
```

If not specified projectDir, the OmegaT plugin assumes a gradle project root is an omegat project.

#### Advanced

When you want to custom omegat release other than genuine distribution for specific purpose,
you can specify it using custom properties.

```groovy
omegat {
    projectDir "path/to/omegat/team/project"
    version = '5.8.0-SNAPSHOT'  // OmegaT version
    omegatGroup = "custom.group"  // default: org.omegat
    omegatProduct = "customProduct"  // default: omegat
}
```

In this example, you can specify "custom.group:customProduct:5.8.0-SNAPSHOT" for dependency.

###  Call translate task

```bash
$ ./gradlew translate
```

This will generate a translation result in OmegaT target directory.


### Configure translate task

```groovy
tasks.translate {
    team = "true"
    configDir = "/home/example/omegat"
}
```


## Development of a custom OmegaT plugin

### Step 1: Apply the plugin to your Gradle script

To apply the plugin, please add one of the following snippets to your `build.gradle` file:

```groovy
plugins {
    id 'org.omegat.gradle' version '1.6.0'
}
```
or in kotlin
```kotlin
plugins {
    id("org.omegat.gradle") version "1.6.0"
}
```

### Step 2: `omegat` configuration closure to your `build.gradle` file

When you want to use the omegat gradle plugin for omegat plugin development, there are several
mandatory configurations. 

for groovy DSL

```groovy
omegat {
    pluginClass = "your.plugin.package.and.className" // mandatory for plugin development
    version = "5.8.0" // (optional) Build against OmegaT version;
                      // available versions(as in Apr. 2023): 5.4.4, 5.5.0, 5.6.0, 5.7.1, 5.8.0, 6.0.0
    debugPort = 5566 // (optional) when you use a java debugger, no need when use IDEs
}
```

for kotlin DSL

```kotlin
omegat {
    pluginClass = "your.plugin.package.and.className" // mandatory for plugin development
    version = "5.8.0" // (optional) Build against OmegaT version;
                      // available versions(as in Apr. 2023): 5.4.4, 5.5.0, 5.6.0, 5.7.1, 5.8.0
                      // Will soon arrive: 5.8.1, 6.0.0, 6.0.1
    debugPort = 5566 // (optional) when you use a java debugger, no need when use IDEs
}
```

The plugin automatically configures a gradle project to depend on a specified version of OmegaT.
When there is an option `debugPort`, a task named `debugOmegaT` is created which run OmegaT
with a jvm debugger port.

When launching `runOmegaT` or `debugOmegaT`, the project will build jar file and place
plugin into temporal configuration folder `build/omegat/plugins` then launch OmegaT
and open OmegaT project at configured as `omegat.projectDir`

### Step 3. Configure dependencies

You can put dependencies with packIntoJar configuration, dependencies are bundled with plugin as Fat-Jar.
Libraries other than packIntoJar such as implementation, compile, etc. are used to compile but not bundled.
The following example illustrates how to use, and jackson is going to be bundled, and a commons-io library is not.
It is because commons-io is a dependency of OmegaT, so we can use it without bundled.

```groovy
dependencies {
    packIntoJar 'com.fasterxml.jackson.core:jackson:2.13.1'
    implementation 'commons-lang:commons-lang:2.6'
    // ...
}
```

or in kotlin example, it bundles jar files placed in `lib` sub-folder;

```kotlin
dependencies {
    packIntoJar("com.fasterxml.jackson.core:jackson:2.13.1")
    packIntoJar(fileTree("lib") {include("*.jar")})
    implementation("commons-lang:commons-lang:2.6")
    // ...
}
```

### Step 4. Setup plugin properties

There is some plugin information to be managed on OmegaT preference.
You can set it on `gradle.properties` file, and the plugin generates proper manifest records.
Here is an example.

```properties
plugin.name=Plugin Name
plugin.author=Plugin developer name
plugin.category=machinetranslator
plugin.description=A plugin to look up online dictionary
plugin.link=https://codeberg.org/miurahr/omegat-onlinedictionary
plugin.license=GNU General Public License version 3
```

Here is a table how properties become manifest record;

|                    | plugin manifest    | gradle.properties    | standard property   | 
|--------------------|--------------------|----------------------|---------------------|
| Name               | Plugin-Name        | `plugin.name`        | n/a                 | 
| Author             | Plugin-Author      | `plugin.author`      | n/a                 | 
| Description        | Plugin-Description | `plugin.description` | n/a                 | 
| Website            | Plugin-Link        | `plugin.link`        | n/a                 | 
| Category           | Plugin-Category    | `plugin.category`    | n/a                 | 
| Version            | Plugin-Version     | n/a                  | version             | 
| Built environment  | Created-By         | n/a                  | n/a                 | 
| Date               | Plugin-Date        | n/a                  | n/a                 | 
| Class name         | OmegaT-Plugins     | n/a                  | n/a                 | 

- Built environment and date records are automatically added to manifest.
- Class name is configured by extension `omegat.pluginClass` in `build.gradle`.

## Incompatible change

An extension `omegat.pluginName` is deprecated and will be removed in future verion.
You should set `plugin.name` property for the purpose.
