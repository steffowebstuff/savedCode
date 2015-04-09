using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Väderapplikation.Models
{
    public class ExtWeather
    {
        public int UserId { get; set; }
        public string placeName { get; set; }
        public decimal longitude { get; set; }
        public decimal latitude { get; set; }
        public DateTime Day1day { get; set; }
        public DateTime Day2day { get; set; }
        public DateTime Day3day { get; set; }
        public DateTime Day4day { get; set; }
        public DateTime Day5day { get; set; }
        public decimal Day1temp { get; set; }
        public decimal Day2temp { get; set; }
        public decimal Day3temp { get; set; }
        public decimal Day4temp { get; set; }
        public decimal Day5temp { get; set; }
        public string Day1Weather { get; set; }
        public string Day2Weather { get; set; }
        public string Day3Weather { get; set; }
        public string Day4Weather { get; set; }
        public string Day5Weather { get; set; }
    }
}