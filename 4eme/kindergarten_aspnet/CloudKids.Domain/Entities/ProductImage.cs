using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations.Schema;

namespace CloudKids.Domain.Entities
{
    public class ProductImage
    {
        public int Id { get; set; }
        public byte[] Img { get; set; }

        public virtual Product MyProduct { get; set; }

        [ForeignKey("MyProduct")]
        public int ProductId { get; set; }

    }
}
