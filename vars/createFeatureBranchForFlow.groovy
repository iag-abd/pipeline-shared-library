#!/usr/bin/groovy

def call(config = [:]) {
  version = config.version ?: "jenkins-${BUILD_ID}"
  sh "git checkout -b feature/${version} develop"
}
