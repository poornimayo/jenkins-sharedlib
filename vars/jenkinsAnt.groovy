def call(String repoUrl) {
  pipeline {
       agent any
       environment{
        PATH="C:/ProgramData/chocolatey/lib/ant/tools/apache-ant-1.10.11/bin:$PATH"
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
                bat 'ant clean compile test package war'
            }
        }
        stage('deploy'){
            steps{
                nexusPublisher nexusInstanceId: 'localnexus3', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'C:\\Windows\\System32\\config\\systemprofile\\AppData\\Local\\Jenkins\\.jenkins\\workspace\\first-project-ant\\target\\roshambo.war']], mavenCoordinate: [artifactId: 'antProject', groupId: 'com.antproject', packaging: 'war', version: '1.5']]]
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
