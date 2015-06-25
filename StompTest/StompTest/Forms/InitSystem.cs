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
    public partial class InitSystem : Form
    {
        public InitSystem()
        {
            InitializeComponent();
        }

        private void btnInit_Click(object sender, EventArgs e)
        {
            try
            {
                if (validateForm())
                {
                    Program.Server.Admin.InitSystem(txtUser.Text, txtPass.Text);
                    Program.SID = Program.Server.Admin.LogInAdmin(txtUser.Text, txtPass.Text);
                    Visible = false;
                    var administration = new Administration();
                    administration.Show();
                }
                lblError.Text = "Empty user name or passwords do not match";
                lblError.Visible = true;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to initialize system");
            }
        }

        private bool validateForm()
        {
            return txtPass.Text != string.Empty && (txtPass.Text != string.Empty && txtPass.Text == txtRepass.Text);
        }
    }
}
