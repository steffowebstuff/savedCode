using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

//Have now added a repository for contact with the database, it should work.
namespace Individuelltarbeteaspmvc.Models
{
    public class ProjectRepository
    {
        private ss222enProjectEntities1 db = new ss222enProjectEntities1();

        public User GetUser(int id)
        {
            User user = db.Users.Single(u => u.ID == id);
            return user;
        }

        public List<User> GetAllUsers()
        {
            List<User> users = new List<User>();
            users = db.Users.ToList();
            return users;
        }

        public void CreateUser(User user)
        {
                db.Users.AddObject(user);
                db.SaveChanges();
        }

        public void EditUser(User user)
        {
            db.Users.Attach(user);
            db.SaveChanges();
        }

        public void DeleteUser(int id)
        {
            User user = db.Users.Single(u => u.ID == id);
            db.Users.DeleteObject(user);
            db.SaveChanges();
        }
    }
}