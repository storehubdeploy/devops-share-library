import com.libs.PodTemplates
import com.libs.Log

def call(BUILD_SLAVE=null, timeoutMinutes = 60 ) {
    // Init
    // Logger
    def log = new Log()

    def label = "k8sagent-${UUID.randomUUID().toString()}"
    
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

                }
            }
        }
    }
}

