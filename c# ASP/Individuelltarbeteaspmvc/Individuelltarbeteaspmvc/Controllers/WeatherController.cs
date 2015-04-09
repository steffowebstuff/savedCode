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
    public class WeatherController : Controller
    {
        private ss222enProjectEntities1 db = new ss222enProjectEntities1();

        //
        // GET: /Weather/

        public ViewResult Index()
        {
            var weathers = db.Weathers.Include("User");
            return View(weathers.ToList());
        }

        //
        // GET: /Weather/Details/5

        public ViewResult Details(int id)
        {
            Weather weather = db.Weathers.Single(w => w.ID == id);
            return View(weather);
        }

        //
        // GET: /Weather/Create

        public ActionResult Create()
        {
            ViewBag.owner = new SelectList(db.Users, "ID", "name");
            return View();
        } 

        //
        // POST: /Weather/Create

        [HttpPost]
        public ActionResult Create(Weather weather)
        {
            if (ModelState.IsValid)
            {
                db.Weathers.AddObject(weather);
                db.SaveChanges();
                return RedirectToAction("Index");  
            }

            ViewBag.owner = new SelectList(db.Users, "ID", "name", weather.owner);
            return View(weather);
        }
        
        //
        // GET: /Weather/Edit/5
 
        public ActionResult Edit(int id)
        {
            Weather weather = db.Weathers.Single(w => w.ID == id);
            ViewBag.owner = new SelectList(db.Users, "ID", "name", weather.owner);
            return View(weather);
        }

        //
        // POST: /Weather/Edit/5

        [HttpPost]
        public ActionResult Edit(Weather weather)
        {
            if (ModelState.IsValid)
            {
                db.Weathers.Attach(weather);
                db.ObjectStateManager.ChangeObjectState(weather, EntityState.Modified);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.owner = new SelectList(db.Users, "ID", "name", weather.owner);
            return View(weather);
        }

        //
        // GET: /Weather/Delete/5
 
        public ActionResult Delete(int id)
        {
            Weather weather = db.Weathers.Single(w => w.ID == id);
            return View(weather);
        }

        //
        // POST: /Weather/Delete/5

        [HttpPost, ActionName("Delete")]
        public ActionResult DeleteConfirmed(int id)
        {            
            Weather weather = db.Weathers.Single(w => w.ID == id);
            db.Weathers.DeleteObject(weather);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}