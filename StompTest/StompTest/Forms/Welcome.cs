using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.VisualStyles;

namespace StompTest.Forms
{
    public partial class Welcome : Form
    {

        public Welcome()
        {
            InitializeComponent();
        }

        private void btnGoToForum_Click(object sender, EventArgs e)
        {
            var forum = new Forum((string)lstForums.SelectedItem);
            forum.Show();
            this.Visible = false;
        }

        private void Welcome_Load(object sender, EventArgs e)
        {
            try
            {
                lstForums.Items.Clear();
                foreach (var forum in Program.Server.GetForums())
                {
                    lstForums.Items.Add(forum);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to load forums");
            }
        }

        private void lblAdmin_Click(object sender, EventArgs e)
        {
            AdminLogin login = new AdminLogin();
            login.ShowDialog();
            if (login.DialogResult == DialogResult.OK)
            {
                Administration administration = new Administration();
                administration.Show();
            }

        }
    }
}
