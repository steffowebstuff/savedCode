using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Väderapplikation.Models;

namespace Väderapplikation.Controllers
{
    public class MyPlacesController : Controller
    {
        MyPlacesRepository mpr = new MyPlacesRepository();

        public ActionResult Index(string message = null)
        {
            if (Request.IsAuthenticated)
            {
                var username = User.Identity.Name;
                var userlist = mpr.GetAllWeather(username);
                
                ViewBag.UserMessage = message;
                return View(userlist);
            }           
            return RedirectToAction("LogIn", "Account");   
        }

        public ActionResult NewDetails(int id = 0, string message = null)
        {
            if (Request.IsAuthenticated)
            {
                try
                {              
                    var placeService = new PlaceService2();

                    MyPlace myweather = mpr.GetWeather(id); 
                    string place = myweather.place; 
                    string region = myweather.region; 

                    ExtWeather extWeather = placeService.GetWeatherInfo(place, region); 
                    extWeather.placeName = myweather.place;
                  
                    ViewBag.longitude = myweather.longitude;
                    ViewBag.latitude = myweather.latitude;              
                
                    if (myweather == null)
                    {
                        return RedirectToAction("Error", "NotFound");   
                    }
                    ViewBag.UserMessage = "";
                    return View(extWeather);
                }
                catch
                {
                    string userMessage = "Sidan kunde inte hämtas pga problem hos extern service";
                    return RedirectToAction("Index", "MyPlaces", new { message = userMessage }); 
                }
            }
            return RedirectToAction("LogIn", "Account");
        }

        [ValidateAntiForgeryToken]
        [HttpPost]
        public ActionResult NewCreateConfirm([Bind(Include = "Place")]ViewModel model)
        {

            if (Request.IsAuthenticated)
            {
                var place = model.place;
                var placeService = new PlaceService2();
                var regions = placeService.GetRegions(place);
                if (regions.Count == 0)
                {
                    string userMessage = "Vi kunde inte hitta någon region där din sökta plats finns";
                    return RedirectToAction("NewCreate", "MyPlaces", new { message = userMessage });
                }
                
                ViewData["regions"] = regions;
                return View();
            }
            return RedirectToAction("LogIn", "Account");

        }

        public ActionResult NewCreate(string message = null)
        {
            if (Request.IsAuthenticated)
            {
                ViewBag.UserMessage = message;
                return View();
            }
            return RedirectToAction("LogIn", "Account");
        }


        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult NewCreate(ViewModel model)
        {
            if (Request.IsAuthenticated)
            {
                if (ModelState.IsValid)
                { 
                MyPlace myweather = new MyPlace();
                myweather.place = model.place;
                var placeService2 = new PlaceService2();

                var username = User.Identity.Name;
                var place = myweather.place;
                var region = model.region;
                //Prova att istället här kalla på GetWeatherlist
                
                List<MyPlace> weatherlist = new List<MyPlace>();
                //Weatherlist kollar om användaren redan har platsen.
                weatherlist = mpr.GetWeatherlist(username, place, region); 
                int number = weatherlist.Count;
                if (number > 0)
                {
                    string userMessage = "Din post har inte sparats eftersom platsen redan finns";
                    return RedirectToAction("Index", "MyPlaces", new { message = userMessage }); 
                }

                bool exists = placeService2.CheckExistanceYr(place, region);
                if (exists == false)
                {
                    //Platsen finns inte
                    string userMessage = "Din post har inte sparats eftersom platsen inte finns på vädersidan";
                    return RedirectToAction("NewCreate", "MyPlaces", new { message = userMessage });
                }

                var newCoordinates = placeService2.GetNewCoordinates(place, region);

                if (newCoordinates == null) // || newCoordinates.Count == 0
                {
                    string userMessage = "Din post har inte sparats eftersom platsen inte finns på geonames";
                    return RedirectToAction("NewCreate", "MyPlaces", new { message = userMessage });
                }
                
                //Ska flyttas in i ovanstående if-sats.
                if (newCoordinates.Count == 0)
                {
                    return RedirectToAction("NewCreate", "MyPlaces");
                }

                myweather.longitude = decimal.Parse(newCoordinates[0], System.Globalization.CultureInfo.InvariantCulture);
                myweather.latitude = decimal.Parse(newCoordinates[1], System.Globalization.CultureInfo.InvariantCulture);
                myweather.region = region;
                myweather.projectuser = username;

                      
                mpr.CreateWeather(myweather);
                return RedirectToAction("Index");
                }

                return View(model);
            }
            return RedirectToAction("LogIn", "Account");
        }

        public ActionResult Delete(int id = 0)
        {
            if (Request.IsAuthenticated)
            {
                string username = User.Identity.Name;
                string placeOwner = mpr.GetNewPlaceOwner(id);               
                if (placeOwner == username)
                {
                    MyPlace myweather = mpr.GetWeather(id);

                    if (myweather == null)
                    {
                        //Skapa eventuellt en errorsida här.
                        return HttpNotFound();
                    }
                    return View(myweather);
                }
                else
                {
                    return RedirectToAction("Index", "MyPlaces");
                }
            }

            return RedirectToAction("LogIn", "Account");
        }


        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            if (Request.IsAuthenticated)
            {                
                mpr.DeleteWeather(id);
                return RedirectToAction("Index");
            }
            return RedirectToAction("LogIn", "Account");
        }

        //Flytta/ta bort
        /*
        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
         * */
    }
}