﻿using System;
using System.Threading;

namespace StompTest
{
    public class ServerFacade : BaseFacade
    {
        private static int TIMEOUT = 3000;
        #region Ctors & Fields
        private readonly StompClient _client;
        private readonly AutoResetEvent _waitEvent = new AutoResetEvent(false);
        private string _sessionId;
        private string[] _forums;
        private bool _initialized;
        private string[] _subforums;

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

        public void Disconnect()
        {
            _client.Disconnect();
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
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
            return _sessionId;
        }

        private void HandleLoginGuestResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.LoginGuest) return;

            FillError(msg);
            _sessionId = msg.Headers["sid"];
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
            _waitEvent.WaitOne(TIMEOUT);
            ValidateError();
            return _forums;
        }

        private void HandleGetForumsResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.GetForums) return;
            FillError(msg);
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
            _waitEvent.WaitOne(TIMEOUT);
        }

        private void HandleLoginResponse(object server, StompMessage msg)
        {
            if (msg.Type != ServerActions.LoginMember) return;

            _client.OnReceived -= HandleLoginResponse;
            _waitEvent.Set();
        }
        #endregion        

        #region Is Initialized
        public bool IsSystemInitialized()
        {

            var msg = new StompMessage { Type = ServerActions.IsSystemInitialized };

            _client.OnReceived += HandleIsSystemInitializedResponse;
            _initialized = false;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);
            try
            {
                ValidateError();
                return _initialized;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        private void HandleIsSystemInitializedResponse(object server, StompMessage msg)
        {
            if (msg.Type != ServerActions.IsSystemInitialized) return;
            FillError(msg);
            _client.OnReceived -= HandleIsSystemInitializedResponse;
            _initialized = bool.Parse(msg.Content);
            _waitEvent.Set();
        }
        #endregion        

        /*
        TODO LIST:
        ***admin service***
        *** forum service ***
        8. get reports ~~
        7. submit complaint
        */
    }
}
