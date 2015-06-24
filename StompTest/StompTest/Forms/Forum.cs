using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
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
            Program.SID = Program.Server.LogInGuest(forumName);

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
        }

        private void lblLogOut_Click(object sender, EventArgs e)
        {
            Program.Server.User.LogoutMember(Program.SID);
            
            lblWelcome.Text = "Welcome Guest";
            lblLogin.Visible = true;
            lblLogOut.Visible = false;
            lblManage.Visible = false;
        }

        private void lblManage_Click(object sender, EventArgs e)
        {
            ForumManagement management = new ForumManagement(_forumName);

            management.Show();
        }

        private void Forum_MouseEnter(object sender, EventArgs e)
        {
            lstSubForums.Items.Clear();
            foreach(var subforum in Program.Server.Forum.GetSubForums(Program.SID))
            {
                lstSubForums.Items.Add(subforum);
            }
            lstSubForums.Refresh();
        }

        private void lstSubForums_SelectedValueChanged(object sender, EventArgs e)
        {
            gbSubForum.Text = lstSubForums.SelectedItem.ToString();
        }
    }
}
