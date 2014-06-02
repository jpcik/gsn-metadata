package gsn.meta.wiki

import java.sql.ResultSet
import es.upm.fi.oeg.morph.Morph
import scala.concurrent.ExecutionContext
import scala.concurrent.Await
import scala.concurrent.duration._
import org.apache.jena.riot.RiotWriter
import org.slf4j.LoggerFactory
import org.apache.jena.riot.RDFDataMgr
import org.apache.jena.riot.Lang
import com.typesafe.config.ConfigFactory
import es.upm.fi.oeg.morph.r2rml.R2rmlReader
import es.upm.fi.oeg.morph.execute.RdfGenerator
import es.upm.fi.oeg.morph.db.RelationalModel
import es.upm.fi.oeg.morph.db.dataset.Metadata
import es.upm.fi.oeg.morph.db.dataset.MapRecord
import es.upm.fi.oeg.morph.db.dataset.RecordDataset

object WikiTranslator {
  def translate={
    val conf=ConfigFactory.load().getConfig("morph")
    //val model=new WikiModel("fieldsite",Seq("Deployment","Description","Organization"))
    val model=new WikiModel("recordfield",Seq("Location","Description","Organization"),
        Seq("DBaseParameterName","MeasuredParameter","MeasurementMedia"))
    val reader = R2rmlReader("mappings/wiki.ttl")
    val generator = new RdfGenerator(reader, model, conf.getString("baseUri"))
    generator.generate//("recordfield", model.query(""))
  }
  
  
  def main(args:Array[String]):Unit={
    val ds=WikiTranslator.translate
    RDFDataMgr.write(System.out, ds.asDatasetGraph,Lang.NQUADS)
    System.exit(0)
  }
}

class WikiModel(name:String,fieldNames:Seq[String],nestedFields:Seq[String]=Seq()) extends RelationalModel(null,true){
  override def query(queryString:String)={
    //val metawithPos=(fieldNames++Seq("id")).zipWithIndex.toMap
    val metadata=Metadata.create(name,fieldNames++nestedFields)
    implicit val context = ExecutionContext.Implicits.global
    val qm=new QueryManager("http://www.swiss-experiment.ch/")
    val sites=Await.result(qm.getMeasurementRecords, 10000 seconds)
    val empty:Array[String]=nestedFields.map(f=>null).toArray
    val data:Seq[MapRecord]=sites.filter(_.isSuccess).map{site=>      
        val wik=site.get.head
        val datafields=fieldNames.map{s=>
          wik._2.getOrElse(s, null)}
        if (nestedFields.isEmpty){
          Seq(new MapRecord(metadata.fields,datafields ++ Seq(wik._1)))
        }
        else {
          val vals=nestedFields.map{field=>
            val orig=wik._2.getOrElse(field,null)
            if (orig==null) empty
            else orig.split(",").map(_.trim)                
          }
          val l=vals.head.size
          (0 to l-1).map{i=>
            val nestedValues=vals.map(v=>v(i))
            println(metadata.fields)
            new MapRecord(metadata.fields,datafields++nestedValues++Seq(wik._1))
          }
          
        }
      
    }.flatten
    new RecordDataset(data.toStream,metadata)
  } 
  override def queries(r2rml:R2rmlReader)=
    r2rml.tMaps.map {tMap =>
       tMap.logicalTable.tableName
    }.toSeq
}