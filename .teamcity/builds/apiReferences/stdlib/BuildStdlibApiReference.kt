package builds.apiReferences.stdlib

import jetbrains.buildServer.configs.kotlin.*
import builds.apiReferences.kotlinx.coroutines.KotlinxCoroutinesBuildApiReference

object BuildStdlibApiReference : BuildType({
  name = "Stdlib Api reference"

  artifactRules = "latest-version.zip"

  params {
    param("%apiTemplatesBranch%", "ktl-696-dokka-stdlib")
    param("revers.deps.*.templatesBranch", "%templatesBranch%")
  }

  dependencies {
    dependency(AbsoluteId("Kotlin_KotlinRelease_1820_LibraryReferenceLatestDocs")) {
      snapshot {
        reuseBuilds = ReuseBuilds.NO
        onDependencyFailure = FailureAction.FAIL_TO_START
      }
      artifacts {
        cleanDestination = true
        artifactRules = "latest-version.zip"
      }
    }
  }
})