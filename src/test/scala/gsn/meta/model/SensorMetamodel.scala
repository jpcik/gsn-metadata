package gsn.meta.model

case class Sensor() {

val tt="""  
{Fieldsite_Home
 | Deployment = Casanna
 | Description = Biodiversity, altitudinal gradients, Gipfelflora Project
 | Coordinates = 46.8593828186° N,  9.828190761° E
 | Altitude = 2557
 | SwissLatitude = 782189
 | SwissLongitude = 192580
 | SwissAltitude = 2557
 | StartDate = 
 | EndDate = 
 | Organization=WSL/SLF"""
}

case class Location(lat:Double,lon:Double,alt:Double)
case class Organization(name:String)

case class Deployment(name:String,
    description:String,location:Location,
    organization:Organization)

