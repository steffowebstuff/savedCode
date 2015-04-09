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
    public class ProjectController : Controller
    {
        private ss222enProjectEntities1 db = new ss222enProjectEntities1();

        //
        // GET: /Project/

        public ViewResult Index()
        {
            return View(db.Users.ToList());
        }

        //
        // GET: /Project/Details/5

        public ViewResult Details(int id)
        {
            User user = db.Users.Single(u => u.ID == id);
            return View(user);
        }

        //
        // GET: /Project/Create

        public ActionResult Create()
        {
            return View();
        } 

        //
        // POST: /Project/Create

        [HttpPost]
        public ActionResult Create(User user)
        {
            if (ModelState.IsValid)
            {
                db.Users.AddObject(user);
                db.SaveChanges();
                return RedirectToAction("Index");  
            }

            return View(user);
        }
        
        //
        // GET: /Project/Edit/5
 
        public ActionResult Edit(int id)
        {
            User user = db.Users.Single(u => u.ID == id);
            return View(user);
        }

        //
        // POST: /Project/Edit/5

        [HttpPost]
        public ActionResult Edit(User user)
        {
            if (ModelState.IsValid)
            {
                db.Users.Attach(user);
                db.ObjectStateManager.ChangeObjectState(user, EntityState.Modified);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(user);
        }

        //
        // GET: /Project/Delete/5
 
        public ActionResult Delete(int id)
        {
            User user = db.Users.Single(u => u.ID == id);
            return View(user);
        }

        //
        // POST: /Project/Delete/5

        [HttpPost, ActionName("Delete")]
        public ActionResult DeleteConfirmed(int id)
        {            
            User user = db.Users.Single(u => u.ID == id);
            db.Users.DeleteObject(user);
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