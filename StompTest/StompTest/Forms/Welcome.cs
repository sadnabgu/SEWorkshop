﻿using System;
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
            lstForums.Items.Clear();
            foreach (var forum in Program.Server.GetForums())
            {
                lstForums.Items.Add(forum);
            }
        }
    }
}