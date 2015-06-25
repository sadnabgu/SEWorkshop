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
    public partial class SignUp : Form
    {
        public SignUp()
        {
            InitializeComponent();
        }

        private void btnClick_Click(object sender, EventArgs e)
        {
            try
            {
                if (txtUser.Text != string.Empty && txtPass.Text != string.Empty && txtPass.Text == txtRetype.Text)
                {
                    Program.Server.User.Register(Program.SID, txtUser.Text, txtPass.Text);
                    this.Visible = false;
                    this.Close();
                }
                else
                {
                    lblError.Visible = true;
                    lblError.Text = "Empty input or passwords does not match";
                }
            }
            catch (Exception ex)
            {
                lblError.Visible = true;
                lblError.Text = "Failed: " + ex.Message;
            }
        }
    }
}
