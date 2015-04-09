using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Väderapplikation.Models
{
    public class userMessages
    {
        public string GetMessage(int id)
        {
            if (id == 1)
            {
                return "Du har sökt en plats som redan finns";
            }
            else if (id == 2)
            {
                return "Du har valt en plats som inte är giltig, försök igen!";
            }
            else if (id == 3)
            {
                return "Du har valt en plats som inte är giltig, försök igen!";
            }
            return "";
        }

    }
}