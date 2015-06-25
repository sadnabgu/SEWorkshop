using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace StompTest
{
    public abstract class BaseFacade
    {
        public string Error { get; set; }
    
        protected void ValidateError()
        {
            if (Error == null) return;

            try
            {
                throw new Exception(Error);
            }
            finally
            {
                Error = null;
            }
        }

        protected void FillError(StompMessage msg)
        {
            if (msg.Headers.ContainsKey("error"))
            {
                Error = msg.Headers["error"];
            }
        }
    }
}
