#!/usr/bin/groovy

package com.dell.cpsd.SCM

def getRepoName() {
    return env.JOB_NAME.split('/')[1]
}

def getOrgName() {
    if (env.JOB_NAME ==~ /^dellemc-symphony.*/) {
        return "dellemc-symphony"
    } else if (env.JOB_NAME ==~ /^vce-symphony.*/) {
        return "vce-symphony"
    } else {
        return env.JOB_NAME.split('/')[0]
    }
}

def getPublicRepos() {
    def publicRepos = []
    def response = new URL("https://api.github.com/orgs/dellemc-symphony/repos?per_page=100000").text
    def json = new JsonSlurper().parseText(response)
    json.each {
        publicRepos.add(it."name")   
    }
   
