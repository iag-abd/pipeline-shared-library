#!/usr/bin/groovy

def call(config) {
  echo 'list remote ecr repo'
  sh "aws --region ${config.region} ecr list-images --repository-name \"${config.repo}\""
}
