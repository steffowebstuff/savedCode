using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Väderapplikation.Models
{
    public class ProjectRepository
    {
        private ss222enProjectEntities db = new ss222enProjectEntities();

        public NewUser GetUser(int id)
        {
            NewUser user = db.NewUsers.Single(u => u.ID == id);
            return user;
        }

        //See if this one is working. Not sure about it
        public List<NewUser> GetAllUsers()
        {
            List<NewUser> users = new List<NewUser>();
            users = db.NewUsers.ToList();
            return users;
        }

        public void CreateUser(NewUser user)
        //Something is wheird here
        {
            //db.NewUsers.AddObject(user);
            db.SaveChanges();
            //user = new User();
            //string message = "user created";
            //return message;
            //This one is not right
        }

        public void EditUser(NewUser user)
        {
            db.NewUsers.Attach(user);
            //This one should be fixed´, for some reason this commando does not seem to work, see how it can be replaced or fixed.
            //db.ObjectStateManager.ChangeObjectState(user, EntityState.Modified);
            db.SaveChanges();
        }

        public void DeleteUser(int id)
        {
            NewUser user = db.NewUsers.Single(u => u.ID == id);
           // db.NewUsers.DeleteObject(user);
            db.SaveChanges();
        }
    }
}