#!/usr/bin/groovy

def call(config = [:]) {
  config.branch = config.branch ?: 'master'
  config.version = config.version ?: "jenkins-build-${BUILD_ID}"
  config.commit = config.commit ?: config.branch

  sshagent (credentials: [config.credentialsId]) {
    sh """
      git tag -a ${config.version} -m \"Build on ${JOB_DISPLAY_URL} at `date -d today +'%Y%m%d%H%M'`\" ${config.commit}
      git push --tags
    """
  }
}
