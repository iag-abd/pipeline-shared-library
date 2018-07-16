#!/usr/bin/groovy

def call(config = [:]) {

  script {
    if (!config.versionParam) {error 'need config.versionParam'}
    if (!config.region) {error 'need config.region'}

    versionParam = config.versionParam
    region = config.region

    sh "aws ssm get-parameter --name $versionParam --region ap-southeast-2 || aws ssm put-parameter --name $versionParam --type String --value 0.0.0 --overwrite --region $region"

    version = sh (
        script: "aws ssm get-parameter --name $versionParam --region $region| jq ' .[] | .Value' --raw-output",
        returnStdout: true
    ).trim()
          
    echo version
          
    def matcher = version =~ /(?<major>\d*).(?<minor>\d*).(?<revision>\d*)[.-]*(.*)/
    v2 = matcher[0][1] + "." + matcher[0][2] + "." + (Integer.parseInt(matcher[0][3]) + 1)

    version = v2
  }

  script {
    sh "aws ssm put-parameter --name $config.versionParam --type String --value $version --overwrite --region $config.region"
  }
  return version
}
