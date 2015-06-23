using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace StompTest
{
    class StompClient
    {
        public string Address { get; private set; }
        public int Port { get; private set; }
        private readonly TcpClient _client;
        private readonly Thread _listenThread;

        public StompClient(string address, int port)
        {
            Address = address;
            Port = port;
            _client = new TcpClient();
            _listenThread = new Thread(ListenToRequests);
        }

        public void Connect()
        {
            if (_client.Connected) return;
            _client.Connect(Address, Port);
            _listenThread.Start();
        }

        public event EventHandler<StompMessage> OnReceived;

        public void Send(StompMessage message)
        {
            var buffer = Encoding.UTF8.GetBytes(message.ToString());

            _client.Client.Send(buffer);
            var zero = new byte[] { 0 };
            _client.Client.Send(zero);
        }

        private void ListenToRequests()
        {
            var buffer = new byte[1];
            var stream = new List<byte>();
            while (_client.Connected)
            {
                var count = _client.Client.Receive(buffer);
                if (count <= 0) continue;
                if (buffer[0] == 0)
                {
                    var total = stream.ToArray();
                    var messageString = Encoding.UTF8.GetString(total);
                    var msg = StompMessage.FromString(messageString);
                    stream.Clear();
                    if (OnReceived != null) { OnReceived(this, msg); }
                }
                else
                {
                    stream.Add(buffer[0]);
                }
            }
        }
    }
}
