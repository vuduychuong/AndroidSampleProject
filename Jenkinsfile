// Every jenkins file should start with either a Declarative or Scripted Pipeline entry point.
node {
    //Utilizing a try block so as to make the code cleaner and send slack notification in case of any error
    try {
        //Call function to send a message to Slack
        //notifyBuild('STARTED')
        // Global variable declaration
        def project = 'sa-android'
        def appName = 'Sample App'

        // Stage, is to tell the Jenkins that this is the new process/step that needs to be executed
        stage('Checkout') {
            // Pull the code from the repo
            checkout scm
        }

        stage('Build Image') {
            // Build our docker Image
            sh("docker build -t ${project} .")
        }

        stage('Run application test') {
            // If you need environmental variables in your image. Why not load it attach it to the image, and delete it afterward
            sh("env >> .env")
            sh("docker run --env-file .env --rm ${project} ./gradlew test")
            sh("rm -rf .env")
        }
``
        stage('Deploy application') {
            // This is the cool part where you deploy. Here, you can specify builds you want to deploy
            switch (env.BRANCH_NAME) {
                case "master":
                    sh("env >> .env")
                    sh("docker run --env-file .env --rm ${project} ./gradlew clean build assembleRelease")
                    sh("rm -rf .env")
                    break
                case "dev":
                    sh("env >> .env")
                    sh("docker run --env-file .env --rm ${project} ./gradlew clean build assembleDebug")
                    sh("rm -rf .env")
                    break
                case "product":
                    sh("env >> .env")
                    sh("docker run --env-file .env --rm ${project} ./gradlew clean build assembleProduct")
                    sh("rm -rf .env")
                    break
                case "master-java":
                    sh("env >> .env")
                    sh("docker run --env-file .env --rm ${project} ./gradlew clean build assembleDebug")
                    sh("rm -rf .env")
                    break
            }
        }
    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
      } finally {
        //notifyBuild(currentBuild.result)
    }
}