def call(String repoUrl) {
  pipeline {
       agent any
       environment{
        PATH="C:/ProgramData/chocolatey/lib/maven/apache-maven-3.6.3/bin:$PATH"
    }
       stages {
           
           stage("Checkout Code") {
               steps {
                   git branch: 'master',
                       url: "${repoUrl}"
               }
           }
           stage('build'){
            steps{
                bat '''mvn clean package
'''
            }
        }
        stage('package publisher'){
            steps{
                nexusPublisher nexusInstanceId: 'localnexus3', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'C:\\Windows\\System32\\config\\systemprofile\\AppData\\Local\\Jenkins\\.jenkins\\workspace\\first-pipeline\\target\\hello-world-war-1.0.0.war']], mavenCoordinate: [artifactId: 'HelloWOrld', groupId: 'com.helloWOrld', packaging: 'war', version: '1.7']]]
            }
        }
       }
    post {
failure {
  script {
    if (currentBuild.currentResult == 'FAILURE') {
      step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "poornimayo98@gmail.com", sendToIndividuals: true])
    }
  }
}
success{
    script{
    if (currentBuild.currentResult == 'SUCCESS') {
      step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "poornimayo98@gmail.com", sendToIndividuals: true])
    }
    }
}
  
}
   }
}
