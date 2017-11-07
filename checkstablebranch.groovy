#!/usr/bin/groovy

import com.dell.cpsd.SCM.Utils

def call() {
    
    def utils = new com.dell.cpsd.SCM.Utils()
    def repoName = utils.getRepoName()
    echo "repoName: ${repoName}"  
    
    if (env.BRANCH_NAME ==~ /stable.*/) {
    
   println("stable branch is present for : " repoName)
   }
   else 
   {
   println("Stable Branch NOT present for": repoName)
   }
  }
