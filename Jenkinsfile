pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('library-management-dockerhub')
        DOCKERHUB_USERNAME = "lasanthi821"
        BOOK_IMAGE_NAME = "library-book-service"
        MEMBER_IMAGE_NAME = "library-member-service"
        IMAGE_TAG = "latest"
    }

    stages {
        stage('Checkout Git') {
            steps {
                checkout scm
            }
        }

        stage('Build Book Service') {
            steps {
                dir('book-service') {
                    // Use 'bat' for Windows instead of 'sh'
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Member Service') {
            steps {
                dir('member-service') {
                    // Use 'bat' for Windows instead of 'sh'
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    docker.build("${DOCKERHUB_USERNAME}/${BOOK_IMAGE_NAME}:${IMAGE_TAG}", "./book-service")
                    docker.build("${DOCKERHUB_USERNAME}/${MEMBER_IMAGE_NAME}:${IMAGE_TAG}", "./member-service")
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKERHUB_CREDENTIALS) {
                        docker.image("${DOCKERHUB_USERNAME}/${BOOK_IMAGE_NAME}:${IMAGE_TAG}").push()
                        docker.image("${DOCKERHUB_USERNAME}/${MEMBER_IMAGE_NAME}:${IMAGE_TAG}").push()
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Library Management System CI/CD Pipeline has completed.'
            cleanWs()
        }
        success {
            echo 'Pipeline SUCCESS! New images are available on Docker Hub.'
        }
        failure {
            echo 'Pipeline FAILED! Please check the console output for errors.'
        }
    }
}