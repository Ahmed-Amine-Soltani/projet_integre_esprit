using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CloudKids.Domain.Entities
{
    public class Cart
    {
        // just for test
        public int Id { get; set; }

       

        public Double TotalCartPrice { get; set; }

        public virtual ICollection<CartLine> CartLines { get; set; }

        //public CommandHistory MyHistory { get; set; }

        public DateTime PurchaseDate { get; set; }

        public bool CartStatus { get; set; }

        //[ForeignKey("MyHistory")]
        //public int CommandHistoryId { get; set; }


        //public Profil_Parent ProfilParent { get; set; }

        //[ForeignKey("ProfilParent")]
        public int ParentId { get; set; }
    }
}
