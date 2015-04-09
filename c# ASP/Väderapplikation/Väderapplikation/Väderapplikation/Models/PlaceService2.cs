using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using System.Web;
using System.Net;
using System.Xml.Linq;


namespace Väderapplikation.Models
{
    public class PlaceService2
    {       
        public bool CheckExistanceYr(string place, string region)
        {
            bool exists = false;
            string requestUristring = String.Format(@"http://www.yr.no/place/Sweden/{0}/{1}/forecast.xml", region, place);
            try
            {
                var request = WebRequest.Create(requestUristring);
                using (var response = request.GetResponse())
                {
                    using (StreamReader stream = new StreamReader(response.GetResponseStream()))
                    {
                        var document = XDocument.Load(stream);
                        var list = document.Descendants("forecast")                           
                            .ToList();
                        if (list.Count > 0)
                        {
                            exists = true;
                            return exists;
                        }

                        else
                        {
                            exists = false;
                            return exists;
                        }
                    }
                }               
            }
            catch
            {
                return exists;
            }
        }
    
        public List<string> GetRegions(string place)
        {          
            var regions = new List<string>();
            string requestUristring = String.Format(@"http://api.geonames.org/search?name_equals={0}&country=se&maxRows=10&username=elsteffo&style=full", place);
            var request = WebRequest.Create(requestUristring);
            using (var response = request.GetResponse())
            {
                using (StreamReader stream = new StreamReader(response.GetResponseStream()))
                {
                    var document = XDocument.Load(stream);
                    var placelist = document.Descendants("geoname")
                        .Distinct()
                        .ToList();
                    foreach (XElement element in placelist)
                    {
                        var regionlist = element.Descendants("adminName1").ToList();
                        var regionvalue = regionlist[0].Value;
                        if (!regions.Contains(regionvalue))
                            regions.Add(regionvalue);                        
                    }                   
                    return regions;
                }
            }
        }


        public ExtWeather GetWeatherInfo(string place, string region)
        {
            string requestUristring = String.Format(@"http://www.yr.no/place/Sweden/{0}/{1}/forecast.xml", region, place);
            //string requestUristring = String.Format(@"http://www.yr.no/place/Sweden/{0}/{1}/forecast.xml", "fakePlace", "fakeRegion");
            var request = WebRequest.Create(requestUristring);
            List<double> informationdouble = new List<double>();
            List<DateTime> informationdate = new List<DateTime>();
            List<string> informationweather = new List<string>();

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
                    string tmpDate = "0000-00-00";
                    foreach (XElement element in timelist)
                    {
                        if (element.Attribute("period").Value == "2" || element.Attribute("period").Value == "3")
                        {
                            if (element.Attribute("from").Value.Substring(0, 10) != tmpDate)
                            {
                                var date = element.Attribute("from").Value;
                                DateTime myDate = DateTime.Parse(date, System.Globalization.CultureInfo.InvariantCulture);
                              
                                informationdate.Add(myDate);
                                var templist = element.Descendants("temperature").ToList();
                                var weatherlist = element.Descendants("symbol").ToList();
                                var weathervalue = weatherlist[0].Attribute("name").Value;
                                informationweather.Add(weathervalue);

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
                                    ExtWeather newWeather = new ExtWeather
                                    {                                  
                                        Day1day = informationdate[0],
                                        Day2day = informationdate[1],
                                        Day3day = informationdate[2],
                                        Day4day = informationdate[3],
                                        Day5day = informationdate[4],
                                        Day1Weather = informationweather[0],
                                        Day2Weather = informationweather[1],
                                        Day3Weather = informationweather[2],
                                        Day4Weather = informationweather[3],
                                        Day5Weather = informationweather[4],
                                        Day1temp = (decimal)informationdouble[0],
                                        Day2temp = (decimal)informationdouble[1],
                                        Day3temp = (decimal)informationdouble[2],
                                        Day4temp = (decimal)informationdouble[3],
                                        Day5temp = (decimal)informationdouble[4],                                        
                                    };
                                    return newWeather;
                                }
                            }
                            tmpDate = element.Attribute("from").Value.Substring(0, 10);
                        } 
                    }
                    return null;                 
                }
            }
        }

       

        public List<string> GetNewCoordinates(string place, string region)
        {
            var placeInfo = place;
            string requestUristring = String.Format(@"http://api.geonames.org/search?name_equals={0}&country=se&maxRows=10&username=elsteffo&style=full", place); 
            var request = WebRequest.Create(requestUristring);
            using (var response = request.GetResponse())
            {
                using (StreamReader stream = new StreamReader(response.GetResponseStream()))
                {
                    var document = XDocument.Load(stream);

                    List<string> coordinates = new List<string>();

                    var placelist = document.Descendants("geoname")
                        .ToList();
                    int count = 0;
                    foreach(XElement xel in placelist){                       
                        var adminNames = xel.Descendants("adminName1")
                        .ToList();
                        var testPlace = adminNames[0].Value.ToLower();
                        if (testPlace == region.ToLower()) 
                        {
                            var latitudelist = placelist.Descendants("lat").ToList();
                            var newLatitude = latitudelist[count];
                            var longitudelist = placelist.Descendants("lng").ToList();
                            var newLongitude = longitudelist[count];
                            coordinates.Add(newLongitude.Value);
                            coordinates.Add(newLatitude.Value);
                            return coordinates;
                        }
                        count += 1;
                    }
                    return null;                    
                }
            }
        }
    }
}