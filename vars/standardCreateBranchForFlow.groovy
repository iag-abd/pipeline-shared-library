#!/usr/bin/groovy

def call(config) {
  sh "git checkout -b feature/${config.version} develop"
}
