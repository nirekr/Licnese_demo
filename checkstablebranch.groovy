#!/usr/bin/groovy

import com.dell.cpsd.SCM.Utils

def stable_branch(item) {
  orgName = item.getFullName().split('/')[0]
  repoName = item.getFullName().split('/')[1]
  branchName = item.getFullName().split('/')[2]
	print(repoName)
	print(branchName)
  if (branchName ==~ /master|stable.*/) {
    println("Stable Branch is present for " +repoName)
  }
	else 
	{ println("NO STABLE BRANCHES FOR" +repoName)}
 
}
