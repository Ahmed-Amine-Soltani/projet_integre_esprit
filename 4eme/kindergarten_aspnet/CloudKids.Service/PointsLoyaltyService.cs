using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CloudKids.Data;
using CloudKids.Data.Infrastructure;
using CloudKids.Domain.Entities;

namespace CloudKids.Service
{
    public class PointsLoyaltyService : Service<PointsLoyalty>, IPointsLoyaltyService
    {

        static CloudKidsContext context = new CloudKidsContext();
        static IDBFactory dbf = new DBFactory(context);
        static IUnitOfWork uow = new UnitOfWork(dbf);

        public PointsLoyaltyService() : base(uow)
        {
        }

    }
}
