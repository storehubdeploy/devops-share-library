package com.libs.util

def parallelRun(def jobs = ["JobA", "JobB", "JobC"]) {
    // parallel
    def parallelStagesMap = [:]

    jobs.each { index, item ->
        def stage_name = item.name ?: "thread_${index}"

        parallelStagesMap[stage_name] = {
            generateStage(item)
        }
    }

    parallel parallelStagesMap
}

def generateStage(def item) {
    return {
        stage("stage: ${item.name}") {
            item.task
        }
    }
}

