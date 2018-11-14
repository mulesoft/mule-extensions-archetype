def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/mule/mule-4.x" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenAdditionalArgs" : "-Pci-environment -Djava.net.preferIPv4Stack=true -Dmule.extension.archetype.testSettings=\${settingsFileForTest}",
                       "additionalConfigFileProviderList" : [configFile(fileId: "mule-runtime-maven-settings-MuleRuntimeFullSettings", variable: "settingsFileForTest") ],
                       "enableMavenTestStage" : false,
                       "enableAllureTestReportStage" : false,
                       "enableMavenDeployStage" : false,
                       "mavenCompileGoal" : "clean install -U"
                       ]

runtimeProjectsBuild(pipelineParams)
