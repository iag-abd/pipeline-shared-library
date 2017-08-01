#!/usr/bin/groovy

import groovy.json.JsonOutput

def call(config = [:]) {
  if (!(config.attachments || config.text)) throw new Exception("need text or attacment or both ${config}")

  //config.text = config.text ?: "hello from ${env.BUILD_ID}"
  config.channel = config.channel ?: env.SLACK_CHANNEL ?: "jenkins"
  config.username = config.username ?: env.SLACK_USERNAME ?: "jenkinsbot"
  config.iconEmoji = config.iconEmoji ?: env.SLACK_ICON_EMOJI ?: ":robot_face:"
  //what is the credential called?
  config.slackUriCredentialsId = config.slackUriCredentialsId ?: 'slack_uri'
  // config.proxyHost = config.proxyHost ?: env.SLACK_PROXY ?: null
  // config.proxyPort = config.proxyPort ?: env.SLACK_PROXY_PORT ?: null
  //
  // if (config.proxyHost) {
  //   if (!(config.proxyPort)) throw new Exception("Proxy host but no proxy port")
  // }

  echo "config.channel: ${config.channel}"

  def payloadJson

  message = [
    text : config.text,
    channel : config.channel,
    username : config.username,
    icon_emoji : config.iconEmoji
  ]

  message.attachments = config.attachments ?: []
  message.text = config.text ?: null

  payloadJson = JsonOutput.toJson(message)
  def payload = "payload=${payloadJson}"

  echo payload

  helper = abd.github.pipeline.Helper

  ping

  // withCredentials([string(credentialsId: config.slackUriCredentialsId, variable: 'slackURI')]) {
  // //   //response = helper.postIt(slackURI, payload, config.proxyHost, config.proxyPort.toInteger())
  // //   response = helper.postIt(slackURI, payload)
  // //   echo "slack response::::${response}"
  // echo "here"
  // }
}

@NonCPS
def ping() {
  url = 'http://google.com.au'
  url = new URL(url)
  connection = url.openConnection()
  connection = null
  return "hi"
}
