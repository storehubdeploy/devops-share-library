package com.libs

void analyzeWithSonarQubeAndWaitForQualityGoal() {
    withSonarQubeEnv('sonarqube') {
        def analysisParameters = ""
        for (def a : task.analysisOptions) {
            def str = "-D" + a.key + "=" + "\"${a.value}\""
            echo "Added Analysis parameters for sonarqube scan: ${str}"
            analysisParameters += " " + str
        }


        sh """ sonar-scanner \
                -Dsonar.host.url=${SONAR_HOST_URL} \
                -Dsonar.branch.name=${GIT_BRANCH}  \
                -Dsonar.login=${SONAR_AUTH_TOKEN} \
                -Dsonar.scanner.force-deprecated-java-version=true \
                -Dsonar.projectKey=${app} ${analysisParameters}
        """
    }

    sleep 3

    timeout(time: 30, unit: 'SECONDS') {
        def qg = waitForQualityGate()
        if (qg.status == 'OK') {
            echo "[INFO] Code Scan success!"
            break
        } else {
            if (task.enableQualityGateCheck == "true") {
                error "Pipeline failed due to quality gate failure: ${qg.status}"
            } else {
                unstable("Pipeline unstable due to quality gate failure: ${qg.status}")
            }
        }
    }
}


// https://github.com/nosinovacao/dotnet-sonar
// https://github.com/SonarSource/sonar-scanner-cli-docker