#!/usr/bin/groovy

def call(config) {
  config.version_file = config.version_file ?: 'VERSION'
  sshagent (credentials: [config.credentialsId]) {
    sh """
      git commit -a -m \"jenkins build\"
      git checkout develop
      git merge feature/${config.version}
      git push origin develop
      git checkout master
      git merge feature/${config.version}
      git push origin master
      git tag -a ${config.version} -m \"Build on ${JOB_DISPLAY_URL} at `date -d today +'%Y%m%d%H%M'`\" master
      git push --tags
    """
  }
}
