#!/usr/bin/groovy

def call(config = [:]) {
  config.version = config.version ?: "jenkins-build-${BUILD_ID}"
  sh "git checkout -b feature/${config.version} develop"
}
