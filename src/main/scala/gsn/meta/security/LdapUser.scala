package gsn.meta.security

import org.apache.commons.lang.math.RandomUtils
import org.apache.commons.lang.RandomStringUtils
import org.apache.directory.api.ldap.model.entry.DefaultEntry

case class LdapUser(fullName:String,email:String,uidNumber:Int,dc:String) {
  val objectClasses=Seq("inetOrgPerson","top","posixAccount")
  val uid=email.split("@").head
  val cn=fullName.replace(' ', '.')
  val sn=cn.split("\\.").head
  val password=getRandomPassword
  
  def getEntry=new DefaultEntry(
            "cn="+cn+","+dc,   
            "objectClass: top",
            "objectClass: inetOrgPerson",
            "objectClass: posixAccount",
            "uidNumber",uidNumber.toString,
            "uid",uid,
            "homeDirectory: /home/users/"+uid,
            "cn",cn,
            "gidNumber: 1000",
            "mail: "+email,
            "sn: "+sn,
            "userPassword: "+password
            //"sn", sn
            ) 
  
  private def getRandomPassword={
    var password = new StringBuffer(20)
    val next = RandomUtils.nextInt(13) + 8
    password.append(RandomStringUtils.randomAlphanumeric(next))
    password.toString
  }
  
}