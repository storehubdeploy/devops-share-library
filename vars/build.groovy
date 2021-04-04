import com.libs.ContainerTemplate

def call(BUILD_SLAVE=null, timeoutMinutes = 60 ) {
    def label = "k8sagent-${UUID.randomUUID().toString()}"

    def containers = new ContainerTemplate().Create(BUILD_SLAVE)

    podTemplate(cloud: 'kubernetes', label: label, containers: containers) {
    // podTemplate(cloud: 'kubernetes', label: label, containers: containers, volumes: [emptyDirVolume(mountPath: '/home/jenkins', memory: true)], imagePullSecrets: []) {
        node(label) {
            timeout(time: timeoutMinutes, unit: 'MINUTES') {
                echo "the pipeline is executed in a k8s agent " + label + "!!!"
            }

        }
    }
}

