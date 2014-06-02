package gsn.meta.wiki

import play.api.libs.ws._
import play.api.libs.json.Json._
import play.api.libs.json.Json
import play.api.libs.json.JsArray
import collection.JavaConversions._
import concurrent.ExecutionContext
import concurrent._
import concurrent.duration._
import scala.xml.XML
import org.slf4j.LoggerFactory

class QueryManager(wikiUrl:String) {

  val logger=LoggerFactory.getLogger(classOf[QueryManager])
  val api=wikiUrl+"api.php"
  val raw=wikiUrl+"index.php"
  implicit val context = ExecutionContext.Implicits.global
  
  private def request(cmtitle:String,continue:String):Future[Seq[String]]={
    val ss=WS.url(api)
      .withQueryString("action"->"query","list"->"categorymembers",
          "cmtitle"->cmtitle,"cmlimit"->"500","cmcontinue"->continue,
          "format"->"xml")
      .get().map{res =>res.body}  
     ss.map{xmlStr=>
      logger.debug(xmlStr)
      val xml=XML.loadString(xmlStr.trim)
      val cont=(xml \\ "query-continue")
      val titles=  (xml \\ "cm").map{cm=>(cm \ "@title").text    }
      if (cont.isEmpty) {
        titles      
      }            
      else {
        val cmm=(cont.head \ "categorymembers").head
        val conti=(cmm \ "@cmcontinue").text
        titles++Await.result(request(cmtitle,conti),10 seconds)
      }
     }
  }
  
  def getMemberTitles(cmtitle:String)={    
    request(cmtitle,"")
  }
  
  def getSites()={    
    val ss=WS.url(api)
      .withQueryString("action"->"query","list"->"categorymembers",
          "cmtitle"->"Category:Fieldsites","cmlimit"->"500",
          "format"->"xml")
      .get().map{res =>
        println("tretet")
        res.body     
      }   
     ss.map{s=>parse(s)}
  }
  
  def getMeasurementRecords()={
    getMemberTitles("Category:Measurement Record").map(ts=>ts.take(10).map{title=>
      val parsed=parseSite(title)
      Await.result(parsed, 10000 seconds)
    }) 
  }
  /*
  def parse(wikiStr:String)={
    println(wikiStr)
    val xml=XML.loadString(wikiStr.trim)
    val sites=(xml \\ "cm").map{cm=>     
      val parsed=parseSite((cm \ "@title").text)
      Await.result(parsed, 10000 seconds)
    }
    sites
   }*/
  
  def parseSite(title:String)={
    WS.url(raw).withQueryString("action"->"raw",
        "title"->title).get.map{wiki=>         
      println(title)
      val parsed=WikiParser.parse(title,wiki.body)
      if (parsed.isFailure){
        parsed.failed.get.printStackTrace
        logger.error("Failed for :"+title+ " "+parsed.failed)
      }
      else {
        if (parsed.get.isEmpty) logger.error("Empty: "+title)
      }
      parsed
    }
  }   
}