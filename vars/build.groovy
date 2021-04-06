import com.libs.util.Parallel
import com.libs.util.PodTemplates
import com.libs.util.YamlParser
import com.libs.exe.Git

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
                    log.title("the pipeline is executed in a k8s agent " + label + "!!!")
                    withEnv(buildEnv) {
                        try {
                            startPipeline(buildYaml)
                        } catch (exp) {
                            error "[ERROR] Program failed, please read logs..." + exp
                        }
                    }
                }
            }
        }
    }
}

def startPipeline(def buildYaml = "build.yaml") {
    def tasks = [:]
    def git = new Git()

    container('jnlp') {
        log.title("startPipeline")

        scmInfo = git.gitFetch()

        tasks = getBuildTasks(buildYaml)

        echo(tasks) 

        // for (def task : tasks) {
        //     def newParallel = new Parallel()
        //     if(task.kind == "Docker") {
        //         log.title("executing docker push")
        //         newParallel.executeBuildParallel(task)
        //     }

        // }

    }
}

def getBuildTasks(def buildYaml = "build.yaml") {
    try {
        def namedTasks = [:]
        def m_dict = [:]

        def yamlObj = YamlParser.loadYaml(buildYaml)
        def tasks = yamlObj["tasks"]

        for (def task in tasks) {
            if (task.name && namedTasks.containsKey(task.name)) {
                namedTasks[task.name] << task
            } else if (task.name) {
                namedTasks[task.name] = [task]
            }
        }

        return namedTasks
    } catch (err) {
        log.error "BuildYaml err:$err.message()"
        throw e
    }
}



// def ReplaceWithRegex(def text, def pattern, def dict) {
//     def matcher = text =~ pattern
//     def replacedText = text
//     //echo "matcher: ${matcher}, match count: ${matcher.count}"
//     for (i in 0..<matcher.count) {
//         //echo "Replace '${matcher[i][0]}', to '${dict.(matcher[i][1]).toString()}'"
//         replacedText = replacedText.replace(matcher[i][0], dict.(matcher[i][1]).toString())
//     }
//     return replacedText
// }
