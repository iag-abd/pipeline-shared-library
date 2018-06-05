#!/usr/bin/groovy

def call(config) {
  echo 'login to docker in ' + config.region
  sh "eval `aws ecr get-login --no-include-email --region ${config.region}`"
  //echo 'test ecr login'
  //sh "aws --region ${config.region} ecr describe-repositories"
}
