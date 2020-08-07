import groovy.json.JsonOutput
import groovy.json.JsonSlurper 
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.URL
import java.util.Scanner

def consul_query(String consul_host, String consul_path, String creds) {
  def String query = null
  def String consul_token = creds

  try {
    // Create connection
    def get = new URL(consul_host + "${consul_path}").openConnection();
    get.setRequestProperty("Content-Type", "application/json")
    get.setRequestProperty("X-Consul-Token", consul_token)
    def getRC = get.getResponseCode();
    if (getRC.equals(200)) {
      def resp = get.getInputStream().getText()
      def jsonSlurper = new JsonSlurper()
      def object = jsonSlurper.parseText(resp)
      query = object
    }

  } catch(Exception e) {
    println("ERROR: " + e.toString()); 
  }

  println("query: " + query)
  
  return query
}