using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace StompTest
{
    class StompMessage
    {
        public Dictionary<String, String> Headers { get; private set; }
        public String Content { get; set; }
        public String Type { get; set; }

        public StompMessage ()
        {
            Content = string.Empty;
            Type = string.Empty;
            Headers = new Dictionary<string, string>();
        }

        public override string ToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append(Type);
            str.Append("\n");
            foreach (var header in Headers)
            {
                str.Append(string.Format("{0}:{1}", header.Key, header.Value));
                str.Append("\n");
            }
            str.Append("\n");
            str.Append(Content);
            str.Append("\n");
            return str.ToString();
        }

        public static StompMessage FromString(string frameString)
        {
            int commandLength = frameString.IndexOf('\n');
            String command = frameString.Substring(0, commandLength);

            int headerStart = commandLength + 1;
            
            Dictionary<String, String> headers = new Dictionary<string, string>();
            while (headerStart < frameString.Length && frameString[headerStart] != '\n')
            {
                int headerEnd = frameString.IndexOf(':', headerStart);
                String header = frameString.Substring(headerStart, headerEnd-headerStart);
                int valueEnd = frameString.IndexOf('\n', headerEnd);
                String value = frameString.Substring(headerEnd + 1, valueEnd-headerEnd-1);
                headers.Add(header, value);
                headerStart = valueEnd + 1;
            }

            String content = "";
            int contentStart = headerStart + 1;
            if (contentStart < frameString.Length)
            {
                content = frameString.Substring(contentStart);
            }

            StompMessage msg = new StompMessage() { Type = command, Content = content };
            foreach (var key in headers.Keys)
            {
                msg.Headers.Add(key, headers[key]);
            }
            return msg;
        }
    }
}
