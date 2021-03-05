using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CloudKids.Domain.Entities
{
    public class CommandHistory
    {
       
        public int Id { get; set; }

        public DateTime DateAdded { get; set; }

        public string Name { get; set; }

        public Double Price { get; set; }

        public int Quantity { get; set; }

        public DateTime DateAddition { get; set; }

        public byte[] Img1 { get; set; }


        public string Description { get; set; }


        public int DirectorId { get; set; }






    }
}
