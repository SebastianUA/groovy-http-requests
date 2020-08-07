import groovy.json.JsonOutput
import groovy.json.JsonSlurper 
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.URL
import java.util.Scanner

def consul_update(String consul_host, String consul_path, String consul_data, String creds) {
  def String update_key = null
  def String consul_token = creds

  try {
    // Create connection
    def put = new URL(consul_host + "${consul_path}").openConnection();
    put.setDoOutput(true)
    put.setRequestMethod('PUT')
    put.setRequestProperty("Accept", "application/json")
    put.setRequestProperty("Content-Type", "application/json")
    put.setRequestProperty("X-Consul-Token", consul_token)

    def out = new OutputStreamWriter(put.outputStream)
    out.write(consul_data)
    out.close()

    def getRC = put.getResponseCode();
    if (getRC.equals(200)) {
      def resp = put.getInputStream().getText()
      def jsonSlurper = new JsonSlurper()
      def object = jsonSlurper.parseText(resp)
      update_key = object
    }

  } catch(Exception e) {
    println("ERROR: " + e.toString()); 
  }

  println("update_key: " + update_key)
  
  return update_key
}
