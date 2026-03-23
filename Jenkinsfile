pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './mvnw clean install'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                pkill -f 'java -jar' || true
                nohup java -jar target/*.jar > app.log 2>&1 &
                '''
            }
        }
    }
}