using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace StompTest
{
    public partial class ForumManagement : Form
    {
        private readonly string _forumName;
        public ForumManagement(string forumName)
        {
            InitializeComponent();

            _forumName = forumName;
            LblForum.Text = forumName;
        }

        private void UpdateSubForums()
        {
            lstSubForums.Items.Clear();
            string[] subforums = Program.Server.Forum.GetSubForums(Program.SID);
            foreach (var subforum in subforums)
            {
                lstSubForums.Items.Add(subforum);
            }
            lstSubForums.Refresh();
        }

        private void ForumManagement_Load(object sender, EventArgs e)
        {
            //Program.Server.Forum.
        }

        private void btnAddMod_Click(object sender, EventArgs e)
        {
            if (txtModName.Text != string.Empty && !lstModerators.Items.Contains(txtModName.Text))
            {
                lstModerators.Items.Add(txtModName.Text);
                lstModerators.Refresh();
            }
        }

        private void btnRemMod_Click(object sender, EventArgs e)
        {
            lstModerators.Items.Remove(lstModerators.SelectedItem);
            lstModerators.Refresh();
        }

        private void btnAddSubForum_Click(object sender, EventArgs e)
        {
            if (txtSubForumName.Text != string.Empty)
            {

                string[] mods = new string[lstModerators.Items.Count];
                for (int i = 0; i < mods.Length; i++)
                {
                    mods[i] = (string) lstModerators.Items[i];
                }

                Program.Server.Forum.AddNewSubForum(Program.SID, 
                                                    txtSubForumName.Text, 
                                                    mods);

                StringBuilder builder = new StringBuilder();
                foreach (var str in mods)
                {
                    builder.AppendLine(str);
                }
                MessageBox.Show($"Name: {txtSubForumName.Text} \nModerators: \n{builder}", @"Sub-Forum added");
                txtModName.Text = string.Empty;
                txtSubForumName.Text = string.Empty;
                lstModerators.Items.Clear();
                lstModerators.Refresh();
                UpdateSubForums();
            }
            
        }

        private void btnRemoveSubforum_Click(object sender, EventArgs e)
        {
            Program.Server.Forum.RemoveNewSubForum(Program.SID, (string)lstSubForums.SelectedItem, new string[] {});
            UpdateSubForums();
        }
    }
}
