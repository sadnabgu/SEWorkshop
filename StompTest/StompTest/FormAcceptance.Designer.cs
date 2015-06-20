namespace StompTest
{
    partial class FormAcceptance
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormAcceptance));
            this.btnLogin = new System.Windows.Forms.Button();
            this.txtTestResult = new System.Windows.Forms.RichTextBox();
            this.btnGetForums = new System.Windows.Forms.Button();
            this.btnEnterForum = new System.Windows.Forms.Button();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.btnAdmin = new System.Windows.Forms.Button();
            this.btnLoginAdmin = new System.Windows.Forms.Button();
            this.btnCreateForum = new System.Windows.Forms.Button();
            this.txtForumName = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // btnLogin
            // 
            this.btnLogin.Location = new System.Drawing.Point(298, 126);
            this.btnLogin.Name = "btnLogin";
            this.btnLogin.Size = new System.Drawing.Size(88, 23);
            this.btnLogin.TabIndex = 1;
            this.btnLogin.Text = "Login";
            this.btnLogin.UseVisualStyleBackColor = true;
            // 
            // txtTestResult
            // 
            this.txtTestResult.Location = new System.Drawing.Point(12, 12);
            this.txtTestResult.Name = "txtTestResult";
            this.txtTestResult.Size = new System.Drawing.Size(280, 246);
            this.txtTestResult.TabIndex = 2;
            this.txtTestResult.Text = "";
            // 
            // btnGetForums
            // 
            this.btnGetForums.Location = new System.Drawing.Point(297, 68);
            this.btnGetForums.Name = "btnGetForums";
            this.btnGetForums.Size = new System.Drawing.Size(88, 23);
            this.btnGetForums.TabIndex = 3;
            this.btnGetForums.Text = "Get Forums";
            this.btnGetForums.UseVisualStyleBackColor = true;
            // 
            // btnEnterForum
            // 
            this.btnEnterForum.Location = new System.Drawing.Point(298, 97);
            this.btnEnterForum.Name = "btnEnterForum";
            this.btnEnterForum.Size = new System.Drawing.Size(88, 23);
            this.btnEnterForum.TabIndex = 4;
            this.btnEnterForum.Text = "Enter Forum";
            this.btnEnterForum.UseVisualStyleBackColor = true;
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
            this.pictureBox1.Location = new System.Drawing.Point(392, 10);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(117, 159);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pictureBox1.TabIndex = 5;
            this.pictureBox1.TabStop = false;
            // 
            // btnAdmin
            // 
            this.btnAdmin.Location = new System.Drawing.Point(298, 10);
            this.btnAdmin.Name = "btnAdmin";
            this.btnAdmin.Size = new System.Drawing.Size(87, 23);
            this.btnAdmin.TabIndex = 6;
            this.btnAdmin.Text = "Init";
            this.btnAdmin.UseVisualStyleBackColor = true;
            // 
            // btnLoginAdmin
            // 
            this.btnLoginAdmin.Location = new System.Drawing.Point(298, 39);
            this.btnLoginAdmin.Name = "btnLoginAdmin";
            this.btnLoginAdmin.Size = new System.Drawing.Size(87, 23);
            this.btnLoginAdmin.TabIndex = 7;
            this.btnLoginAdmin.Text = "Login Admin";
            this.btnLoginAdmin.UseVisualStyleBackColor = true;
            // 
            // btnCreateForum
            // 
            this.btnCreateForum.Location = new System.Drawing.Point(12, 317);
            this.btnCreateForum.Name = "btnCreateForum";
            this.btnCreateForum.Size = new System.Drawing.Size(101, 23);
            this.btnCreateForum.TabIndex = 8;
            this.btnCreateForum.Text = "Create Forum";
            this.btnCreateForum.UseVisualStyleBackColor = true;
            // 
            // txtForumName
            // 
            this.txtForumName.Location = new System.Drawing.Point(126, 319);
            this.txtForumName.Name = "txtForumName";
            this.txtForumName.Size = new System.Drawing.Size(100, 20);
            this.txtForumName.TabIndex = 9;
            // 
            // FormAcceptance
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(521, 352);
            this.Controls.Add(this.txtForumName);
            this.Controls.Add(this.btnCreateForum);
            this.Controls.Add(this.btnLoginAdmin);
            this.Controls.Add(this.btnAdmin);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.btnEnterForum);
            this.Controls.Add(this.btnGetForums);
            this.Controls.Add(this.txtTestResult);
            this.Controls.Add(this.btnLogin);
            this.Name = "FormAcceptance";
            this.Text = "GUI";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Button btnLogin;
        private System.Windows.Forms.RichTextBox txtTestResult;
        private System.Windows.Forms.Button btnGetForums;
        private System.Windows.Forms.Button btnEnterForum;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Button btnAdmin;
        private System.Windows.Forms.Button btnLoginAdmin;
        private System.Windows.Forms.Button btnCreateForum;
        private System.Windows.Forms.TextBox txtForumName;
    }
}