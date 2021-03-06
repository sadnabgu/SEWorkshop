﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using StompTest.Forms;

namespace StompTest
{
    public class AdminFacade : AbstractFacade
    {
        private string _sid;

        public AdminFacade(StompClient client, AutoResetEvent waitEvent) : base(client, waitEvent) {}

        #region Init System
        public void InitSystem(string admin, string adminpass)
        {
            var msg = new StompMessage { Type = ServerActions.InitSystem };

            msg.Headers.Add("username", admin);
            msg.Headers.Add("password", adminpass);
            _client.OnReceived += HandleInitSystemResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleInitSystemResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.InitSystem) return;
            FillError(msg);
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
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleCreateForumResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.CreateForum) return;
            FillError(msg);
            _client.OnReceived -= HandleCreateForumResponse;
            _waitEvent.Set();
        }
        #endregion

        #region Login Admin
        public string LogInAdmin(string username, string password)
        {
            var msg = new StompMessage { Type = ServerActions.LoginAdmin };

            msg.Headers.Add("username", username);
            msg.Headers.Add("password", password);
            _client.OnReceived += HandleLoginAdminResponse;
            _sid = string.Empty;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
            return _sid;
        }

        private void HandleLoginAdminResponse(object sender, StompMessage msg)
        {
           
            if (msg.Type != ServerActions.LoginAdmin) return;
            ValidateError();

            try
            {
                _sid = msg.Headers["sid"];
            }
            catch (Exception ex)
            {

            }
            finally
            {
                _client.OnReceived -= HandleLoginAdminResponse;
                _waitEvent.Set();
            }
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
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleRemoveForumResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.RemoveForum) return;
            FillError(msg);
            _client.OnReceived -= HandleCreateForumResponse;
            _waitEvent.Set();
        }
        #endregion
    }
}
