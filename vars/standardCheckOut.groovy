#!/usr/bin/groovy

def call(config) {
  deleteDir()
  config.branch = config.branch ?: 'master'
  git branch: config.branch, credentialsId: config.credentialsId, url: config.git_url
  sh """
    git config user.email \"${config.email}\"
    git config user.name \"${config.name}\"
  """
}
