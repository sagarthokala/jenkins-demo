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
                pkill -f 'OrderService' || true
                nohup java -jar target/OrderService-0.0.1-SNAPSHOT.jar --server.port=8081 > app.log 2>&1 &
                sleep 5
                '''
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                docker stop orderservice || true
                docker rm orderservice || true
                docker run -d -p 8082:8081 --name orderservice orderservice
                '''
            }
        }
    }
}