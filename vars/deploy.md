镜像为[ "helm", ".env获得工具" ]

选项有 [ app, cluster ]

通过app名称寻找对应的chart

选择版本发布至cluster



// def parallelRun() {
//     // // parallel
//     // def jobs = ["JobA", "JobB", "JobC"]

//     // def parallelStagesMap = jobs.collectEntries {
//     //     // Dynamic generate stage
//     //     ["${it}" : generateStage(it)]
//     // }

//     // parallel parallelStagesMap
// }


// def generateStage(job) {
//     return {
//         stage("stage: ${job}") {
//             echo "1: This is ${job}."
//             sleep 15
//         }
//     }
// }