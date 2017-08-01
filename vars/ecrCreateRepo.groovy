#!/usr/bin/groovy

call(String repo, String region) {
  echo 'create repo if it does not exist'
  sh "aws --region ${region} ecr create-repository --repository-name ${repo} || echo 'repo exists already'"
}
