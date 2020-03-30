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
        
        stage('Setup compose environmet') {
            steps {
                echo "Building backend image ${backendVersion}"
                sh "ls"
                sh "docker build -t ${backendVersion} ."
                echo "Generate docker-compose file"
                sh "sed -i 's@{{BACKEND_DOCKER_IMAGE}}@${backendVersion}@g' docker-compose.dist"
                sh 'cat docker-compose.dist'
                sh "docker-compose -f docker-compose.dist up -d"
                sh "sleep 10"
                sh "docker-compose -f docker-compose.dist ps"
            }
        }
        
        stage("Test") {
            steps {
                echo "Testing postman-newman"
                sh "ls"
                dir("${env.WORKSPACE}/newman") {
                    sh 'newman run mitocode-calculator.postman_collection.json -e mitocode_calculator.postman_environment.json --reporters cli,junit --reporter-junit-export newman.xml --insecure'
                    junit 'newman.xml'
                }
            }
        }
    }

    post {
        always {
            echo "Down ephemeral environment...."
            sh "docker-compose -f docker-compose.dist down"
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