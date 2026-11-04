pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
        timeout(time: 15, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source repository...'
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling Quote API...'
                sh './gradlew compileJava || mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh './gradlew test || mvn test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/build/test-results/**/*.xml,**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging application artifact...'
                sh './gradlew bootJar || mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts allowEmptyArchive: true, artifacts: '**/build/libs/*.jar,**/target/*.jar'
                }
            }
        }
    }

    post {
        success {
            echo '======================================'
            echo '  BUILD AND TEST SUCCESSFUL!         '
            echo '======================================'
        }
        failure {
            echo '======================================'
            echo '  BUILD FAILED!                      '
            echo '======================================'
        }
    }
}
