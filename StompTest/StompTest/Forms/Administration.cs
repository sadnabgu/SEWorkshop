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
            if (ValidateForm())
            {
                lblError.Visible = false;
                Program.Server.Admin.CreateForum(Program.SID, txtForum.Text, txtAdmin.Text, txtPass.Text);
            }
            else
            {
                lblError.Text = "Some data is missing or forum already exists";
                lblError.Visible = true;
            }
            UpdateForums();
        }

        private bool ValidateForm()
        {
            bool emptyInput = txtPass.Text == string.Empty || txtAdmin.Text == string.Empty || txtForum.Text == string.Empty;
            bool forumExists = lstForums.Items.Contains(txtForum.Text);

            return (emptyInput == false) && (forumExists == false);
        }

        private void UpdateForums()
        {
            lstForums.Items.Clear();
            foreach (var forum in Program.Server.GetForums())
            {
                lstForums.Items.Add(forum);
            }
        }

        private void btnRemove_Click(object sender, EventArgs e)
        {
            if (lstForums.SelectedItem != null)
            {
                Program.Server.Admin.RemoveForum(Program.SID, (string) lstForums.SelectedItem);
            }
            UpdateForums();
        }

        private void lstForums_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}
