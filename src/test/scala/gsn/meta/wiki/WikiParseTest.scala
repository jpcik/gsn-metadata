package gsn.meta.wiki

import org.scalatest.Matchers
import org.scalatest.FunSpec

class WikiParseTest extends FunSpec with Matchers {
    
  describe("Parse wiki text"){
    it("should return key values"){
      val s=WikiParser.parse("WikiTitle",validWiki).get
      s.size shouldBe(1)
      val (field,props)=s.head
      field shouldBe("Fieldsite_Home")
      props("Altitude").shouldBe("3117")
      props("StartDate").shouldBe(null)
      props("Organization").shouldBe("WSL/SLF")
    }
  }

  describe("Parse wiki text with markup"){
    it("should return key values"){
      val s=WikiParser.parse("WikiTitle",validWiki2).get
      s.size shouldBe(1)
      val (field,props)=s.head
      field shouldBe("Fieldsite Home")
      props("Altitude").shouldBe("2341")
      props("StartDate").shouldBe("2006/12/25")
      props("Organization").shouldBe("SLF")
    }
  }

val validWiki="""
ffafsdfsfsfs
  
{{Fieldsite_Home
 | Deployment = Albula
 | Description = Biodiversity, altitudinal gradients, Gipfelflora Project
 | Coordinates = 46.5966096276° N,  9.8294889012° E
 | Altitude = 3117
 | SwissLatitude = 783178
 | SwissLongitude = 163385
 | SwissAltitude = 3117
 | StartDate = 
 | EndDate = 
 | Organization=WSL/SLF
}}
more text here
and even more text
""" 



val validWiki2= """






{{Fieldsite Home
|Deployment=Wannengrat
|Description=Snow, Wind Transport, Avalanche, Avalanche Fracture, boundary layer meteorology, ecosystem genetics, snow hydrology
|Organization=SLF
|StartDate=2006/12/25
|Coordinates=46.8019689531° N, 9.77953255177° E
|Altitude=2341
|Image=Wannengratviewlabelled.jpg
|addinfo=Wannengrat is a high altitude multi-disciplinary fieldsite in complex alpine terrain. The site is centred around a single catchment which ranges in altitude from 1500 - 2700m. At the top of the fieldsite there is a wide plateau with several steep ridges and rockfaces. The site has 7 semi-permanent meteo stations which are augmented with mobile meteo stations on a deployment basis. At the centre of the site, a portacabin with a diesel generator and multiple solar panels provides energy for instruments requiring heating or high data rates. A multi-hop wifi link provides high data rate communications and a GPRS modem provides communications from the semi-permanent station network.

Research on Wannengrat crosses several disciplines: measurements for cryospheric, hydrological and ecological research are all carried out at the site, though shared for mutual benefit.

}}
* [http://www.swiss-experiment.ch/index.php/File:SLF_Safety_concept.pdf Safety concept for working in the field]
__NOTOC__
__NOEDITSECTION__

<!--GOOGLE ANALYTICS......DO NOT REMOVE ! -->
<html>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
var pageTracker = _gat._getTracker("UA-5137010-1");
pageTracker._initData();
pageTracker._trackPageview();
</script>
<link rel="apple-touch-icon" sizes="72x72" href="http://www.swiss-experiment.ch/images/3/38/SwissEx72.jpg" />
<link rel="apple-touch-icon" sizes="114x114" href="http://www.swiss-experiment.ch/images/4/4e/SwissEx144.jpg"/>

<!-- Cached 20140312233038 -->
</html>
"""
}