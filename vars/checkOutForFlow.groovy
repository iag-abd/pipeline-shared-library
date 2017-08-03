#!/usr/bin/groovy

def call(config) {
  deleteDir()
  git branch: 'develop', credentialsId: config.credentialsId, url: config.git_url
  sh """
    git config user.email \"${config.email}\"
    git config user.name \"${config.name}\"
    git checkout master
    git checkout develop
  """
}
