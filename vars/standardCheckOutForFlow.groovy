#!/usr/bin/groovy

def call(config) {
  deleteDir()
  git branch: 'develop', credentialsId: config.credentialsId, url: config.git_url
  sh """
    cat .git/config
    git config user.email \"${config.email}\"
    git config user.name \"${config.username}\"
    git checkout master
    git checkout develop
  """
}
