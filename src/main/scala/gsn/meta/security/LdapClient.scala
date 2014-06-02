package gsn.meta.security

import org.apache.directory.ldap.client.api.LdapConnection
import org.apache.directory.ldap.client.api.LdapNetworkConnection
import org.apache.directory.api.ldap.model.entry.DefaultEntry
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

class LdapClient(url:String,user:String,passwd:String) {
  val connection:LdapConnection = new LdapNetworkConnection( url, 389 )
  
  //cn=admin,dc=lsir-swissex,dc=epfl,dc=ch
  val bindResponse = connection.bind( user, passwd )
   
  if (!connection.isAuthenticated) 
    throw new IllegalArgumentException("User not authenticated.")
  
  def addUser(entry:DefaultEntry)={
    val cn="Test.Person"
    val response = connection.add(entry)
    response
  }
  
  def disconnect={
    connection.unBind
    connection.close
  }
  
  
  
}

object LdapClient{
  def apply(conf:Config)=new LdapClient(conf.getString("url"),
      conf.getString("username"),conf.getString("password"))
  
 
}