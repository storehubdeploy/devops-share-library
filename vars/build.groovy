import com.libs.PodTemplates

def call(BUILD_SLAVE=null, timeoutMinutes = 60 ) {
    def label = "k8sagent-${UUID.randomUUID().toString()}"


    // 
    def containers = new PodTemplates().Create(BUILD_SLAVE)

    podTemplate(label: label, containers: containers, 
                volumes: [emptyDirVolume(mountPath: '/home/jenkins', memory: true)], 
                imagePullSecrets: [], 
                showRawYaml: false) 
    {
        node(label) {
            timeout(time: timeoutMinutes, unit: 'MINUTES') {
                echo "the pipeline is executed in a k8s agent " + label + "!!!"
            }

        }
    }
}

