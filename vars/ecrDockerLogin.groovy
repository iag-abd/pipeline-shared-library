#!/usr/bin/groovy

call(String region){
  echo 'login to docker'
  sh "eval `aws ecr get-login --region ${region}`"
  echo 'test that login'
  sh "aws --region ${region} ecr describe-repositories"
}
