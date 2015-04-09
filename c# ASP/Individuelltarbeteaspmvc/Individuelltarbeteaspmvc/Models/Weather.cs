using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Individuelltarbeteaspmvc.Models
{
    //contains the weatherobject that is to be used in the database.
    public class WeatherObject
    {
        public int UserId { get; set; }
        public string placeName { get; set; }
        public double longitude { get; set; }
        public double latitude { get; set; }
        public DateTime Day1day { get; set; }
        public DateTime Day2day { get; set; }
        public DateTime Day3day { get; set; }
        public DateTime Day4day { get; set; }
        public DateTime Day5day { get; set; }
        public int Day1Weather { get; set; }
        public int Day2Weather { get; set; }
        public int Day3Weather { get; set; }
        public int Day4Weather { get; set; }
        public int Day5Weather { get; set; }
    }
}