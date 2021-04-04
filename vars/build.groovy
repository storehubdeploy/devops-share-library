import com.libs.ContainerTemplate

def call(BUILD_SLAVE=null, timeoutMinutes = 60 ) {
    def label = "k8sagent-${UUID.randomUUID().toString()}"

    def containerTemplates = ContainerTemplate.Create(BUILD_SLAVE)

    podTemplate(label: label, containers: containerTemplates, volumes: [emptyDirVolume(mountPath: '/home/jenkins', memory: true)], imagePullSecrets: []) {
        node(label) {
            timeout(time: timeoutMinutes, unit: 'MINUTES') {
                echo "the pipeline is executed in a k8s agent " + label + "!!!"
            }

        }
    }
}