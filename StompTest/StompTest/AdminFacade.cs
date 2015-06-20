﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace StompTest
{
    class AdminFacade : AbstractFacade
    {
        private bool _initSuccess;

        internal AdminFacade(StompClient client, AutoResetEvent waitEvent) : base(client, waitEvent) {}

        #region Init System
        public bool InitSystem(string admin, string adminpass)
        {
            var msg = new StompMessage { Type = ServerActions.InitSystem };

            msg.Headers.Add("username", admin);
            msg.Headers.Add("password", adminpass);
            _client.OnReceived += HandleInitSystemResponse;
            _client.Send(msg);
            _waitEvent.WaitOne();

            return _initSuccess;
        }

        private void HandleInitSystemResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.InitSystem) return;

            _initSuccess = bool.Parse(msg.Content);
            _client.OnReceived -= HandleInitSystemResponse;
            _waitEvent.Set();
        }

        #endregion

        #region Create Forum
        public void CreateForum(string adminSession, string forumName, string manager, string password)
        {
            var msg = new StompMessage { Type = ServerActions.CreateForum };

            msg.Headers.Add("forum", forumName);
            msg.Headers.Add("adminSessionId", adminSession);
            msg.Headers.Add("manager", manager);
            msg.Headers.Add("password", password);
            _client.OnReceived += HandleCreateForumResponse;

            _client.Send(msg);
            _waitEvent.WaitOne();
        }

        private void HandleCreateForumResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.CreateForum) return;

            _client.OnReceived -= HandleCreateForumResponse;
            _waitEvent.Set();
        }
        #endregion

        #region Login Admin
        public void LogInAdmin(string username, string password)
        {
            var msg = new StompMessage { Type = ServerActions.LoginAdmin };

            msg.Headers.Add("username", username);
            msg.Headers.Add("password", password);
            _client.OnReceived += HandleLoginAdminResponse;
            _client.Send(msg);
            _waitEvent.WaitOne();
        }

        private void HandleLoginAdminResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.LoginAdmin) return;

            _client.OnReceived -= HandleLoginAdminResponse;
            _waitEvent.Set();
        }

        #endregion

        #region Remove Forum
        public void RemoveForum(string adminSession, string forumName)
        {
            var msg = new StompMessage { Type = ServerActions.RemoveForum };

            msg.Headers.Add("forum", forumName);
            msg.Headers.Add("adminSessionId", adminSession);

            _client.OnReceived += HandleRemoveForumResponse;

            _client.Send(msg);
            _waitEvent.WaitOne();
        }

        private void HandleRemoveForumResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.RemoveForum) return;

            _client.OnReceived -= HandleCreateForumResponse;
            _waitEvent.Set();
        }
        #endregion
    }
}
