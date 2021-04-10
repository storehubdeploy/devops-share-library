import com.libs.util.PodTemplates
import com.libs.util.Conf
import com.libs.util.Lint

def call(buildYaml = "build.yaml",  timeoutMinutes = 60 ) {
    // Init
    def label = "k8sagent-${UUID.randomUUID().toString()}"
    def buildEnv = ["GIT_BRANCH=${GIT_BRANCH}",
                    "GIT_PROJECT=${GIT_PROJECT}",
                    "DOCKER_REPO=${DOCKER_REPO}",
                    "BUILD_NUMBER=${BUILD_NUMBER}"
                   ]
    
    // Init and lint build.yaml
    def confObj = new Conf().loadYaml("${buildYaml}")
    

    // Generate podTemplate
    // https://plugins.jenkins.io/kubernetes/
    // Pod and container template configuration
    def containers = new PodTemplates().Create(confObj.env.containers)
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