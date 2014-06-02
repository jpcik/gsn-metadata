package gsn.meta.wiki

import scala.util.Try

object WikiParser {
  type WikiTemplate=(String,Map[String,String])
  def parse(title:String,wiki:String):Try[Array[WikiTemplate]]={
    println(wiki)
    val ss=wiki.trim.split("\\{\\{").filterNot(_.length==0)
    println("substrings"+ss.mkString("\n"))
    Try{
      if (ss.isEmpty) throw new IllegalArgumentException("Invalid wiki content.") 
      val components=ss.map{w=>
        val end=w.indexOf("}}")        
        if (end>0)  
          parseFields(w.substring(0,end))
        else null
      }      
      val res=components.filterNot(_==null)
      if (res.isEmpty)  throw new IllegalArgumentException("Invalid wiki content.") 
      res
    }
  }
  
  private def parseFields(wiki:String):WikiTemplate={
    println("parsing this: "+wiki)
    val values=wiki.split("\\|").map(_.trim)
    val parsed=(values.head,values.tail.map{eq =>
      val split=eq.split("=")
      if (split.length>1) split(0).trim->split(1).trim
      else split(0).trim -> null
    }.toMap)
    println(parsed._2.mkString("::"))
    parsed
  }
}