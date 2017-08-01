package abd.github.pipeline

class Helper {

  static postIt(url, payload) {
    // def proxy
    // if(proxy_host) {
    //  proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy_host, proxy_port))
    // }

    url = new URL(url)

    // def connection
    // if(proxy_host) {
    //  connection = url.openConnection(proxy)
    // } else {
    //  connection = url.openConnection()
    // }

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


}
