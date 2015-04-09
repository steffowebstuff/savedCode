using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Väderapplikation.Models
{
    //public class PlacesRepository 
    public class PlacesRepository // : IRepository
    {
        private ss222enProjectEntities4 db = new ss222enProjectEntities4();

       // private bool _disposed = false;

        public PlacesExt GetWeather(int id)
        {
            PlacesExt myweather = db.PlacesExts.Find(id);
            return myweather;
        }

        public List<PlacesExt> GetAllWeather(string username)
        {
            List<PlacesExt> weatherlist = new List<PlacesExt>();
            weatherlist = (from w in db.PlacesExts where w.projectuser == username select w).ToList();
            return weatherlist;
        }

        public void CreateWeather(PlacesExt weather)
        {
            db.PlacesExts.Add(weather);
            db.SaveChanges();
        }


        public void DeleteWeather(int id)
        {
            PlacesExt myweather = db.PlacesExts.Find(id);
            db.PlacesExts.Remove(myweather);
            db.SaveChanges();
        }

        /*
        protected override void Dispose(bool disposing)
        {
            if (!_disposed)
            {
                if (disposing)
                {
                    db.Dispose();
                }
            }
            _disposed = true;

        }


        public void Dispose()
        {
            Dispose(true / disposing /);
            GC.SuppressFinalize(this);
        }
         * */
    }
}