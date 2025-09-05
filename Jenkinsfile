pipeline {
    agent any

    // environment section is commented out
    // environment {
    //     DOCKERHUB_CREDENTIALS = credentials('lib_dockerhub')
    //     DOCKERHUB_USERNAME = "lasanthi821"
    //     BOOK_IMAGE_NAME = "library-book-service"
    //     MEMBER_IMAGE_NAME = "library-member-service"
    //     IMAGE_TAG = "latest"
    // }

    stages {
        stage('Checkout Git') {
            steps {
                checkout scm
            }
        }

        stage('Build Book Service') {
            steps {
                dir('book-service') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Member Service') {
            steps {
                dir('member-service') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    docker.build("lasanthi821/library-book-service:latest", "./book-service")
                    docker.build("lasanthi821/library-member-service:latest", "./member-service")
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                script {
                    // Use withCredentials to bind the Docker Hub credentials directly
                    withCredentials([usernamePassword(
                        credentialsId: 'lib_dockerhub', 
                        usernameVariable: 'DOCKER_USER', 
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        // Log in to Docker Hub using the bound credentials
                        bat "docker login -u %DOCKER_USER% -p %DOCKER_PASS%"
                        
                        // Push the Book Service image
                        bat "docker push lasanthi821/library-book-service:latest"
                        
                        // Push the Member Service image
                        bat "docker push lasanthi821/library-member-service:latest"
                        
                        // Log out from Docker Hub
                        bat "docker logout"
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