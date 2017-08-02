#!/usr/bin/groovy

def call(config = [:]) {
  config.channel = config.failChannel ?: env.SLACK_FAIL_CHANNEL ?: config.channel ?: 'jenkins_fail'

  config.attachments = config.attachments ?: []

  config.attachments << [
      //"fallback" : " #{status}",
      //"pretext" : "Jenkins Failure *#${config.text}#*",
      "title" : "Failed Jenkins Build",
      "text" : ":boom: Failure - *#${config.text}#*",
      "color" : "#FF0000"
  ]

  slackOut(config)
}
