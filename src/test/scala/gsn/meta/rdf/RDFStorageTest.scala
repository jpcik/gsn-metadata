package gsn.meta.rdf

import org.scalatest.FunSpec
import org.scalatest.Matchers

class RDFStorageTest  extends FunSpec with Matchers {
    
  val url="jdbc:virtuoso://localhost:1111/charset=UTF-8/log_enable=2"
 
  describe("Insert triples"){
    it("should insert the data"){
      val rdf=new RDFRepo(url)
      rdf.update
    }
  }
}