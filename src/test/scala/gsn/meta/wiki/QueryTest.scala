package gsn.meta.wiki

import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.concurrent.ExecutionContext
import scala.concurrent.Await
import concurrent.duration._
import es.upm.fi.oeg.morph.relational.RelationalModel
import java.sql.ResultSet

class QueryTest extends FunSpec with Matchers {
    
  describe("Query all fieldsites on wiki"){
    implicit val context = ExecutionContext.Implicits.global
    val qm=new QueryManager("http://www.swiss-experiment.ch/")
    ignore ("should parse sites"){
      val fsites=qm.getSites
      
      fsites.onComplete {a=>
        println("finished")
      }
      println("now wait")
      while (!fsites.isCompleted){
      }
    }
    it("should get titles"){
      val titlesFut=qm.getMemberTitles("Category:Measurement Record")
      val titles=Await.result(titlesFut,10 seconds)
      println("the number of titles: " +titles.size)
   }

  }
  

}

