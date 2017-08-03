#!/usr/bin/groovy

def call(config) {
  config.branch = config.branch ?: 'master'
  sh "git checkout -b feature/${config.version} ${config.branch}"
}
