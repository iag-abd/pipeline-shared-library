#!/usr/bin/groovy

def call(config = [:]) {
  config.version = config.version ?: "jenkins-build-${BUILD_ID}"
  config.branch = config.branch ?: 'master'
  sh "git checkout -b feature/${config.version} ${config.branch}"
}
