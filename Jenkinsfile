def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/mule-extensions-api/master",
                                "Mule-runtime/mule/mule-4.x" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                        // TODO: Remove this to start using Maven 3.5.x
                        "mavenTool" : "M3" ]

runtimeProjectsBuild(pipelineParams)
