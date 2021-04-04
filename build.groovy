import com.libs.ContainerTemplate

def call(timeoutMinutes = 60 ) {
    def label = "k8sagent-${UUID.randomUUID().toString()}"

    containers = [{name:"test1",image:"docker:stable-dind"},{name:"test2",image:"dtzar/helm-kubectl:2.12.0"},{name:"test3",image:"java:openjdk-8u111-jre-alpine"}]
    containerTemplates = ContainerTemplate.Create(containers)

    podTemplate(label: label, containers: containerTemplates, volumes: [emptyDirVolume(mountPath: '/home/jenkins', memory: true)], imagePullSecrets: []) {
        node(label) {
            timeout(time: timeoutMinutes, unit: 'MINUTES') {
                echo "the pipeline is executed in a k8s agent " + label + "!!!"
                stages {
                    stage('Main') {
                        steps {
                            echo 'Hello World'
                            echo "${BUILD_URL}/consoleText"
                        }
                    }
                }
            }

        }
    }
}