#!/usr/bin/groovy

def call(config = [:]) {

  script {
    versionParam = config.versionParam || error("provide versionParam")
        
    sh "aws ssm get-parameter --name $versionParam --region ap-southeast-2 || aws ssm put-parameter --name $versionParam --type String --value 0.0.0 --overwrite --region ap-southeast-2"

    version = sh (
        script: "aws ssm get-parameter --name $versionParam --region ap-southeast-2 | jq ' .[] | .Value' --raw-output",
        returnStdout: true
    ).trim()
          
    echo version
          
    def matcher = version =~ /(?<major>\d*).(?<minor>\d*).(?<revision>\d*)[.-]*(.*)/
    v2 = matcher[0][1] + "." + matcher[0][2] + "." + (Integer.parseInt(matcher[0][3]) + 1)
    echo v2
       
    cmd = "aws ssm put-parameter --name $versionParam --type String --value v2 --overwrite --region ap-southeast-2"
    sh cmd
  }
  return version
}
