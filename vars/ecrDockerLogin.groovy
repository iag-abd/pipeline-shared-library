#!/usr/bin/groovy

def call(config) {
  // evaluate the body block, and collect configuration into the object
  config = config ?: [:]
  echo 'login to docker in ' + config.region
  sh "eval `aws ecr get-login --region ${config.region}`"
  echo 'test ecr login'
  sh "aws --region ${config.region} ecr describe-repositories"
}
