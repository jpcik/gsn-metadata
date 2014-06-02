package gsn.tools

import java.io.File
import scala.io.Source
import java.io.FileWriter
import java.io.BufferedWriter
import java.io.StringWriter
import java.io.PrintWriter

object CodeHeaders {
  def main(args:Array[String]):Unit={
    navigate(new File(base))    
  }
  val base="/home/mehdi/git/openiot/modules/x-gsn/src/main/java/org/openiot/"
  val ddbase="/home/mehdi/git/gsn/src/"
  def navigate(file:File):Unit={
    
    if (file.isDirectory)
      file.listFiles.foreach(f=>navigate(f))
    else if (file.getName.endsWith("java")){
      val ff=new File(file.getPath.replace(base, ddbase))
      if (ff.exists)
        addUsers(file,ff)
    }
    else {}
  }
  
  def addUsers(file:File,fileOrig:File)={
    val orig=Source.fromFile(fileOrig)
    val origIt=orig.getLines
    val authors=origIt.filter(_.startsWith("* @author")).toList
    orig.close          

    val bf=Source.fromFile(file)
    val buf=new StringWriter
    val it=bf.getLines
    it.foreach{line=>
      buf.append(line+"\n")
      if (line.trim.startsWith("*     Contact: OpenIoT")){
        authors.foreach(a=>buf.append(" "+a+"\n"))      
      }
    } 

    bf.close
    val fw= new PrintWriter(file)
    fw.write(buf.toString)
    fw.close
    println("done")
  }
}