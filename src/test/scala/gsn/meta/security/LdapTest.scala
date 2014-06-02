package gsn.meta.security

import org.scalatest.FunSpec
import org.scalatest.Matchers

class LdapTest extends FunSpec with Matchers {
    
  describe("Connect to Ldap"){
    it("should not authenticate"){
      val ldap= new LdapClient("128.178.156.248","trala","allala")
    }
  }
}