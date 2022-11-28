# Change Log
All notable changes to this project will be documented in this file.

## [Unreleased]

## [1.5.11] - 2022-11-28

* New! (preview feature) translateStats task with OmegaT 5.8/6.0.

### Fixed
* Fix a bug that failed with file syntax error in 1.5.10

### Added
- Support custom group/product for omegat dependency
- Add translateStats task
- Support "--no-team" omde for run tasks when project property "no-team" is "true"

### Changed
- Change default dependency OmegaT version to v 5.7.1.
- Change runOmegaT/debugOmegaT to install plugin jar file that name is without version number.
  - Override jar file when change commit on multiple execution.
  - This avoid a case OmegaT load a jar file of previous versions.
- Use project root  from `project.rootDir.path`

## [1.5.9] - 2022-08-06

### Fixed
- Set DuplicatesStrategy to WARN that does not stop build
  but print warning when trying to copy same path in jar file.

### Changed
- Migrate forge site to codeberg.org
  - Update plugin metadata
- Migrate CI/CD to azure pipelines
- Specify gradleApi() as compileOnly
- Bump versions
  - gradle plugin plugin-publish@1.0.0
  - kotlin(jvm)@1.6.21

## [1.5.7] - 2021-01-09

### Changed
- Does not use project.name as Plugin-Name. 
- plugin.name` property or `omegat { pluginName }` is mandatory.
- Bump Versions
  - gradle@7.3.3
    - plugin-publish@0.19.0
    - kotlin@1.6.10
  - actions
    - setup-java@2.5.0
      - temurin opnejdk 8
    - upload-artifact@2.3.1
    - checkout@2.4.0
- Drop dependency for Dokka
 
## [1.5.3] - 2021-06-08

### FIXED
- CI: release script affected by changing to setup-java@2.1.0

### Added
- Enable dependabot

### Removed
- Remove Plugin-Id property.

### Changed
- Drop configuration of jCenter
- Bump Gralde@6.9
- Bump Kotlin(JVM)@1.5.0
- Bump gradle-plugin-publish@0.14.0
- Bump dokka@1.4.32
- CI: Bump setup-java@2.1.0
- CI: Bump upload-artifact@2.2.3
- CI: Bump checkout@2.3.4


## [1.5.0] - 2021-05-18

### Added
- Add Plugin-Id property.

### Changed
- Retrieve OmegaT and related packages from MavenCentral(moved).

## [1.4.2] - 2021-01-25

### Added
- Add more manifest property keys: Plugin-Link, Plugin-Category, Created-By, and Plugin-Date

### Changed
- Signature of OmegatManifest constructor.


## [1.4.1] - 2021-01-18

### Fixed
- Fix typo in manifest key Plugin-Name

## [1.4.0] - 2021-01-18

### Added
- Support plugins properties information in jar manifest(#5)

## [1.3.2] - 2021-01-13

### Changed
- Open OmT project When `omegat.projectDir` specified for `runOmegaT` task 

## [1.3.1] - 2021-01-10

### Fixed
- Suppress annotation error

## [1.3.0] - 2021-01-10

### Changed
- Use Kotlin to implement the plugin.
- Support launching OmegaT tasks `runOmegaT` and `debugOmegaT`
- Add dependency for vldocking.

## [1.2.3] - 2020-12-27

### Changed
- Use Kotlin for build script

## [1.2.2] - 2020-12-26

### Fixed
- Fix plugin load error

### Added
- Add manifest configuration
- Introduce 'packIntoJar' configuration

## [1.1.1] - 2020-12-23

### Added
- Support OmegaT plugin development
- make OmegaT version configurable
- Add dependency as compileOnly and testImplementation

### Deleted
- Drop signatory from build script

## Fixed
- Fix publish automation
- Fix vcsUrl for publish

## [1.0.0] - 2020-12-18

### Changed
- Reboot the project.
- Change plugin name 'org.omegat.gradle'
- Use Gradle-publish plugin
- Drop signing when publish
- Bump up OmegaT 5.4.1
- Bump up Gradle 6.7.1
- CI: Github actions

## [0.9.5] - 2017-4-29
## [0.9.4] - 2017-2-22
## [0.9.1] - 2016-9-25


[Unreleased]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.5.11...HEAD
[1.5.11]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.5.9...v1.5.11
[1.5.9]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.5.8...v1.5.9
[1.5.8]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.5.7...v1.5.8
[1.5.7]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.5.3...v1.5.7
[1.5.3]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.5.0...v1.5.3
[1.5.0]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.4.2...v1.5.0
[1.4.2]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.4.1...v1.4.2
[1.4.1]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.4.0...v1.4.1
[1.4.0]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.3.2...v1.4.0
[1.3.2]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.3.1...v1.3.2
[1.3.1]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.3.0...v1.3.1
[1.3.0]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.2.0...v1.3.0
[1.2.0]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.1.1...v1.2.0
[1.1.1]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v1.0.0...v1.1.1
[1.0.0]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v0.9.5...v1.0.0
[0.9.5]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v0.9.4...v0.9.5
[0.9.4]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v0.9.1...v0.9.4
[0.9.1]: https://codeberg.org/miurahr/gradle-omegat-plugin/compare/v0.9.0...v0.9.1
