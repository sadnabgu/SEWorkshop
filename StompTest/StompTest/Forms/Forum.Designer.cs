namespace StompTest.Forms
{
    partial class Forum
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
            this.lstSubForums = new System.Windows.Forms.ListBox();
            this.gbSubForum = new System.Windows.Forms.GroupBox();
            this.threads = new System.Windows.Forms.TreeView();
            this.lblError = new System.Windows.Forms.Label();
            this.lblPost = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.txtBody = new System.Windows.Forms.RichTextBox();
            this.txtTitle = new System.Windows.Forms.TextBox();
            this.lblWelcome = new System.Windows.Forms.Label();
            this.lblLogin = new System.Windows.Forms.Label();
            this.lblSignin = new System.Windows.Forms.Label();
            this.lblLogOut = new System.Windows.Forms.Label();
            this.lblManage = new System.Windows.Forms.Label();
            this.lblNewThread = new System.Windows.Forms.Label();
            this.lblEdit = new System.Windows.Forms.Label();
            this.lblRem = new System.Windows.Forms.Label();
            this.gbSubForum.SuspendLayout();
            this.SuspendLayout();
            // 
            // lstSubForums
            // 
            this.lstSubForums.FormattingEnabled = true;
            this.lstSubForums.Location = new System.Drawing.Point(12, 49);
            this.lstSubForums.Name = "lstSubForums";
            this.lstSubForums.Size = new System.Drawing.Size(93, 420);
            this.lstSubForums.TabIndex = 0;
            this.lstSubForums.SelectedValueChanged += new System.EventHandler(this.lstSubForums_SelectedValueChanged);
            // 
            // gbSubForum
            // 
            this.gbSubForum.Controls.Add(this.lblRem);
            this.gbSubForum.Controls.Add(this.lblEdit);
            this.gbSubForum.Controls.Add(this.lblNewThread);
            this.gbSubForum.Controls.Add(this.threads);
            this.gbSubForum.Controls.Add(this.lblError);
            this.gbSubForum.Controls.Add(this.lblPost);
            this.gbSubForum.Controls.Add(this.label2);
            this.gbSubForum.Controls.Add(this.label1);
            this.gbSubForum.Controls.Add(this.txtBody);
            this.gbSubForum.Controls.Add(this.txtTitle);
            this.gbSubForum.Location = new System.Drawing.Point(111, 29);
            this.gbSubForum.Name = "gbSubForum";
            this.gbSubForum.Size = new System.Drawing.Size(360, 443);
            this.gbSubForum.TabIndex = 2;
            this.gbSubForum.TabStop = false;
            this.gbSubForum.Text = "Sub Forum Name";
            // 
            // threads
            // 
            this.threads.Location = new System.Drawing.Point(9, 19);
            this.threads.Name = "threads";
            this.threads.Size = new System.Drawing.Size(345, 271);
            this.threads.TabIndex = 10;
            // 
            // lblError
            // 
            this.lblError.AutoSize = true;
            this.lblError.ForeColor = System.Drawing.Color.Red;
            this.lblError.Location = new System.Drawing.Point(165, 427);
            this.lblError.Name = "lblError";
            this.lblError.Size = new System.Drawing.Size(39, 13);
            this.lblError.TabIndex = 8;
            this.lblError.Text = "lblError";
            this.lblError.Visible = false;
            // 
            // lblPost
            // 
            this.lblPost.AutoSize = true;
            this.lblPost.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.lblPost.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lblPost.Location = new System.Drawing.Point(317, 403);
            this.lblPost.Name = "lblPost";
            this.lblPost.Size = new System.Drawing.Size(34, 13);
            this.lblPost.TabIndex = 8;
            this.lblPost.Text = "Reply";
            this.lblPost.Click += new System.EventHandler(this.lblPost_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(6, 325);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(31, 13);
            this.label2.TabIndex = 9;
            this.label2.Text = "Body";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(6, 299);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(27, 13);
            this.label1.TabIndex = 8;
            this.label1.Text = "Title";
            // 
            // txtBody
            // 
            this.txtBody.Location = new System.Drawing.Point(60, 322);
            this.txtBody.Name = "txtBody";
            this.txtBody.Size = new System.Drawing.Size(294, 78);
            this.txtBody.TabIndex = 1;
            this.txtBody.Text = "";
            // 
            // txtTitle
            // 
            this.txtTitle.Location = new System.Drawing.Point(60, 296);
            this.txtTitle.Name = "txtTitle";
            this.txtTitle.Size = new System.Drawing.Size(294, 20);
            this.txtTitle.TabIndex = 0;
            // 
            // lblWelcome
            // 
            this.lblWelcome.AutoSize = true;
            this.lblWelcome.Location = new System.Drawing.Point(13, 13);
            this.lblWelcome.Name = "lblWelcome";
            this.lblWelcome.Size = new System.Drawing.Size(83, 13);
            this.lblWelcome.TabIndex = 3;
            this.lblWelcome.Text = "Welcome Guest";
            // 
            // lblLogin
            // 
            this.lblLogin.AutoSize = true;
            this.lblLogin.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.lblLogin.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lblLogin.Location = new System.Drawing.Point(382, 13);
            this.lblLogin.Name = "lblLogin";
            this.lblLogin.Size = new System.Drawing.Size(36, 13);
            this.lblLogin.TabIndex = 4;
            this.lblLogin.Text = "Log in";
            this.lblLogin.Click += new System.EventHandler(this.lblLogin_Click);
            // 
            // lblSignin
            // 
            this.lblSignin.AutoSize = true;
            this.lblSignin.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.lblSignin.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lblSignin.Location = new System.Drawing.Point(428, 13);
            this.lblSignin.Name = "lblSignin";
            this.lblSignin.Size = new System.Drawing.Size(43, 13);
            this.lblSignin.TabIndex = 5;
            this.lblSignin.Text = "Sign up";
            // 
            // lblLogOut
            // 
            this.lblLogOut.AutoSize = true;
            this.lblLogOut.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.lblLogOut.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lblLogOut.Location = new System.Drawing.Point(108, 13);
            this.lblLogOut.Name = "lblLogOut";
            this.lblLogOut.Size = new System.Drawing.Size(43, 13);
            this.lblLogOut.TabIndex = 6;
            this.lblLogOut.Text = "Log out";
            this.lblLogOut.Visible = false;
            this.lblLogOut.Click += new System.EventHandler(this.lblLogOut_Click);
            // 
            // lblManage
            // 
            this.lblManage.AutoSize = true;
            this.lblManage.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.lblManage.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lblManage.Location = new System.Drawing.Point(168, 13);
            this.lblManage.Name = "lblManage";
            this.lblManage.Size = new System.Drawing.Size(46, 13);
            this.lblManage.TabIndex = 7;
            this.lblManage.Text = "Manage";
            this.lblManage.Visible = false;
            this.lblManage.Click += new System.EventHandler(this.lblManage_Click);
            // 
            // lblNewThread
            // 
            this.lblNewThread.AutoSize = true;
            this.lblNewThread.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.lblNewThread.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lblNewThread.Location = new System.Drawing.Point(249, 403);
            this.lblNewThread.Name = "lblNewThread";
            this.lblNewThread.Size = new System.Drawing.Size(62, 13);
            this.lblNewThread.TabIndex = 11;
            this.lblNewThread.Text = "New thread";
            this.lblNewThread.Click += new System.EventHandler(this.lblNewThread_Click);
            // 
            // lblEdit
            // 
            this.lblEdit.AutoSize = true;
            this.lblEdit.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.lblEdit.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lblEdit.Location = new System.Drawing.Point(218, 403);
            this.lblEdit.Name = "lblEdit";
            this.lblEdit.Size = new System.Drawing.Size(25, 13);
            this.lblEdit.TabIndex = 12;
            this.lblEdit.Text = "Edit";
            this.lblEdit.Click += new System.EventHandler(this.lblEdit_Click);
            // 
            // lblRem
            // 
            this.lblRem.AutoSize = true;
            this.lblRem.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.lblRem.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lblRem.Location = new System.Drawing.Point(165, 403);
            this.lblRem.Name = "lblRem";
            this.lblRem.Size = new System.Drawing.Size(47, 13);
            this.lblRem.TabIndex = 13;
            this.lblRem.Text = "Remove";
            this.lblRem.Click += new System.EventHandler(this.lblRem_Click);
            // 
            // Forum
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(483, 484);
            this.Controls.Add(this.lblManage);
            this.Controls.Add(this.lblLogOut);
            this.Controls.Add(this.lblSignin);
            this.Controls.Add(this.lblLogin);
            this.Controls.Add(this.lblWelcome);
            this.Controls.Add(this.gbSubForum);
            this.Controls.Add(this.lstSubForums);
            this.Name = "Forum";
            this.Text = "Forum";
            this.Load += new System.EventHandler(this.Forum_Load);
            this.MouseEnter += new System.EventHandler(this.Forum_MouseEnter);
            this.gbSubForum.ResumeLayout(false);
            this.gbSubForum.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ListBox lstSubForums;
        private System.Windows.Forms.GroupBox gbSubForum;
        private System.Windows.Forms.Label lblWelcome;
        private System.Windows.Forms.Label lblLogin;
        private System.Windows.Forms.Label lblSignin;
        private System.Windows.Forms.Label lblLogOut;
        private System.Windows.Forms.Label lblManage;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.RichTextBox txtBody;
        private System.Windows.Forms.TextBox txtTitle;
        private System.Windows.Forms.Label lblPost;
        private System.Windows.Forms.Label lblError;
        private System.Windows.Forms.TreeView threads;
        private System.Windows.Forms.Label lblNewThread;
        private System.Windows.Forms.Label lblEdit;
        private System.Windows.Forms.Label lblRem;
    }
}