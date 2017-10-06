import json
import requests
from requests.auth import HTTPDigestAuth
import sys
import os
import getenv
import xml.etree.ElementTree as ET

user, token, orgs_url, repos_url = getenv.readyml()

""" Data information """
def getpom():
   data = {}
   data['pom'] = []
   outputpath = 'target/poms'
   pompath = 'target'
   with open('data.json') as data_file:
       repos = json.load(data_file)
   for reponame in repos['repoinfo']:
       repo_url = repos_url + "/" + reponame['name'] + "/contents/pom.xml"
       myResponse = requests.get(repo_url, auth=(user ,token))
       if(myResponse.ok):
           data['pom'].append({
              "name" : reponame['name'],
              "pom" : myResponse.json()['name']
              })
           try:
              os.makedirs(outputpath)
           except Exception:
              pass
       else:
	       data['pom'].append({
		      "name" : reponame['name'],
			  "pom" : "Missing"
			  })
       with open(os.path.join(outputpath, reponame['name'] + ".json"), 'w') as pomfile:
           json.dump(myResponse.json(), pomfile, indent = 2)
               
   with open(os.path.join(pompath, 'poms.json'), 'w') as outfile:
      json.dump(data, outfile, indent = 2)
    


   
        
if __name__ == '__main__':
   getpom()
