using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Individuelltarbeteaspmvc.Models;

namespace Individuelltarbeteaspmvc.Controllers
{ 
    public class NewWeatherController : Controller
    {
        private newss222enProjectEntities db = new newss222enProjectEntities();
        

        //
        // GET: /NewWeather/

        public ViewResult Index()
        {
            var newweathers = db.NewWeathers.Include("NewUser");
            return View(newweathers.ToList());
        }

        //
        // GET: /NewWeather/Details/5

        public ViewResult Details(int id)
        {
            NewWeather newweather = db.NewWeathers.Single(n => n.ID == id);
            return View(newweather);
        }

        //
        // GET: /NewWeather/Create

        public ActionResult Create()
        {
            ViewBag.owner = new SelectList(db.NewUsers, "ID", "name");
            return View();
        } 

        //
        // POST: /NewWeather/Create

        [HttpPost]
        public ActionResult Create(NewWeather newweather)
        {
            if (ModelState.IsValid)
            {
                db.NewWeathers.AddObject(newweather);
                db.SaveChanges();
                return RedirectToAction("Index");  
            }

            ViewBag.owner = new SelectList(db.NewUsers, "ID", "name", newweather.owner);

            return View(newweather);
        }
        
        //
        // GET: /NewWeather/Edit/5
 
        public ActionResult Edit(int id)
        {
            NewWeather newweather = db.NewWeathers.Single(n => n.ID == id);
            ViewBag.owner = new SelectList(db.NewUsers, "ID", "name", newweather.owner);
            return View(newweather);
        }

        //
        // POST: /NewWeather/Edit/5

        [HttpPost]
        public ActionResult Edit(NewWeather newweather)
        {
            if (ModelState.IsValid)
            {
                db.NewWeathers.Attach(newweather);
                db.ObjectStateManager.ChangeObjectState(newweather, EntityState.Modified);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.owner = new SelectList(db.NewUsers, "ID", "name", newweather.owner);
            return View(newweather);
        }

        //
        // GET: /NewWeather/Delete/5
 
        public ActionResult Delete(int id)
        {
            NewWeather newweather = db.NewWeathers.Single(n => n.ID == id);
            return View(newweather);
        }

        //
        // POST: /NewWeather/Delete/5

        [HttpPost, ActionName("Delete")]
        public ActionResult DeleteConfirmed(int id)
        {            
            NewWeather newweather = db.NewWeathers.Single(n => n.ID == id);
            db.NewWeathers.DeleteObject(newweather);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }

        public ActionResult FindCity()
        {
            return View();
        }

        [HttpPost]
        public ActionResult FindCity([Bind(Include = "Place")]ViewModel model)
        {
            if (ModelState.IsValid)
            {
                var place = model.place;
                var time = DateTime.Now;
                var placeService = new PlaceServiceTest();
                var placeInfo = placeService.GetCoordinates(place); //the rigt one
                //convert the coordinates to the right types, then adjust the xml-request and create the complete object in the placeService class.
                Weather weather = new Weather
                {
                };
                return RedirectToAction("Index");
            }
            return View();
        }
    }
}