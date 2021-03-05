using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CloudKids.Domain.Entities;

namespace CloudKids.Data
{
    public class CloudKidsContext : DbContext
    {


        public DbSet<Product> Products { get; set; }
        public DbSet<Cart> Carts { get; set; }
        public DbSet<CartLine> CartLines { get; set; }
        public DbSet<CommandHistory> CommandHistories { get; set; }
    

        public DbSet<PointsLoyalty> PointsLoyaltys { get; set; }


        //public CloudKidsContext() : base("name=Remote_DB_CloudKidsConnectionString")
        public CloudKidsContext() : base("name=CloudKidsConnectionString")
        {

        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {



        }
    }
}
