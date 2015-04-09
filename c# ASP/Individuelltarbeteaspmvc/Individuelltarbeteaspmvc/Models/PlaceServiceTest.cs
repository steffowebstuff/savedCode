using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.IO;
using System.Web;
using System.Net;
using System.Xml.Linq;


namespace Individuelltarbeteaspmvc.Models
{
    public class PlaceServiceTest
     {

        public string GetRegion(string place)
        {
            string requestUristring = String.Format(@"http://api.geonames.org/search?name_startsWith={0}&country=se&maxRows=10&username=elsteffo&style=full", place);
            var request = WebRequest.Create(requestUristring);
            //string region = "somewhere";
            using (var response = request.GetResponse())
            {
                using (StreamReader stream = new StreamReader(response.GetResponseStream()))
                {
                    var document = XDocument.Load(stream);
                    var list = document.Descendants("adminName1")
                        .Take(1)
                        .ToList();
                    string region = list[0].Value;
                    return region;
                }

                
                //return null;
            }
        }

       // public List<double> GetWeatherinfo(string place)
        //public NewWeather GetWeatherinfo(string place)
        public NewWeather GetWeatherinfo(string place, string region)
        {
            //Change this one to a dynamic string
          //  string requestUristring = String.Format(@"http://www.yr.no/place/Sweden/stockholm/norrtelje/forecast.xml");
            //string requestUristring = String.Format(@"http://www.yr.no/place/Sweden/stockholm/{0}/forecast.xml", place); //right one
            string requestUristring = String.Format(@"http://www.yr.no/place/Sweden/{0}/{1}/forecast.xml", region, place);
           // string requestUristring = String.Format(@"http://api.geonames.org/search?name_startsWith={0}&country=GB&maxRows=10&username=elsteffo", place);
            var request = WebRequest.Create(requestUristring);
            var coordinates = GetCoordinates(place);
            decimal longitude = decimal.Parse(coordinates[0], System.Globalization.CultureInfo.InvariantCulture);
            decimal latitude = decimal.Parse(coordinates[1], System.Globalization.CultureInfo.InvariantCulture);
           
            //List<string> information = new List<string>();
            List<double> informationdouble = new List<double>();
            List<DateTime> informationdate = new List<DateTime>();

            using (var response = request.GetResponse())
            {
                using (StreamReader stream = new StreamReader(response.GetResponseStream()))
                {
                    var document = XDocument.Load(stream);
                    var list = document.Descendants("forecast")
                        .Take(1)
                        .ToList();
                    
                    var timelist = list.Descendants("time")
                            //.Take(5) //where last attribute(period) = 0
                            .ToList();
                    string tmpDate = "0000-00-00";
                    foreach (XElement element in timelist)
                    {
                        if (element.Attribute("period").Value == "2" || element.Attribute("period").Value == "3")//ska ersättas med 2
                        {
                            if (element.Attribute("from").Value.Substring(0, 10) != tmpDate) {
                                var date = element.Attribute("from").Value;
                                DateTime myDate = DateTime.Parse(date, System.Globalization.CultureInfo.InvariantCulture);
                                informationdate.Add(myDate);
                                //använd substring för date istället för datetime.

                                var templist = element.Descendants("temperature").ToList();

                                var value = templist[0]
                                    .LastAttribute
                                    .Value
                                    .ToString();
                                if (informationdouble.Count < 5)
                                {
                                    informationdouble.Add(double.Parse(value, System.Globalization.CultureInfo.InvariantCulture));
                                }

                                else
                                {
                                    NewWeather newWeather = new NewWeather
                                    {
                                        // Day1day = ...

                                        day1day = informationdate[0],
                                        day2day = informationdate[1],
                                        day3day = informationdate[2],
                                        day4day = informationdate[3],
                                        day5day = informationdate[4],
                                        day1temp = (decimal)informationdouble[0],
                                        day2temp = (decimal)informationdouble[1],
                                        day3temp = (decimal)informationdouble[2],
                                        day4temp = (decimal)informationdouble[3],
                                        day5temp = (decimal)informationdouble[4],
                                        latitude = latitude,
                                        longitude = longitude
                                        //owner = get from session or something
                                        //place = place //check that it exists and is the right one first
                                        //latitude = call that method from here
                                        //longitude = call that method from here
                                    };
                                    //var coordinates = GetCoordinates(place);
                                    //return informationdouble;
                                    return newWeather;
                                }
                            }
                            tmpDate = element.Attribute("from").Value.Substring(0, 10);
                            
                        } //--

                       //DateTime myDate = DateTime.ParseExact("2009-05-08 14:40:52,531", "yyyy-MM-dd HH:mm:ss,fff",
                                      // System.Globalization.CultureInfo.InvariantCulture)

                        // konvertera därefter om dessa element till rätt typ
                        //Returnera även datum från listan
                        //returnera sedan en array av dessa element ut till action-controllern.

                        
                        //Create the newWeatherObject here
                        /* Weather weather = new Weather
                        {
                           // Day1day = ...
                        };*/
                    }
                       
                  //  }

                   

                    
                    
                    return null;
                   /* NewWeather newWeather2 = new NewWeather
                    {
                    };
                    return newWeather2;
                    * */
                }
            }
        }

        public List<string> GetCoordinates(string place)
        {
            var placeInfo = place;
            //string city = "Lon";
            //string requestUristring = String.Format(@"http://api.geonames.org/search?name_startsWith={0}&country=GB&maxRows=10&username=elsteffo", place);
            string requestUristring = String.Format(@"http://api.geonames.org/search?name_startsWith={0}&country=se&maxRows=10&username=elsteffo", place); //eventuellt måste du ändra på landskoden också om du vill ha med internationella länder.
            var request = WebRequest.Create(requestUristring);
            using (var response = request.GetResponse())
            {
                using (StreamReader stream = new StreamReader(response.GetResponseStream()))
                {
                    var document = XDocument.Load(stream);

                    /*
                    var list = document.Descendants("geoname")
                        .Take(2)
                        .ToList();
                     * * */
                    List<string> coordinates = new List<string>();

                    var list = document.Descendants("geoname")
                        .Take(1)
                        .ToList();
                    /*
                    foreach(XElement xel in list){
                        
                        testarray.Add(xel.ToString());
                        var newlist = xel.Descendants("lat").ToList();
                    }
                    */
                    var latitude = list.Descendants("lat").ToList();
                    var longitude = list.Descendants("lng").ToList();
                    var typetest = longitude[0].Value;
                    string cityLongitude = longitude[0].Value;
                    string cityLatitude = latitude[0].Value;

                    //float test1 = float.Parse(cityLatitude);
                  //  float test2 = float.Parse(cityLongitude);

                    coordinates.Add(cityLongitude);
                    coordinates.Add(cityLatitude);
                    return coordinates;
                    //working now

                    //var testElement = list[0].Value;
                    
                    //This should get the xmlDocument. Next step is to break this out to a separate funktion and to change the string so the variable is by city Name
                }
            }
            
        }
    }
}