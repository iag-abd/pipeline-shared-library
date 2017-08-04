#!/usr/bin/groovy

def call(config) {
  //KAT = Key, AccessKey, Token :)
  sts_kat = sh(returnStdout: true, script: """
    aws sts assume-role --role-arn ${config.role_arn} \
                              --role-session-name ${config.session_name} \
                              --duration-seconds ${config.duration} \
                              --query '[Credentials.AccessKeyId,Credentials.SecretAccessKey,Credentials.SessionToken]' \
                              --output text
  """).trim()
  return sts_kat
}
