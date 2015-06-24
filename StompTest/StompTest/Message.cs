using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace StompTest
{
    class Message
    {
        public string Id { get; set; }
        public string Title { get; set; }
        public string Body { get; set; }
        public ICollection<Message> Replies { get; set; }

        public Message()
        {
            Replies = new List<Message>();
            Id = "";
            Title = "";
            Body = "";
        }
    }
}
