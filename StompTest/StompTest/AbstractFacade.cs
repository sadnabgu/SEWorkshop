using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace StompTest
{
    public abstract class AbstractFacade: BaseFacade
    {
        public static readonly int TIMEOUT = 3000;
        protected readonly AutoResetEvent _waitEvent;
        protected readonly StompClient _client;

        protected AbstractFacade(StompClient client, AutoResetEvent waitEvent)
        {
            _client = client;
            _waitEvent = waitEvent;
        }
    }
}
