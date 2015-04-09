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
    public class NewUserController : Controller
    {
        private newss222enProjectEntities db = new newss222enProjectEntities();

        //
        // GET: /NewUser/

        public ViewResult Index()
        {
            //Här kommer användarnamnet att flyga in. Skapa ett repository, som ersätter de nuvarande actioncontrollena.
            //Skicka användaren till getUser
            //Om resultatet blir null, skicka den till insertuser
            //Annars hämtar du id't från den användaren
            //Sedan skickar du vidare id't till att använda genom applikationen.
            return View(db.NewUsers.ToList());
        }

        //
        // GET: /NewUser/Details/5

        public ViewResult Details(int id)
        {
            NewUser newuser = db.NewUsers.Single(n => n.ID == id);
            return View(newuser);
        }

        //
        // GET: /NewUser/Create

        public ActionResult Create()
        {
            return View();
        } 

        //
        // POST: /NewUser/Create

        [HttpPost]
        public ActionResult Create(NewUser newuser)
        {
            if (ModelState.IsValid)
            {
                db.NewUsers.AddObject(newuser);
                db.SaveChanges();
                return RedirectToAction("Index");  
            }

            return View(newuser);
        }
        
        //
        // GET: /NewUser/Edit/5
 
        public ActionResult Edit(int id)
        {
            NewUser newuser = db.NewUsers.Single(n => n.ID == id);
            return View(newuser);
        }

        //
        // POST: /NewUser/Edit/5

        [HttpPost]
        public ActionResult Edit(NewUser newuser)
        {
            if (ModelState.IsValid)
            {
                db.NewUsers.Attach(newuser);
                db.ObjectStateManager.ChangeObjectState(newuser, EntityState.Modified);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(newuser);
        }

        //
        // GET: /NewUser/Delete/5
 
        public ActionResult Delete(int id)
        {
            NewUser newuser = db.NewUsers.Single(n => n.ID == id);
            return View(newuser);
        }

        //
        // POST: /NewUser/Delete/5

        [HttpPost, ActionName("Delete")]
        public ActionResult DeleteConfirmed(int id)
        {            
            NewUser newuser = db.NewUsers.Single(n => n.ID == id);
            db.NewUsers.DeleteObject(newuser);
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