namespace StompTest
{
    public class Message
    {
        public string _id { get; set; }
        public string _title { get; set; }
        public string _body { get; set; }
        public string _creator { get; set; }
        public Message[] _replies { get; set; }
    }
}
