import groovy.json.JsonOutput
import groovy.json.JsonSlurper 
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.URL
import java.util.Scanner


def vault_query(String vault_host, String vault_path, String creds) {
  def String vault_token = null

  def String user = creds.split(":")[0]
  def String user_password = creds.split(":")[1]

  try {
    // Create connection
    def post = new URL(vault_host + vault_path).openConnection();
    
    post.setRequestMethod("POST")
    post.setDoOutput(true)
    post.setRequestProperty("Content-Type", "application/json")

    def message = '{"password":' + '"' + user_password + '"}'
    post.getOutputStream().write(message.getBytes("UTF-8"));
    
    def postRC = post.getResponseCode();
    if (postRC.equals(200)) {
      resp = post.getInputStream().getText()
      def jsonSlurper = new JsonSlurper()
      def object = jsonSlurper.parseText(resp)
      vault_token = object['auth']['client_token']
    }

  } catch(Exception e) {
    println("ERROR: " + e.toString());
  }

  println("vault_host: " + vault_host)
  println("vault_token: " + vault_token)
  return vault_token
}