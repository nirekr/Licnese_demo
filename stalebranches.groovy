import groovy.time.TimeCategory

use ( TimeCategory ) {
  sometimeago = (new Date() - environ.get('MONTHS').toInteger().months)
}

def check_last_build(item) {
  orgName = item.getFullName().split('/')[0]
  repoName = item.getFullName().split('/')[1]
  branchName = item.getFullName().split('/')[2]
  if (branchName ==~ /master|stable.*/) {
    return false
  }
  if (item.getLastBuild()) {
    //println "last build: " + item.absoluteUrl + " " + item.getClass() + " " + item.getLastBuild().timestamp.format('YYYY-MMM-dd HH:MM:SS')
    if (item.getLastBuild().timestamp.getTime() < sometimeago) {
      //println "last build: " + item.absoluteUrl + " " + item.getClass() + " " + item.getLastBuild().timestamp.format('YYYY-MMM-dd HH:MM:SS')
      //println item.absoluteUrl
      if (true) {
        if (orgName ==~ /dellemc-symphony.*/) {
          println 'https://github.com/dellemc-symphony/' + repoName + '/' + 'tree/' + branchName
        } else if (orgName == 'vce-symphony') {
          println 'https://eos2git.cec.lab.emc.com/vce-symphony/' + repoName + '/' + 'tree/' + branchName
        }
      }
      return true
    }
  } else {
    println "no build: " + item.absoluteUrl + " " + item.getClass()
    return true
  }
  return false
}

environ = build.getEnvironment()

view = jenkins.model.Jenkins.instance.getView(environ.get('VIEW'))

for (item in view.getItems()) {
//  println item.absoluteUrl
  view.remove(item)
}

counter = 0
for (item in jenkins.model.Jenkins.instance.items) {
    if (item.getClass() == jenkins.branch.OrganizationFolder) {
      if (item.name ==~ /dellemc-symphony\d+|vce-symphony/) {
        repos = item.getItems()
        for (repo in repos) {
          //println "repo: " + item.name + "/" + repo.name + " " + repo.getClass()
          branches = repo.getItems()
          for (branch in branches) {
            //println "branch: " + branch.absoluteUrl
            if (check_last_build(branch)) {
              counter++
              view.add(branch)
            }
          }
        }
      }
    //} else if (item.getClass() != org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject && item.getClass() != com.cloudbees.hudson.plugins.folder.Folder) {
      //println item.getFullName() + " " + item.getClass()
    //  check_last_build(item)
    }
}

println "Total items: " + counter.toString()
