using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CloudKids.Domain;

namespace CloudKids.Data.Infrastructure
{
    public class UnitOfWork : IUnitOfWork
    {
        private IDBFactory _dbFactory; 

        public UnitOfWork(IDBFactory dbFactory)
        {
            _dbFactory = dbFactory;
        }

        public IRepository<T> GetRepository<T>() where T : class
        {
            return new Repository<T>(_dbFactory);
        }

        public void Dispose()
        {
            _dbFactory.Dispose();
        }

        // Expose commit method
        public void Commit()
        {
            _dbFactory.Context.SaveChanges();
        }
    }
}
