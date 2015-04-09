using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Väderapplikation.Models
{
    public class WeatherRepository
    {
        private ss222enProjectEntities1 db = new ss222enProjectEntities1();

        public MyWeather GetWeather(int id)
        {
            MyWeather myweather = db.MyWeathers.Find(id);
            return myweather;
        }

        public List<MyWeather> GetAllWeather(string username)
        {
            List<MyWeather> weatherlist = new List<MyWeather>();
            weatherlist = (from w in db.MyWeathers where w.projectuser == username select w).ToList();
            return weatherlist;
        }

        public void CreateWeather(MyWeather weather)
        //Something is wheird here
        {
            db.MyWeathers.Add(weather);
            db.SaveChanges();
            //user = new User();
            //string message = "user created";
            //return message;
            //This one is not right
        }

        //Se uppgift 2 gällande hur du ska göra här. 

        public void DeleteWeather(int id)
        {
            MyWeather myweather = db.MyWeathers.Find(id);
            db.MyWeathers.Remove(myweather);
            db.SaveChanges();
        }
    }
}