using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Individuelltarbeteaspmvc.Models;

namespace Individuelltarbeteaspmvc.Controllers
{
    public class HomeController : Controller
    {
        private newss222enProjectEntities db = new newss222enProjectEntities();

        public ActionResult Index()
        {
            ViewBag.Message = "Det här är förstasidan!";

            return View();
        }

        public ActionResult About()
        {
            return View();
        }

        public ActionResult PlaceInput()
        {
            return View();
        }

        [HttpPost]
        public ActionResult PlaceInput([Bind(Include = "Place")]ViewModel model)

        {
            /*
             * Put in a try catch here, and then send 
             * */
            if (ModelState.IsValid)
            {
                var place = model.place;
                var time = DateTime.Now;
                var placeService = new PlaceServiceTest();
                string region = placeService.GetRegion(place);
                NewWeather weatherIno = placeService.GetWeatherinfo(place, region);
                var latitude = 51.508742;
                Weather weather = new Weather
                        {
                            longitude = 12,
                            latitude = 123,
                            place = "stockholm"
                        };
                TempData["weatherObject"] = weatherIno;
                TempData["latitude"] = weatherIno.latitude;
                TempData["longitude"] = weatherIno.longitude;
               return RedirectToAction("GetMap");
            }
            return View();
        }

        public ActionResult TestingAjax(string ajax)
        {
            return View();
        }

        public ActionResult HelloAjax(string greeting)
        {
            if (Request.IsAjaxRequest())
            {
                return Content("din hälsning" + greeting);
            }

            else
            {
                return Content("ajax ej aktiverat");
            }
        }

        [HttpPost]
        public ActionResult FindDistinctScreenNames(string term)
        {
            var service = new PlaceService();
            return View();
        }

        public ActionResult GetMap()
        {
            var weather = TempData["weatherObject"];
            var latitude = TempData["latitude"];
            var longitude = TempData["longitude"];
            ViewBag.Object = weather;
            ViewBag.longitude = longitude;
            ViewBag.latitude = latitude;      
            return View(weather);
        }

        public ActionResult GetAnotherMap()
        {
            var weather = TempData["weatherObject"];
            return View(weather);
        }

        public ActionResult GetNewWeatherMap()
        {
            var weather = TempData["weatherObject"];
            var latitude = TempData["latitude"];
            ViewBag.latitude = latitude;
            return View(weather);
        }
        
        public ActionResult AjaxGetObjectInfo(string placename)
        {
            NewWeather newweather = db.NewWeathers.Single(n => n.place == "placename");
            return Json(newweather);
        }
    }
}
