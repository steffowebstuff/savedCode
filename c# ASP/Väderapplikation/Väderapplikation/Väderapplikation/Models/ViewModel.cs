using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Väderapplikation.Models;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace Väderapplikation.Models
{
    public class ViewModel
    {
        [DisplayName("Name")] //Bör ändras till Place
        [Required]
        [StringLength(100)]
        public string place { get; set; }

        [DisplayName("Region")] //Se om du kan ändra den här utan att det påverkar något annat
        [Required]
        [StringLength(100)]
        public string region { get; set; }
    }
}