using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CloudKids.Domain.Entities
{
     public class PointsLoyalty
    {
        public int Id { get; set; }
        public int ParentId { get; set; }
        public int Points { get; set; }
    }
}
