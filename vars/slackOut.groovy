#!/usr/bin/groovy

/**********
  slackOut(["channel":"jenkins", "text":"hello from ${BUILD_ID}" ])
  slackOut(["text":"hello from ${BUILD_ID}" ])
  slackOut([
      "text":"hello from ${BUILD_ID}",
      "channel":"build",
      "username":"builder",
      "iconEmoji":":panda_face":"
  ])
**********/

import groovy.json.JsonOutput

def call(config = [:]) {
  if (!(config.attachments || config.text)) throw new Exception("need text or attacment or both ${config}")

  //TODO: put proxy back in
  //TODO: ignore curl secure

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

  //def utils = new abd.pipeline.Utils()
  proxy = ''
  if (config.proxy) {
    proxy = "-x ${config.proxy}"
  }

  withCredentials([string(credentialsId: config.slackUriCredentialsId, variable: 'slackURI')]) {
    //response = utils.postIt(slackURI, payload)
    //echo "slack response::::${response}"
    sh "curl ${proxy} -X POST --data-urlencode \'${payload}\' ${slackURI}"
  }

}
