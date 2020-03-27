backendVersion = '0.0.0'
frontendVersion = '0.0.0'

pipeline {

    agent any

    environment {
        // sonar parameters
        //SONAR_URL = "${params.SONAR_URL}"
        //SONAR_PROJECT = "${params.SONAR_PROJECT}"
        //SONAR_LOGIN = "${params.SONAR_LOGIN}"
        //SONAR_ORGANIZATION = "${params.SONAR_ORGANIZATION}"
        // selenium parameters
        //SELENIUM_HOST = "${params.SELENIUM_HOST}"
        // internal parameters (docker)
        EPHEMERAL_HOST = "${params.EPHEMERAL_HOST}"
        CONTAINER_BACKEND_PATH = "${params.CONTAINER_BACKEND_PATH}"
        //CONTAINER_FRONTEND_PATH = "${params.CONTAINER_FRONTEND_PATH}"
        //API_EPHEMERAL_URL = "http://${EPHEMERAL_HOST}:9998"
        // external test parameters
        //API_URL_CUCUMBER = "${API_EPHEMERAL_URL}"
        //SELENIUM_HUB_URL = "http://${EPHEMERAL_HOST}:4444/wd/hub"
        //SELENIUM_APP_URL = "http://${EPHEMERAL_HOST}:9999"
    }

    stages {
        stage('Prepare backend version') {
            agent {
                docker { image 'maven:3.6.3-jdk-11-slim' }
            }
            steps {
                echo "Getting backend version with maven"
                echo "Before ${backendVersion}"
                sh 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout > backend.txt'
                script {
                    backendVersion = "${CONTAINER_BACKEND_PATH}:" + readFile('backend.txt').trim() + "-" + env.BUILD_NUMBER
                }
                echo "After ${backendVersion}"
            }
        }
    }

    post {
        always {
            echo "Down ephemeral environment...."
            //sh "docker-compose -f docker-compose.dist down"
        }

        success {
            echo "success"
        }

        unstable {
            echo "unstable"
        }

        failure {
            echo "failure"
        }

        changed {
            echo "changed"
        }
    }
}