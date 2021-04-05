package com.libs

def gitFetch() {
    stage 'Git Fetch'
    echo "Start fetching code from git project: " + GIT_PROJECT + " using the docker project: ${DOCKER_REPO}"

    def gitDepth = (new ConfigHelper()).GetGitDepth()
    def scmResult

    retry(3) {
        timeout(time: ConstVars.GitTimeout , unit: 'SECONDS') {
            scmResult = checkout([$class                           : 'GitSCM',
                                  branches                         : [[name: GIT_BRANCH]],
                                  doGenerateSubmoduleConfigurations: false,
                                  extensions                       : [[$class: 'CloneOption', noTags: true, reference: '', shallow: true, depth: gitDepth]],
                                  submoduleCfg                     : [],
                                  userRemoteConfigs                : [[credentialsId: 'bitbucket-2', url: 'ssh://git@altssh.bitbucket.org:443/iherbllc/' + GIT_PROJECT + '.git']]])

            echo "Test CI diff features"
            //sh script: 'git diff-tree -m -r --name-only HEAD'
        }
    }

    return scmResult
}