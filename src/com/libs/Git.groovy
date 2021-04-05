package com.libs

def gitFetch() {
    def git = [ Depth: 1,
                Timeout: 600,
                Result: "",
                Credential: "45ffa5c8-48bf-4c18-b40f-334bc25d0c56",
                Url: "https://github.com/storehubdeploy/"
              ]

    stage 'Git Fetch'
    log.title("Start fetching code from git project: ${GIT_PROJECT} using the docker project: ${DOCKER_REPO}")

    retry(3) {
        timeout(time: git.Timeout , unit: 'SECONDS') {
            git.Result = checkout([$class                           : 'GitSCM',
                                   branches                         : [[name: GIT_BRANCH]],
                                   doGenerateSubmoduleConfigurations: false,
                                   extensions                       : [[$class: 'CloneOption', noTags: true, reference: '', shallow: true, depth: git.Depth]],
                                   submoduleCfg                     : [],
                                   userRemoteConfigs                : [[credentialsId: git.Credential, url: git.Url + GIT_PROJECT + '.git']]])
        }
    }

    return git.Result
}