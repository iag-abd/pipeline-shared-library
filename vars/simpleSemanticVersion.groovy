#!/usr/bin/groovy

def call(config = [:]) {
  config.version_file = config.version_file ?: 'VERSION'
  script {
    //note: we will support number.number.number and drop anything after the third number
    version_file = config.version_file
    def matcher = readFile(version_file) =~ /(?<major>\d*).(?<minor>\d*).(?<revision>\d*)[.-]*(.*)/
    matcher[0]
    patch = Integer.parseInt(matcher[0][3]) + 1
    version = matcher[0][1] + "." + matcher[0][2] + "." + patch
    echo "New Version = ${version}"
    //writeFile file: version_file, text: version
  }
  return version
}
