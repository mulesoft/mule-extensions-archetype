def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/mule/support/4.2.x" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenCompileGoal" : "clean install -U -DskipTests -Darchetype.test.skip",
                       "mavenTestGoal" : "clean install",
                       "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
