pipeline {
    agent {
        node{
            label 'maven-builder'
            customWorkspace "workspace/${env.JOB_NAME}"
            }
    }
   environment {
    GITHUB_TOKEN = credentials('github-02')
   }
    options {
        //Keep the 5 most recent builds
       buildDiscarder(logRotator(numToKeepStr:'5'))
        timestamps()
        disableConcurrentBuilds()    
    }
    tools {
        maven 'linux-maven-3.3.9'
        jdk 'linux-jdk1.8.0_102'
    }
     stages {
        stage("CopyArtifacts") {
           steps {
             script {
                      def externalCopyArtifacts = load("copyArtifacts.groovy")    
                      externalCopyArtifacts()
                }
             }
       }
    
       stage("License") {
            steps {
                     sh 'license=($(grep -oP '(?<=license>)[^<]+' "**/pom.xml"))'

             }  
      
    }
     post {
         success {
            LicenseSuccessEmail()
            cleanWorkspace() 
        }
         failure {
            failureEmail()
            cleanWorkspace() 
         }
                 
        
    }
}
