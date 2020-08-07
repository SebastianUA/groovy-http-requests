import groovy.json.JsonOutput
import groovy.json.JsonSlurper 
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.URL
import java.util.Scanner

def consul_delete(String consul_host, String consul_path, String creds) {
  def String delete_key = null
  def String consul_token = creds

  try {
    // Create connection
    def delete = new URL(consul_host + "${consul_path}").openConnection();
    delete.setDoOutput(true)
    delete.setRequestMethod('DELETE')
    delete.setRequestProperty("Accept", "application/json")
    delete.setRequestProperty("Content-Type", "application/json")
    delete.setRequestProperty("X-Consul-Token", consul_token)

    def getRC = delete.getResponseCode();
    if (getRC.equals(200)) {
      def resp = delete.getInputStream().getText()
      def jsonSlurper = new JsonSlurper()
      def object = jsonSlurper.parseText(resp)
      delete_key = object
    }

  } catch(Exception e) {
    println("ERROR: " + e.toString()); 
  }

  println("delete_key: " + delete_key)
  
  return delete_key
}