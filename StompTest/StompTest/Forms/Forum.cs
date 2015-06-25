using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

namespace StompTest.Forms
{
    public partial class Forum : Form
    {
        private readonly string _forumName;

        public Forum(string forumName)
        {
            InitializeComponent();
            _forumName = forumName;
            try
            {
                Program.SID = Program.Server.LogInGuest(forumName);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to establish session");
            }
        }

        private void Forum_Load(object sender, EventArgs e)
        {
            this.Text = _forumName;

            lstSubForums.Items.Clear();
            //Program.Server.Forum.
        }

        private void lblLogin_Click(object sender, EventArgs e)
        {
            LogIn login = new LogIn(SetWelcome);

            login.Show();
        }

        private void SetWelcome(string text)
        {
            lblWelcome.Text = "Hello " +text;
            lblLogin.Visible = false;
            lblLogOut.Visible = true;
            lblManage.Visible = true;
            lblSignup.Visible = false;
        }

        private void lblLogOut_Click(object sender, EventArgs e)
        {
            try
            {
                Program.Server.User.LogoutMember(Program.SID);
                lblWelcome.Text = "Welcome Guest";
                lblLogin.Visible = true;
                lblLogOut.Visible = false;
                lblManage.Visible = false;
                lblSignup.Visible = true;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to log out");
            }
        }

        private void lblManage_Click(object sender, EventArgs e)
        {
            ForumManagement management = new ForumManagement(_forumName);

            management.Show();
        }

        private void Forum_MouseEnter(object sender, EventArgs e)
        {
            try
            {
                string[] subforums = Program.Server.Forum.GetSubForums(Program.SID);
                if (subforums == null) return;

                lstSubForums.Items.Clear();
                foreach (var subforum in subforums)
                {
                    lstSubForums.Items.Add(subforum);
                }
                lstSubForums.Refresh();
            }
            catch (Exception ex)
            {
                //TODO: some error messge?
            }
        }

        private void lstSubForums_SelectedValueChanged(object sender, EventArgs e)
        {
            if (lstSubForums.SelectedItem == null) return;
            gbSubForum.Text = lstSubForums.SelectedItem.ToString();
            UpdateAllThreads();
        }

        private void lblPost_Click(object sender, EventArgs e)
        {
            try
            {
                if (txtTitle.Text == string.Empty)
                {
                    lblError.Text = "Cannot post empty title";
                    lblError.Visible = true;
                    return;
                }

                if (threads.SelectedNode == null)
                {
                    Program.Server.Forum.AddNewThread(Program.SID, gbSubForum.Text, txtTitle.Text, txtBody.Text);
                }
                else
                {
                    Program.Server.Forum.PostComment(Program.SID, gbSubForum.Text,
                        ((Message) threads.SelectedNode.Tag)._id, txtTitle.Text, txtBody.Text);
                }

                UpdateAllThreads();

                lblError.Visible = false;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to post message");
            }
        }

        private void UpdateAllThreads()
        {
            try
            {
                IEnumerable<Message> messages = Program.Server.Forum.GetThreads(Program.SID, gbSubForum.Text);

                if (messages == null) return;

                threads.BeginUpdate();
                threads.Nodes.Clear();
                foreach (var msg in messages)
                {
                    TreeNode threadNode = new TreeNode(msg._title);
                    var bodNode = new TreeNode(msg._body) {ForeColor = Color.DarkSlateGray};
                    var byNode = new TreeNode(msg._creator) {ForeColor = Color.Blue};
                    var repNode = new TreeNode("replies (" + msg._replies.Length + ")") {ForeColor = Color.Chocolate};

                    threadNode.Nodes.Add(bodNode);
                    threadNode.Nodes.Add(byNode);
                    threadNode.Nodes.Add(repNode);
                    threadNode.Tag = msg;
                    bodNode.Tag = msg;
                    repNode.Tag = msg;
                    byNode.Tag = msg;
                    threads.Nodes.Add(threadNode);
                    foreach (var reply in msg._replies)
                    {
                        UpdateNode(repNode, reply);
                    }
                }

                threads.EndUpdate();
                threads.Refresh();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to update threads");
            }
        }

        private void UpdateNode(TreeNode parentNode, Message msg)
        {
            TreeNode threadNode = new TreeNode(msg._title);
            var bodNode = new TreeNode(msg._body) { ForeColor = Color.DarkSlateGray };
            var byNode = new TreeNode(msg._creator) { ForeColor = Color.Blue };
            var repNode = new TreeNode("replies (" + msg._replies.Length + ")") { ForeColor = Color.Chocolate };

            threadNode.Nodes.Add(bodNode);
            threadNode.Nodes.Add(byNode);
            threadNode.Nodes.Add(repNode);
            threadNode.Tag = msg;
            bodNode.Tag = msg;
            repNode.Tag = msg;
            byNode.Tag = msg;
            parentNode.Nodes.Add(threadNode);
            foreach (var reply in msg._replies)
            {
                UpdateNode(repNode, reply);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            MessageBox.Show((threads.SelectedNode == null || threads.Nodes.Contains(threads.SelectedNode)).ToString());
        }

        private void lblNewThread_Click(object sender, EventArgs e)
        {
            try
            {
                if (txtTitle.Text == string.Empty)
                {
                    lblError.Text = "Cannot post empty title";
                    lblError.Visible = true;
                    return;
                }

                Program.Server.Forum.AddNewThread(Program.SID, gbSubForum.Text, txtTitle.Text, txtBody.Text);

                UpdateAllThreads();

                lblError.Visible = false;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to add thread");
            }
        }

        private void lblEdit_Click(object sender, EventArgs e)
        {
            try
            {
                if (threads.SelectedNode == null) return;
                var result = MessageBox.Show("Are you sure you want to edit", "Edit Message", MessageBoxButtons.OKCancel);
                if (result != DialogResult.OK) return;
                if (txtTitle.Text == string.Empty)
                {
                    lblError.Text = "Cannot post empty title";
                    lblError.Visible = true;
                    return;
                }

                string id = ((Message) threads.SelectedNode.Tag)._id;
                Program.Server.Forum.EditMessage(Program.SID, gbSubForum.Text, id, txtTitle.Text, txtBody.Text);
                UpdateAllThreads();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to edit message");
            }
        }

        private void lblRem_Click(object sender, EventArgs e)
        {
            try
            {
                if (threads.SelectedNode == null) return;
                var result = MessageBox.Show("Are you sure you want to remove", "Remove Message",
                    MessageBoxButtons.OKCancel);
                if (result != DialogResult.OK) return;

                string id = ((Message) threads.SelectedNode.Tag)._id;
                Program.Server.Forum.RemoveMessage(Program.SID, gbSubForum.Text, id);
                UpdateAllThreads();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to remove message");
            }
        }

        private void lblSignup_Click(object sender, EventArgs e)
        {
            SignUp signUp = new SignUp();
            signUp.ShowDialog();
        }
    }
}
