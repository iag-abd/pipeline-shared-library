package abd.github.pipeline

class Helper {

  static postItToSlack(url, payload) {
    sh "curl -X POST --data-urlencode \'payload=${payload}\' ${url}"
    return "done"
  }

}
