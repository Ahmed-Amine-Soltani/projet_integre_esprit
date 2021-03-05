using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CloudKids.Data;

namespace CloudKids.Data.Infrastructure
{
    public interface IDBFactory : IDisposable 
    {
        CloudKidsContext Context 
        {
            get;
        }
    }
}
