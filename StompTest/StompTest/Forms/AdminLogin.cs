using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace StompTest.Forms
{
    public partial class AdminLogin : Form
    {
        public AdminLogin()
        {
            InitializeComponent();
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            try
            {
                Program.SID = Program.Server.Admin.LogInAdmin(txtUser.Text, txtPass.Text);
            }
            catch (Exception ex)
            {
                Program.SID = string.Empty;
            }
            if (Program.SID == null || Program.SID == string.Empty)
            {
                this.DialogResult = DialogResult.No;
            }
            else
            {
                this.DialogResult = DialogResult.OK;
            }

            this.Close();
        }
    }
}
