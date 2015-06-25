namespace StompTest
{
    partial class ForumManagement
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtSubForumName = new System.Windows.Forms.TextBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.lstModerators = new System.Windows.Forms.ListBox();
            this.btnRemMod = new System.Windows.Forms.Button();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.lstSubForums = new System.Windows.Forms.ListBox();
            this.btnRemoveSubforum = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.txtModName = new System.Windows.Forms.TextBox();
            this.btnAddMod = new System.Windows.Forms.Button();
            this.btnAddSubForum = new System.Windows.Forms.Button();
            this.LblForum = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.btnAddSubForum);
            this.groupBox1.Controls.Add(this.groupBox2);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.txtSubForumName);
            this.groupBox1.Location = new System.Drawing.Point(12, 43);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(245, 421);
            this.groupBox1.TabIndex = 16;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Add new sub forum";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(15, 35);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(35, 13);
            this.label3.TabIndex = 5;
            this.label3.Text = "Name";
            // 
            // txtSubForumName
            // 
            this.txtSubForumName.Location = new System.Drawing.Point(118, 35);
            this.txtSubForumName.Name = "txtSubForumName";
            this.txtSubForumName.Size = new System.Drawing.Size(100, 20);
            this.txtSubForumName.TabIndex = 1;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.btnAddMod);
            this.groupBox2.Controls.Add(this.label1);
            this.groupBox2.Controls.Add(this.lstModerators);
            this.groupBox2.Controls.Add(this.txtModName);
            this.groupBox2.Controls.Add(this.btnRemMod);
            this.groupBox2.Location = new System.Drawing.Point(9, 84);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(223, 303);
            this.groupBox2.TabIndex = 18;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Moderators";
            // 
            // lstModerators
            // 
            this.lstModerators.FormattingEnabled = true;
            this.lstModerators.Location = new System.Drawing.Point(9, 74);
            this.lstModerators.Name = "lstModerators";
            this.lstModerators.Size = new System.Drawing.Size(197, 199);
            this.lstModerators.TabIndex = 11;
            // 
            // btnRemMod
            // 
            this.btnRemMod.Location = new System.Drawing.Point(9, 276);
            this.btnRemMod.Name = "btnRemMod";
            this.btnRemMod.Size = new System.Drawing.Size(75, 21);
            this.btnRemMod.TabIndex = 12;
            this.btnRemMod.Text = "Remove";
            this.btnRemMod.UseVisualStyleBackColor = true;
            this.btnRemMod.Click += new System.EventHandler(this.btnRemMod_Click);
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.lstSubForums);
            this.groupBox3.Controls.Add(this.btnRemoveSubforum);
            this.groupBox3.Location = new System.Drawing.Point(263, 43);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(223, 421);
            this.groupBox3.TabIndex = 18;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Sub Forums";
            // 
            // lstSubForums
            // 
            this.lstSubForums.FormattingEnabled = true;
            this.lstSubForums.Location = new System.Drawing.Point(9, 32);
            this.lstSubForums.Name = "lstSubForums";
            this.lstSubForums.Size = new System.Drawing.Size(197, 355);
            this.lstSubForums.TabIndex = 11;
            // 
            // btnRemoveSubforum
            // 
            this.btnRemoveSubforum.Location = new System.Drawing.Point(9, 394);
            this.btnRemoveSubforum.Name = "btnRemoveSubforum";
            this.btnRemoveSubforum.Size = new System.Drawing.Size(75, 21);
            this.btnRemoveSubforum.TabIndex = 12;
            this.btnRemoveSubforum.Text = "Remove";
            this.btnRemoveSubforum.UseVisualStyleBackColor = true;
            this.btnRemoveSubforum.Click += new System.EventHandler(this.btnRemoveSubforum_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(9, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(35, 13);
            this.label1.TabIndex = 20;
            this.label1.Text = "Name";
            // 
            // txtModName
            // 
            this.txtModName.Location = new System.Drawing.Point(109, 21);
            this.txtModName.Name = "txtModName";
            this.txtModName.Size = new System.Drawing.Size(100, 20);
            this.txtModName.TabIndex = 19;
            // 
            // btnAddMod
            // 
            this.btnAddMod.Location = new System.Drawing.Point(6, 48);
            this.btnAddMod.Name = "btnAddMod";
            this.btnAddMod.Size = new System.Drawing.Size(75, 21);
            this.btnAddMod.TabIndex = 21;
            this.btnAddMod.Text = "Add";
            this.btnAddMod.UseVisualStyleBackColor = true;
            this.btnAddMod.Click += new System.EventHandler(this.btnAddMod_Click);
            // 
            // btnAddSubForum
            // 
            this.btnAddSubForum.Location = new System.Drawing.Point(18, 394);
            this.btnAddSubForum.Name = "btnAddSubForum";
            this.btnAddSubForum.Size = new System.Drawing.Size(75, 21);
            this.btnAddSubForum.TabIndex = 22;
            this.btnAddSubForum.Text = "Add";
            this.btnAddSubForum.UseVisualStyleBackColor = true;
            this.btnAddSubForum.Click += new System.EventHandler(this.btnAddSubForum_Click);
            // 
            // LblForum
            // 
            this.LblForum.AutoSize = true;
            this.LblForum.Location = new System.Drawing.Point(24, 9);
            this.LblForum.Name = "LblForum";
            this.LblForum.Size = new System.Drawing.Size(35, 13);
            this.LblForum.TabIndex = 23;
            this.LblForum.Text = "Name";
            // 
            // ForumManagement
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(494, 476);
            this.Controls.Add(this.LblForum);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox1);
            this.Name = "ForumManagement";
            this.Text = "ForumManagement";
            this.Load += new System.EventHandler(this.ForumManagement_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtSubForumName;
        private System.Windows.Forms.Button btnAddSubForum;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button btnAddMod;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ListBox lstModerators;
        private System.Windows.Forms.TextBox txtModName;
        private System.Windows.Forms.Button btnRemMod;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.ListBox lstSubForums;
        private System.Windows.Forms.Button btnRemoveSubforum;
        private System.Windows.Forms.Label LblForum;
    }
}