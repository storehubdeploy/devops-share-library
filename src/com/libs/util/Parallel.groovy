package com.libs.util

def executeBuildParallel(def obj) {

    def maxParallelBuildCount = 5
    echo "docker will build images with ${maxParallelBuildCount} threads"

    def buildImageTasks = []
    for (def imageTmp : obj.images) {
        def image = imageTmp
        buildImageTasks.add {
            executeBuildTask()
        }
    }
    parallelRun("build_image", buildImageTasks, maxParallelBuildCount)
}

def executeBuildTask() {
    echo "start"
    sleep 30
    echo "end"
}

def parallelRun(def taskTypePrefix,
                def tasks,
                def maxParallelCount) {
    def index = 0
    def dic = [:]

    tasks.each { task ->
        def group = index % maxParallelCount
        def tasksInOneGroup = []
        if (!dic.containsKey(group)) {
            tasksInOneGroup.add(task)
            dic.put(group, tasksInOneGroup)
        } else {
            dic[group].add(task)
        }
        index++
    }

    def runDic = [:]
    dic.each { key, value ->
        def mapKey = "thread_${key}"
        runDic[mapKey] = {
            runTask(value)
        }
    }
    parallel runDic
}

def runTask(def taskArr) {
    for (def task : taskArr) {
        task()
    }
}