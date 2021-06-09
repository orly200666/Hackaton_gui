#!groovy

// Declarative Pipeline fundamentals
pipeline {
    options {buildDiscarder(logRotator(numToKeepStr: '5', daysToKeepStr: '10'))}
    stages {
        stage('Build') {
            steps {
                sh '''
                    echo "Building 1... 2... 3..." > build.log
                '''
            }
        }
        stage('Test') {
            steps {
                sh '''
                    echo "Testing 1... 2... 3..." > test.log
                '''
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    echo "Deploying 1... 2... 3..." > deploy.log
                '''
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
