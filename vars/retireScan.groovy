def call(config = [:]) {
  config.projectName = config.projectName ?:  env.PROJECT_NAME
  config.version = config.version ?: env.VERSION
  if (config.projectName == null || config.fullImageName == null || config.version == null) throw new Exception("whoops, part of config in retireScan is missing.")
  sh """
    docker rm retire-${config.projectName} || true
    docker create -v /app --name retire-${config.projectName} ${config.fullImageName}:${config.version}
    docker run --rm --volumes-from retire-${config.projectName} -v ${env.WORKSPACE}:/retirejs/output macbasket/retirejs-docker:latest --outputformat json || true
    docker rm retire-${config.projectName} || true
  """
}
