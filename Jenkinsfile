def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/mule/mule-4.x" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenAdditionalArgs" : "-Pci-environment -Djava.net.preferIPv4Stack=true" ]

runtimeProjectsBuild(pipelineParams)
