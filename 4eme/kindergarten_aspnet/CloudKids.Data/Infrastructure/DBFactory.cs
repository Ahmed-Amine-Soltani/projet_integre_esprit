using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CloudKids.Data;

namespace CloudKids.Data.Infrastructure
{
    public class DBFactory : Disposable, IDBFactory
    {
        CloudKidsContext ctxt;

        public DBFactory()
        {
            ctxt = new CloudKidsContext();
        }
        public DBFactory(CloudKidsContext context)
        {
            ctxt = context;
        }

        public CloudKidsContext Context
        {
            get { return ctxt; }
        }

        public override void DisposeContext()
        {
            if (ctxt != null)
                ctxt.Dispose();
        }

    }
}
