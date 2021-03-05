using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CloudKids.Data.Infrastructure
{
    public interface IUnitOfWork // class abstract is more flexible than an interface (cause interface => must implement all its methods)
    {
        IRepository<T> GetRepository<T>() where T : class;
        void Dispose();
        void Commit();
    }
}
