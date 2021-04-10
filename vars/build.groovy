import com.libs.util.PodTemplates

def call(GIT_BRANCH=null,  GIT_PROJECT=null, DOCKER_REPO=null, BUILD_SLAVE=null , buildYaml = "build.yaml", timeoutMinutes = 60 ) {
    // Init
    def label = "k8sagent-${UUID.randomUUID().toString()}"
    def buildEnv = ["GIT_BRANCH=${GIT_BRANCH}",
                    "GIT_PROJECT=${GIT_PROJECT}",
                    "DOCKER_REPO=${DOCKER_REPO}",
                    "BUILD_NUMBER=${BUILD_NUMBER}"
                   ]
    
    def yamlObj = new Conf().loadYaml("${WORKSPACE}/${buildYaml}")
    echo(yamlObj.name)

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
                    log.title("Start Pipeline")
                    withEnv(buildEnv) {
                        body(buildYaml)
                    }
                }
            }
        }
    }
}