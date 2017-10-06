#!/usr/bin/groovy

import com.dell.cpsd.SCM.Utils

def call() {
    def utils = new com.dell.cpsd.SCM.Utils()
    def publicRepos = utils.getPublicRepos()
    println(publicRepos)
    
    for (repo in publicRepos) {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${repo}"]], gitTool: 'linux-git', submoduleCfg: [], userRemoteConfigs: [[url: "https://github.com/dellemc-symphony/${repo}"]]])
        dir("$repo"){
            sh '''
                 if [[ ! -f pom.xml ]]; then
                    echo "pom file does not exist"
                  elif grep 'licenses' pom.xml; then
                    echo "License  exist"
                else 
                     echo "License does not exist"
                     exit 1
                fi
              '''
    }        
}
}
