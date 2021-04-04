import com.libs.ContainerTemplate

@Simple 
class Container {
   String name
   String image
}
 
def call(timeoutMinutes = 60 ) {
    def label = "k8sagent-${UUID.randomUUID().toString()}"

    def xxxxx = []
    xxxxx.add(new Container(name: "test1",image:"docker:stable-dind")
    xxxxx.add(new Container(name: "test2",image:"dtzar/helm-kubectl:2.12.0")
    def containerTemplates = ContainerTemplate.Create(xxxxx)

    podTemplate(label: label, containers: containerTemplates, volumes: [emptyDirVolume(mountPath: '/home/jenkins', memory: true)], imagePullSecrets: []) {
        node(label) {
            timeout(time: timeoutMinutes, unit: 'MINUTES') {
                echo "the pipeline is executed in a k8s agent " + label + "!!!"
            }

        }
    }
}