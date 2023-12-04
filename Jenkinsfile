Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenCompileGoal" : "clean install -U -DskipTests -Darchetype.test.skip",
                       "mavenTestGoal" : "clean install",
                       "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
