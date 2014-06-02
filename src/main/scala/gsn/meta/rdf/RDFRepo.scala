package gsn.meta.rdf

import virtuoso.jena.driver.VirtGraph
import virtuoso.jena.driver.VirtuosoUpdateFactory

class RDFRepo(endpoint:String) {
  
  def update={
  	val set = new VirtGraph(endpoint,"dba","dbapass")
    val query="INSERT INTO GRAPH <http://gsn.epfl.ch/metadata> { <aa> <bb> 'cc' . <aa1> <bb1> 123. }"
    val vur=VirtuosoUpdateFactory.create(query,set)
    vur.exec            
  }
}