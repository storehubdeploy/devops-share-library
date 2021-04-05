// import com.libs.util.Parallel
import com.libs.PodTemplates
// import com.libs.util.YamlParser
// import com.libs.exe.Git

def call(GIT_BRANCH=null,  GIT_PROJECT=null, DOCKER_REPO=null, BUILD_SLAVE=null , buildYaml = "build.yaml", timeoutMinutes = 60 ) {
    // Init
    def label = "k8sagent-${UUID.randomUUID().toString()}"
    def buildEnv = ["GIT_BRANCH=${GIT_BRANCH}",
                    "GIT_PROJECT=${GIT_PROJECT}",
                    "DOCKER_REPO=${DOCKER_REPO}",
                    "BUILD_NUMBER=${BUILD_NUMBER}"
                   ]
    
    // Generate podTemplate
    // https://plugins.jenkins.io/kubernetes/
    // Pod and container template configuration
    def containers = new PodTemplates().Create(BUILD_SLAVE)
    podTemplate(label: label, containers: containers, 
                volumes: [emptyDirVolume(mountPath: '/home/jenkins', memory: true)], 
                imagePullSecrets: [], 
                showRawYaml: false) 
    {
        node(label) {
            // https://plugins.jenkins.io/ansicolor/
            ansiColor('xterm') {
                timeout(time: timeoutMinutes, unit: 'MINUTES') {
                    // log.title("the pipeline is executed in a k8s agent " + label + "!!!")
                    echo "the pipeline is executed in a k8s agent " + label + "!!!"
                    // withEnv(buildEnv) {
                    //     try {
                    //         startPipeline(buildYaml)
                    //     } catch (exp) {
                    //         error "[ERROR] Program failed, please read logs..." + exp
                    //     }
                    // }
                }
            }
        }
    }
}

// def startPipeline(def buildYaml = "build.yaml") {
//     def tasks = [:]
//     // def git = new Git()

//     container('jnlp') {
//         log.title("startPipeline")

//         scmInfo = gitFetch()

//         // tasks = getBuildTasks(buildYaml)

//         // for (def task : tasks) {
//         //     def newParallel = new Parallel()
//         //     if(task.kind == "Docker") {
//         //         log.title("executing docker push")
//         //         newParallel.executeBuildParallel(task)
//         //     }

//         // }

//     }
// }

// def getBuildTasks(def buildYaml = "build.yaml") {
//     try {
//         def namedTasks = [:]

//         def yamlText = readFile([file: buildYaml])
//         def tasks = YamlParser.loadYaml(yamlText, "tasks")

//         for (def task in tasks) {
//             if (task.name && namedTasks.containsKey(task.name)) {
//                 namedTasks[task.name] << task
//             } else if (task.name) {
//                 namedTasks[task.name] = [task]
//             }
//         }

//         return namedTasks
//     } catch (err) {
//         log.error "BuildYaml err:$err.message()"
//         throw e
//     }
// }
// def gitFetch() {
//     def gitParams = [ Depth: 1,
//                 Timeout: 600,
//                 Result: "",
//                 Credential: "45ffa5c8-48bf-4c18-b40f-334bc25d0c56" 
//                 Url: "https://github.com/storehubdeploy/"
//               ]

//     stage 'Git Fetch'
//     log.title("Start fetching code from git project: ${GIT_PROJECT} using the docker project: ${DOCKER_REPO}")

//     retry(3) {
//         timeout(time: gitParams.Timeout , unit: 'SECONDS') {
//             gitParams.Result = checkout([$class                           : 'GitSCM',
//                                    branches                         : [[name: GIT_BRANCH]],
//                                    doGenerateSubmoduleConfigurations: false,
//                                    extensions                       : [[$class: 'CloneOption', noTags: true, reference: '', shallow: true, depth: gitParams.Depth]],
//                                    submoduleCfg                     : [],
//                                    userRemoteConfigs                : [[credentialsId: gitParams.Credential, url: gitParams.Url + GIT_PROJECT + '.git']]])
//         }
//     }

//     return gitParams.Result
// }
