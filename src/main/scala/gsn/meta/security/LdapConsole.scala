package gsn.meta.security

import com.typesafe.config.ConfigFactory
import scala.io.Source

object LdapConsole {
   def main(args:Array[String]):Unit={
    val conf = ConfigFactory.load().getConfig("ldap")
    val ldap=LdapClient(conf)
    val initialId=conf.getInt("initialid")
    val dc="ou=people,dc=lsir-swissex,dc=epfl,dc=ch"
    val bs=Source.fromFile("src/main/resources/users")
    val usersIndex=bs.getLines.map(_.split("\\t")).zipWithIndex
    val users=usersIndex.map{case (u,idx)=>
      println(u.mkString("--"))
      new LdapUser(u(0),u(1),initialId+idx,dc)
    }
    
    users.foreach{u=>
      println(u.cn+" "+u.uid+" "+u.password+" "+u.uidNumber)
      ldap.addUser(u.getEntry)
    }
    
    //ldap.addUser
    ldap.disconnect
  }
}