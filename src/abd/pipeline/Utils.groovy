package abd.github.pipeline

@NonCPS
def postIt(url, payload) {
  url = new URL(url)

  def connection
  connection = url.openConnection()

  connection.setRequestMethod("POST")
  connection.doOutput = true

  def writer = new OutputStreamWriter(connection.outputStream)
  writer.write(payload)
  writer.flush()
  writer.close()

  connection.connect()
  return "${connection.getResponseCode()}::::${connection.content.text}"
}
