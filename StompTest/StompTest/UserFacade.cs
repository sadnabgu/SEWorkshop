using System.Threading;

namespace StompTest
{
    public class UserFacade : AbstractFacade
    {
        internal UserFacade(StompClient client, AutoResetEvent waitEvent) : base(client, waitEvent){}

        #region Add Moderator
        public void AddModerator(string sid, string subforum, string moderator)
        {
            var msg = new StompMessage() { Type = ServerActions.AddModerator };
            msg.Headers.Add("sid", sid);
            msg.Headers.Add("subforum", subforum);
            msg.Headers.Add("moderator", moderator);

            _client.OnReceived += HandleAddModeratorResponse;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();

        }

        private void HandleAddModeratorResponse(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.AddModerator) return;
            FillError(msg);
            _client.OnReceived -= HandleAddModeratorResponse;
            _waitEvent.Set();
        }

        #endregion

        #region Add Friend
        public void AddFriend(string sid, string friend)
        {
            var msg = new StompMessage { Type = ServerActions.AddFriend };
            msg.Headers.Add("sid", sid);
            msg.Headers.Add("friend", friend);

            _client.OnReceived += HandleAddFriendRequest;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleAddFriendRequest(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.AddFriend) return;
            FillError(msg);
            _client.OnReceived -= HandleAddFriendRequest;
            _waitEvent.Set();
        }

        #endregion

        #region Remove Friend
        public void RemoveFriend(string sid, string friend)
        {
            var msg = new StompMessage { Type = ServerActions.RemoveFriend };
            msg.Headers.Add("sid", sid);
            msg.Headers.Add("friend", friend);

            _client.OnReceived += HandleRemoveFriendRequest;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleRemoveFriendRequest(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.RemoveFriend) return;
            FillError(msg);
            _client.OnReceived -= HandleRemoveFriendRequest;
            _waitEvent.Set();
        }
        #endregion

        #region Login Member
        public void LoginMember(string sid, string username, string password)
        {
            var msg = new StompMessage { Type = ServerActions.LoginMember };
            msg.Headers.Add("sid", sid);
            msg.Headers.Add("username", username);
            msg.Headers.Add("password", password);

            _client.OnReceived += HandleLoginMemberRequest;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleLoginMemberRequest(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.LoginMember) return;
            FillError(msg);
            _client.OnReceived -= HandleLoginMemberRequest;
            _waitEvent.Set();
        }
        #endregion

        #region Log Out
        public void LogoutMember(string sid)
        {
            var msg = new StompMessage { Type = ServerActions.LogoutMember };
            msg.Headers.Add("sid", sid);
            
            _client.OnReceived += HandleLogoutMemberRequest;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleLogoutMemberRequest(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.LogoutMember) return;
            FillError(msg);
            _client.OnReceived -= HandleLogoutMemberRequest;
            _waitEvent.Set();
        }
        #endregion

        #region Register
        public void Register(string sid, string username, string password)
        {
            var msg = new StompMessage { Type = ServerActions.Register };
            msg.Headers.Add("sid", sid);
            msg.Headers.Add("username", username);
            msg.Headers.Add("password", password);

            _client.OnReceived += HandleRegisterRequest;
            _client.Send(msg);
            _waitEvent.WaitOne(TIMEOUT);

            ValidateError();
        }

        private void HandleRegisterRequest(object sender, StompMessage msg)
        {
            if (msg.Type != ServerActions.Register) return;
            FillError(msg);
            _client.OnReceived -= HandleRegisterRequest;
            _waitEvent.Set();
        }
        #endregion

        /*
        TODO LIST:
        2. logout
        7. submit complaint
        */
    }
}
