#!/usr/bin/groovy

// call(String region){
//   // echo 'login to docker'
//   // sh "eval `aws ecr get-login --region ${region}`"
//   // echo 'test that login'
//   // sh "aws --region ${region} ecr describe-repositories"
// }

def call(config) {
  // evaluate the body block, and collect configuration into the object
  config = config ?: [:]
  echo 'login to docker in ' + config.region
  sh "eval `aws ecr get-login --region ${config.region}`"
  echo "done ecr login"
}
