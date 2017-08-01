#!/usr/bin/groovy

def call(config) {
  // evaluate the body block, and collect configuration into the object
  echo 'create repo if it does not exist'
  sh "aws --region ${config.region} ecr create-repository --repository-name ${config.repo} || echo 'repo exists already'"
}
