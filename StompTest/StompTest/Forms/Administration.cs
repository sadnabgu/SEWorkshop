using System;
using System.Windows.Forms;

namespace StompTest.Forms
{
    public partial class Administration : Form
    {
        public Administration()
        {
            InitializeComponent();
            UpdateForums();
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            try
            {
                if (ValidateForm())
                {
                    lblError.Visible = false;
                    Program.Server.Admin.CreateForum(Program.SID, txtForum.Text, txtAdmin.Text, txtPass.Text);
                }
                else
                {
                    new Exception("Some data is missing or forum already exists");
                }
                UpdateForums();
            }
            catch (Exception ex)
            {
                lblError.Text = ex.Message;
                lblError.Visible = true;
            }
            
        }

        private bool ValidateForm()
        {
            bool emptyInput = txtPass.Text == string.Empty || txtAdmin.Text == string.Empty || txtForum.Text == string.Empty;
            bool forumExists = lstForums.Items.Contains(txtForum.Text);

            return (emptyInput == false) && (forumExists == false);
        }

        private void UpdateForums()
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
                MessageBox.Show(ex.Message, "Error loading forums");
            }
        }

        private void btnRemove_Click(object sender, EventArgs e)
        {
            try
            {
                if (lstForums.SelectedItem != null)
                {
                    Program.Server.Admin.RemoveForum(Program.SID, (string) lstForums.SelectedItem);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Failed to remove forum");
            }
            UpdateForums();
        }

        private void lstForums_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void Administration_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
            System.Diagnostics.Process.GetCurrentProcess().Kill();
        }

        private void Administration_Load(object sender, EventArgs e)
        {

        }

        private void Administration_FormClosing(object sender, FormClosingEventArgs e)
        {
            Application.Exit();
            System.Diagnostics.Process.GetCurrentProcess().Kill();
        }
    }
}
