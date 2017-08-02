#!/usr/bin/groovy

def call(config) {
  config.version_file = config.version_file ?: 'VERSION'
  sh "git checkout -b feature/${config.version} develop"
}
