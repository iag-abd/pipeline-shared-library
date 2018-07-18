#!/usr/bin/groovy

def call(config = [:]) {
  config.branch = config.branch ?: 'master'
  config.version = config.version ?: "jenkins-build-${BUILD_ID}"
  config.commit = config.commit ?: config.branch

  local_date = new Date().format('yyyy-MM-dd hh:mm')

  sshagent (credentials: [config.credentialsId]) {
    sh """
      git tag -a ${config.version} -m \"Build on ${JOB_DISPLAY_URL} at ${local_date}\" ${config.commit}
      git push --tags
    """
  }
}
