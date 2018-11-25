pipeline {
    agent any
    tools {
        maven 'maven'
        jdk 'jdk8'
    }
    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Checkout code') {
            steps {
                checkout scm
            }
        }

        stage ('Compile') {
            steps {
                sh 'mvn -B clean package -Dmaven.test.skip=true'
            }
            post {
                always {
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                }
            }
        }

        stage ('Test') {
            steps {
                sh 'mvn -B test -Dmaven.test.failure.ignore=true'
            }
        }

        stage ('Sonar') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }

        stage ('Deploy snapshot') {
            when {
                not {
                    branch('master')
                }
            }
            steps {
                sh "mvn deploy -DaltDeploymentRepository=$SNAPSHOT_REPOSITORY"
            }
        }
        stage ('Deploy master') {
            when {
                branch('master')
            }
            steps {
                sh "mvn deploy -DaltDeploymentRepository=$RELEASE_REPOSITORY"
            }
        }
    }
}