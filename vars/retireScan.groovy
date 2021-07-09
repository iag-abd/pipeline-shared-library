def call(config = [:]) {
  sh """
    docker run --rm -v ${env.WORKSPACE}/app:/app -v ${env.WORKSPACE}:/retirejs/output macbasket/retirejs-docker:latest --outputformat json || true
  """
}
