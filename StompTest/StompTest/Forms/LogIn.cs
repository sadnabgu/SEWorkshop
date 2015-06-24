using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace StompTest.Forms
{
    public partial class LogIn : Form
    {
        public Action<string> callback;
       

        public LogIn(Action<string> callback)
        {
            this.callback = callback;
            InitializeComponent();
       
            lblError.Visible = false;
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            if (txtPass.Text == string.Empty || txtUser.Text == string.Empty)
            {
                lblError.Text = "Empty input or error logging in";
                lblError.Visible = true;
            }

            Program.Server.User.LoginMember(Program.SID, txtUser.Text, txtPass.Text);
            callback.Invoke(txtUser.Text);
            this.Visible = false;
            this.Close();
        }
    }
}
