#!/usr/bin/groovy

def call(config) {
  version = config.version ?: "jenkins-${BUILD_ID}"
  config.branch = config.branch ?: 'master'
  sh "git checkout -b feature/${version} ${config.branch}"
}
