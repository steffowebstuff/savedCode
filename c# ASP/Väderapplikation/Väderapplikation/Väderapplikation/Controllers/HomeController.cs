using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Väderapplikation.Models;

namespace Väderapplikation.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index(string username)
        {
            ViewBag.Message = "Modify this template to jump-start your ASP.NET MVC application.";
            string name = User.Identity.Name;
            ViewBag.Username = name;
            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your app description page.";
            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
    }
}
