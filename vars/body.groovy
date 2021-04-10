import com.libs.util.Parallel
import com.libs.util.Conf
import com.libs.exe.Git

def call(def buildYaml = "build.yaml") {
    def tasks = [:]
    def git = new Git()

    stages("Prepare") {
        container('jnlp') {
            scmInfo = git.gitFetch()

            def yamlObj = new Conf().loadYaml("${WORKSPACE}/${buildYaml}")
            echo(yamlObj.name)

        }
    }
}



            // tasks = getBuildTasks(buildYaml)

            // echo(tasks) 

            // for (def task : tasks) {
            //     def newParallel = new Parallel()
            //     if(task.kind == "Docker") {
            //         log.title("executing docker push")
            //         newParallel.executeBuildParallel(task)
            //     }

            // }


// def getBuildTasks(def buildYaml = "build.yaml") {
//     try {
//         def namedTasks = [:]
//         def m_dict = [:]

//         def yamlObj = new Conf.loadYaml("${WORKSPACE}/${buildYaml}")
//         def tasks = yamlObj["tasks"]

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