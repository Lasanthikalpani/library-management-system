pipeline {
    agent any // This tells Jenkins to allocate an executor and workspace for the pipeline

    environment {
        // The ID of the Docker Hub credentials you configured in Jenkins
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-credentials')
        // Your Docker Hub username
        DOCKERHUB_USERNAME = "<<lasanthi821>>" // e.g., "lasan123"
        // Names for your images on Docker Hub
        BOOK_IMAGE_NAME = "library-book-service"
        MEMBER_IMAGE_NAME = "library-member-service"
        // Use 'latest' tag for simplicity in this exam
        IMAGE_TAG = "latest"
    }

    stages {
        // Stage 1: Fetch the latest code from GitHub
        stage('Checkout Git') {
            steps {
                checkout scm // This checks out the code from the repository that triggered the job
            }
        }

        // Stage 2: Build and run tests for the Book Service
        stage('Build Book Service') {
            steps {
                dir('book-service') { // Changes directory to the book-service folder
                    sh 'mvn clean package -DskipTests' // Builds the JAR, skips tests for speed
                }
            }
        }

        // Stage 3: Build and run tests for the Member Service
        stage('Build Member Service') {
            steps {
                dir('member-service') { // Changes directory to the member-service folder
                    sh 'mvn clean package -DskipTests' // Builds the JAR, skips tests for speed
                }
            }
        }

        // Stage 4: Build Docker images for both services
        stage('Build Docker Images') {
            steps {
                script {
                    // Build the Book Service image using the Dockerfile in its directory
                    docker.build("${DOCKERHUB_USERNAME}/${BOOK_IMAGE_NAME}:${IMAGE_TAG}", "./book-service")
                    // Build the Member Service image using the Dockerfile in its directory
                    docker.build("${DOCKERHUB_USERNAME}/${MEMBER_IMAGE_NAME}:${IMAGE_TAG}", "./member-service")
                }
            }
        }

        // Stage 5: Push the images to Docker Hub
        stage('Push Images to Docker Hub') {
            steps {
                script {
                    // Authenticate with Docker Hub using the credentials from Jenkins
                    docker.withRegistry('https://registry.hub.docker.com', DOCKERHUB_CREDENTIALS) {
                        // Push the Book Service image
                        docker.image("${DOCKERHUB_USERNAME}/${BOOK_IMAGE_NAME}:${IMAGE_TAG}").push()
                        // Push the Member Service image
                        docker.image("${DOCKERHUB_USERNAME}/${MEMBER_IMAGE_NAME}:${IMAGE_TAG}").push()
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Library Management System CI/CD Pipeline has completed.'
            cleanWs() // Cleans the Jenkins workspace after the run
        }
        success {
            echo 'Pipeline SUCCESS! New images are available on Docker Hub.'
            // You could add a step here to send a notification (e.g., email, Slack)
        }
        failure {
            echo 'Pipeline FAILED! Please check the console output for errors.'
            // You could add a step here to send an alert
        }
    }
}