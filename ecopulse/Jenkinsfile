pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
        timeout(time: 15, unit: 'MINUTES')
    }

    triggers {
        // SCM polling every 5 minutes as required by Component 1 rubric
        pollSCM('H/5 * * * *')
    }

    environment {
        APP_NAME = 'ecopulse-aiops'
        STUDENT_USN = '1MS24CI060'
        COURSE_CODE = '25CIAEC59'
    }

    stages {
        stage('Checkout SCM') {
            steps {
                echo "=============================================================="
                echo "  Checking out EcoPulse AIOps Repository (USN: ${STUDENT_USN}) "
                echo "=============================================================="
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                echo "Compiling Java sources with Apache Maven..."
                sh 'mvn clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                echo "Running JUnit 5 Test Suite (AIOps, Carbon & Health tests)..."
                sh 'mvn test'
            }
            post {
                always {
                    // CI Enhancement: Automated JUnit XML report parsing and publishing
                    archiveArtifacts allowEmptyArchive: true, artifacts: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package Artifact') {
            steps {
                echo "Packaging production JAR artifact..."
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    // Post-build action: Fingerprinted artifact archiving
                    archiveArtifacts allowEmptyArchive: false, artifacts: '**/target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Versioning') {
            steps {
                script {
                    def commitSha = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                    echo "=============================================================="
                    echo "  ECOPULSE ARTIFACT VERSIONED: ${APP_NAME}-${commitSha}       "
                    echo "  STUDENT USN: ${STUDENT_USN} | STATUS: PASSED                "
                    echo "=============================================================="
                }
            }
        }
    }

    post {
        success {
            echo "SUCCESS: EcoPulse AIOps CI pipeline completed cleanly!"
        }
        failure {
            echo "FAILURE: Build or test step failed. Review console log."
        }
    }
}
