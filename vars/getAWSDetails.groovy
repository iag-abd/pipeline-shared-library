#!/usr/bin/groovy

def call() {
  az = sh(returnStdout: true, script: 'curl -s http://169.254.169.254/latest/meta-data/placement/availability-zone').trim()
  echo az
  region = az.replaceFirst(/[a-z]$/, "")
  echo "region: ${REGION}"
  //account = sh(returnStdout: true, script: 'curl -s http://169.254.169.254/latest/dynamic/instance-identity/document | grep -oP \'(?<="accountId" : ")[^"]*(?=")\'').trim()
  //ecr = "${account}.dkr.ecr.${region}.amazonaws.com"

  //#return = ["region": region, "account": account, "ecr": ecr]
}
