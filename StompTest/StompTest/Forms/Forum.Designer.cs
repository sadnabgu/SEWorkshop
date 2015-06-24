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
            this.lblWelcome = new System.Windows.Forms.Label();
            this.lblLogin = new System.Windows.Forms.Label();
            this.lblSignin = new System.Windows.Forms.Label();
            this.lblLogOut = new System.Windows.Forms.Label();
            this.lblManage = new System.Windows.Forms.Label();
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
            this.gbSubForum.Location = new System.Drawing.Point(111, 49);
            this.gbSubForum.Name = "gbSubForum";
            this.gbSubForum.Size = new System.Drawing.Size(360, 420);
            this.gbSubForum.TabIndex = 2;
            this.gbSubForum.TabStop = false;
            this.gbSubForum.Text = "Sub Forum Name";
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
            this.lblManage.ForeColor = System.Drawing.SystemColors.HotTrack;
            this.lblManage.Location = new System.Drawing.Point(168, 13);
            this.lblManage.Name = "lblManage";
            this.lblManage.Size = new System.Drawing.Size(46, 13);
            this.lblManage.TabIndex = 7;
            this.lblManage.Text = "Manage";
            this.lblManage.Visible = false;
            this.lblManage.Click += new System.EventHandler(this.lblManage_Click);
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
    }
}