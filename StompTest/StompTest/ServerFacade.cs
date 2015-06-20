using System;
using System.Threading;

namespace StompTest
{
    class ServerFacade
    {
        #region Ctors & Fields
        private readonly StompClient _client;
        private readonly AutoResetEvent _waitEvent = new AutoResetEvent(false);
        private string _sessionId;
        private string[] _forums;

        public AdminFacade Admin { get; private set; }
        public ForumFacade Forum { get; private set; }
        public UserFacade User { get; private set; }

        // default
        public ServerFacade() : this ("127.0.0.1", 12345) { }

        public ServerFacade(string serverAddress, int port) {
            _client = new StompClient(serverAddress, port);
            _client.OnReceived += HandleNotification;

            Admin = new AdminFacade(_client, _waitEvent);
            Forum = new ForumFacade(_client, _waitEvent);
            User = new UserFacade(_client, _waitEvent);
        }
        #endregion

        #region Notifications
        private static void HandleNotification(object sender, StompMessage msg)
        {
            if (!msg.Type.Equals(ServerActions.Notification)){
                return;
            }

            // TODO: handle notification here
            var type = msg.Headers["type"];
            // TODO: what types do we have?
        }
        #endregion

        #region Connect / Disconnect
        public void Connect()
        {
            _client.Connect();
        }
        #endregion

        #region Login Guest
        public string LogInGuest(string forum)
        {
            var msg = new StompMessage { Type = ServerActions.LoginGuest };

            msg.Headers.Add("forum", forum);

            _sessionId = string.Empty;
            _client.OnReceived += HandleLoginGuestResponse;
            _client.Send(msg);
            _waitEvent.WaitOne();
            return _sessionId;
        }

        private void HandleLoginGuestResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.LoginGuest) return;

            _sessionId = msg.Content;
            _client.OnReceived -= HandleLoginGuestResponse;
            _waitEvent.Set();
        }
        #endregion

        #region Get Forums
        public string[] GetForums()
        {
            var msg = new StompMessage {Type = ServerActions.GetForums};

            _client.OnReceived += HandleGetForumsResponse;
            _client.Send(msg);
            _waitEvent.WaitOne();

            return _forums;
        }

        private void HandleGetForumsResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.GetForums) return;

            _forums = msg.Content.Split('\n');
            _client.OnReceived -= HandleGetForumsResponse;
            _waitEvent.Set();
        }

        #endregion

        #region Member Login
        public void LogIn(string username, string password)
        {
            var msg = new StompMessage { Type = ServerActions.LoginMember };

            msg.Headers.Add("username", username);
            msg.Headers.Add("password", password);
            _client.OnReceived += HandleLoginResponse;
            _client.Send(msg);
            _waitEvent.WaitOne();
        }

        private void HandleLoginResponse(object server, StompMessage msg)
        {
            if (msg.Type != ServerActions.LoginMember) return;

            _client.OnReceived -= HandleLoginResponse;
            _waitEvent.Set();
        }
        #endregion        

        /*
        
        TODO LIST:
        ***admin service***
        *** forum service ***
        8. get reports ~~

        *** user service ***
        1. login
        2. logout
        7. submit complaint
        
        */
    }
}
