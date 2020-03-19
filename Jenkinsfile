def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/mule/master" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                        "mavenCompileGoal" : "clean install -U -DskipTests -Darchetype.test.skip",
                        "mavenTestGoal" : "clean verify",
                        "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
