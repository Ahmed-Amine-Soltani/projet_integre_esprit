using CloudKids.Data;
using CloudKids.Data.Infrastructure;
using CloudKids.Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CloudKids.Service
{
   public class ProductService : Service<Product> , IProductService
    {
        static CloudKidsContext context = new CloudKidsContext();
        static IDBFactory dbf = new DBFactory(context);
        static IUnitOfWork uow = new UnitOfWork(dbf);

        public ProductService() : base(uow)
        {

        }
    }
}
