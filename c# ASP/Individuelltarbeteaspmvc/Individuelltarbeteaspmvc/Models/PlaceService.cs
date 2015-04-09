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
    public class PlaceService      
    {
        public string GetRegion(string place)
        {
            string requestUristring = String.Format(@"http://api.geonames.org/search?name_startsWith={0}&country=se&maxRows=10&username=elsteffo&style=full", place);
            var request = WebRequest.Create(requestUristring);
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
            }
        }

        public NewWeather GetWeatherinfo(string place, string region)
        {
            string requestUristring = String.Format(@"http://www.yr.no/place/Sweden/{0}/{1}/forecast.xml", region, place);
            var request = WebRequest.Create(requestUristring);
            var coordinates = GetCoordinates(place);
            decimal longitude = decimal.Parse(coordinates[0], System.Globalization.CultureInfo.InvariantCulture);
            decimal latitude = decimal.Parse(coordinates[1], System.Globalization.CultureInfo.InvariantCulture);
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
                            .ToList(); 
                    foreach (XElement element in timelist)
                    {
                        if (element.Attribute("period").Value == "0")
                        {
                            var date = element.Attribute("from").Value;
                            DateTime myDate = DateTime.Parse(date, System.Globalization.CultureInfo.InvariantCulture);
                            informationdate.Add(myDate);
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
									//Kör en fullösning tills vidare
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
                                };
                                return newWeather;
                            }
                        }
                    }
                         
                    return null;
                }
            }
        }

        public List<string> GetCoordinates(string place)
        {
            var placeInfo = place;
            string requestUristring = String.Format(@"http://api.geonames.org/search?name_startsWith={0}&country=se&maxRows=10&username=elsteffo", place); //eventuellt måste du ändra på landskoden också om du vill ha med internationella länder.
            var request = WebRequest.Create(requestUristring);
            using (var response = request.GetResponse())
            {
                using (StreamReader stream = new StreamReader(response.GetResponseStream()))
                {
                    var document = XDocument.Load(stream);
                    List<string> coordinates = new List<string>();
                    var list = document.Descendants("geoname")
                        .Take(1)
                        .ToList();
                    var latitude = list.Descendants("lat").ToList();
                    var longitude = list.Descendants("lng").ToList();
                    var typetest = longitude[0].Value;
                    string cityLongitude = longitude[0].Value;
                    string cityLatitude = latitude[0].Value;

                    coordinates.Add(cityLongitude);
                    coordinates.Add(cityLatitude);
                    return coordinates;
                }
            }
            
        }
    }
}