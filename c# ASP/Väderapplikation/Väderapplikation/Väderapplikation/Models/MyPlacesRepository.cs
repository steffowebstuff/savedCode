using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Väderapplikation.Models
{
    public class MyPlacesRepository
    {
        private ss222enProjectEntities6 db = new ss222enProjectEntities6();

        public MyPlace GetWeather(int id)
        {
            MyPlace myweather = db.MyPlaces.Find(id);
            return myweather;
        }

        public List<MyPlace> GetAllWeather(string username)
        {
            List<MyPlace> weatherlist = new List<MyPlace>();
            weatherlist = (from w in db.MyPlaces where w.projectuser == username select w).ToList();
            return weatherlist;
        }

        public void CreateWeather(MyPlace weather)
        {
            db.MyPlaces.Add(weather);
            db.SaveChanges();
        }


        public void DeleteWeather(int id)
        {
            MyPlace myweather = db.MyPlaces.Find(id);
            db.MyPlaces.Remove(myweather);
            db.SaveChanges();
        }

        public string GetNewPlaceOwner(int id)
        {
            List<string> newplaceOwner = (from w in db.MyPlaces where w.ID == id select w.projectuser).ToList();
            string owner = newplaceOwner[0];
            return owner;
        }

        public List<MyPlace> GetWeatherlist(string username, string place, string region) 
        {
            
           List<MyPlace> weatherlist = (from w in db.MyPlaces where w.projectuser == username && w.place == place && w.region == region select w).ToList();
           return weatherlist;
        }

        
    }
}