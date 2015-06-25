using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Json;
using System.Runtime.Serialization.Json;
using System.Threading;
using System.Threading.Tasks;
using Newtonsoft.Json.Utilities;
using Newtonsoft.Json;

namespace StompTest
{
    public class ForumFacade : AbstractFacade
    {
        private string _sid;
        private string[] _subforums;
        private IEnumerable<Message> _messages;

        internal ForumFacade(StompClient client, AutoResetEvent waitEvent) : base(client, waitEvent) { }

        #region Sub Forums

        public string[] GetSubForums(string sid)
        {
            var msg = new StompMessage { Type = ServerActions.GetSubForums };

            msg.Headers.Add("sid", sid);

            _subforums = null;
            _client.OnReceived += HandleGetSubForumsResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
            return _subforums;
        }

        private void HandleGetSubForumsResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.GetSubForums) return;
            FillError(msg);
            _subforums = msg.Content.Trim().Split('\n');
            _client.OnReceived -= HandleGetSubForumsResponse;
            _waitEvent.Set();
        }

        #endregion

        #region Login Guest

        public string LoginGuest(string forumName)
        {
            var msg = new StompMessage { Type = ServerActions.LoginGuest };

            msg.Headers.Add("forum", forumName);

            _sid = string.Empty;
            _client.OnReceived += HandleLoginGuestRequest;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
            return _sid;
        }

        private void HandleLoginGuestRequest(object sender, StompMessage e)
        {
            if (e.Type != ServerActions.LoginGuest) return;
            FillError(e);
            _client.OnReceived -= HandleLoginGuestRequest;
            _sid = e.Content;
            _waitEvent.Set();
        }

        #endregion

        #region Add New Sub Forum
        public void AddNewSubForum(string sid, string subforum, string[] moderators)
        {
            var msg = new StompMessage { Type = ServerActions.AddNewSubForum };

            msg.Headers.Add("sid", sid);
            msg.Headers.Add("subforum", subforum);
            var builder = new StringBuilder();
            foreach (var moderator in moderators)
            {
                builder.AppendLine(moderator);
            }
            msg.Content = builder.ToString().Trim();

            _client.OnReceived += HandleAddNewSubForumResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleAddNewSubForumResponse(object sender, StompMessage e)
        {
            if (e.Type != ServerActions.AddNewSubForum) return;
            FillError(e);
            _client.OnReceived -= HandleAddNewSubForumResponse;
            _waitEvent.Set();
        }
        #endregion

        #region Remove New Sub Forum
        public void RemoveNewSubForum(string sid, string subforum, string[] moderators)
        {
            var msg = new StompMessage { Type = ServerActions.RemoveSubForum };

            msg.Headers.Add("sid", sid);
            msg.Headers.Add("subforum", subforum);
            var builder = new StringBuilder();
            foreach (var moderator in moderators)
            {
                builder.AppendLine(moderator);
            }
            msg.Content = builder.ToString().Trim();

            _client.OnReceived += HandleRemoveSubForumResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleRemoveSubForumResponse(object sender, StompMessage e)
        {
            if (e.Type != ServerActions.RemoveSubForum) return;
            FillError(e);
            _client.OnReceived -= HandleRemoveSubForumResponse;
            _waitEvent.Set();
        }
        #endregion

        #region Add New Thread

        public void AddNewThread(string sid, string subforum, string title, string body)
        {
            var msg = new StompMessage { Type = ServerActions.AddNewThread };

            msg.Headers.Add("sid", sid);
            msg.Headers.Add("subforum", subforum);
            msg.Headers.Add("title", title);
            msg.Content = body;

            _client.OnReceived += HandleAddNewThreadResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleAddNewThreadResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.AddNewThread) return;
            FillError(msg);
            _client.OnReceived -= HandleAddNewThreadResponse;
            _waitEvent.Set();
        }

        #endregion

        #region Remove Message

        public void RemoveMessage(string sid, string subforum, string msgid)
        {
            var msg = new StompMessage { Type = ServerActions.RemoveMessage };

            msg.Headers.Add("sid", sid);
            msg.Headers.Add("subforum", subforum);
            msg.Headers.Add("msgid", msgid);

            _client.OnReceived += HandleRemoveMessageResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleRemoveMessageResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.RemoveMessage) return;
            FillError(msg);
            _client.OnReceived -= HandleRemoveMessageResponse;
            _waitEvent.Set();
        }

        #endregion

        #region Post Comment
        
        public void PostComment(string sid, string subforum, string parentId, string title, string body)
        {
            var msg = new StompMessage { Type = ServerActions.PostComment };

            msg.Headers.Add("sid", sid);
            msg.Headers.Add("subforum", subforum);
            msg.Headers.Add("parent", parentId);
            msg.Headers.Add("title", title);
            msg.Content = body;

            _client.OnReceived += HandlePostCommentResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }
       
        private void HandlePostCommentResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.PostComment) return;
            FillError(msg);
            _client.OnReceived -= HandlePostCommentResponse;
            _waitEvent.Set();
        }

        #endregion

        #region Edit Message

        public void EditMessage(string sid, string subforum, string msgid, string title, string body)
        {
            var msg = new StompMessage { Type = ServerActions.EditMessage };

            msg.Headers.Add("sid", sid);
            msg.Headers.Add("subforum", subforum);
            msg.Headers.Add("msgid", msgid);
            msg.Headers.Add("title", title);
            msg.Content = body;

            _client.OnReceived += HandleEditMessageResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleEditMessageResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.EditMessage) return;
            FillError(msg);
            _client.OnReceived -= HandleEditMessageResponse;
            _waitEvent.Set();
        }

        #endregion

        #region Get Threads
        public IEnumerable<Message> GetThreads(string sid, string subforum)
        {
            var msg = new StompMessage { Type = ServerActions.GetThreads };

            msg.Headers.Add("sid", sid);
            msg.Headers.Add("subforum", subforum);

            _client.OnReceived += HandleGetThreadsResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
            return _messages;
        }

        private void HandleGetThreadsResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.GetThreads) return;
            FillError(msg);
            _client.OnReceived -= HandleGetThreadsResponse;

            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(IEnumerable<Message>));
            using (var stream = new MemoryStream(Encoding.UTF8.GetBytes(msg.Content)))
            {
                IEnumerable<Message> messages = (IEnumerable<Message>)serializer.ReadObject(stream);

                //JsonValue value = JsonObject.Parse(msg.Content);
                _messages = messages;

                _waitEvent.Set();
            }
        }
        #endregion


        /*
        *** TODO LIST: ***
        7. get reports ~~
        */
    }
}
