namespace StompTest.Forms
{
    partial class Welcome
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
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.lstForums = new System.Windows.Forms.ListBox();
            this.btnGoToForum = new System.Windows.Forms.Button();
            this.lblAdmin = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(83, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Welcome Guest";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 53);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(110, 13);
            this.label2.TabIndex = 1;
            this.label2.Text = "Please Choose Forum";
            // 
            // lstForums
            // 
            this.lstForums.FormattingEnabled = true;
            this.lstForums.Location = new System.Drawing.Point(15, 84);
            this.lstForums.Name = "lstForums";
            this.lstForums.Size = new System.Drawing.Size(241, 251);
            this.lstForums.TabIndex = 3;
            // 
            // btnGoToForum
            // 
            this.btnGoToForum.Location = new System.Drawing.Point(15, 342);
            this.btnGoToForum.Name = "btnGoToForum";
            this.btnGoToForum.Size = new System.Drawing.Size(107, 23);
            this.btnGoToForum.TabIndex = 4;
            this.btnGoToForum.Text = "Go";
            this.btnGoToForum.UseVisualStyleBackColor = true;
            this.btnGoToForum.Click += new System.EventHandler(this.btnGoToForum_Click);
            // 
            // lblAdmin
            // 
            this.lblAdmin.AutoSize = true;
            this.lblAdmin.Location = new System.Drawing.Point(220, 9);
            this.lblAdmin.Name = "lblAdmin";
            this.lblAdmin.Size = new System.Drawing.Size(36, 13);
            this.lblAdmin.TabIndex = 5;
            this.lblAdmin.Text = "Admin";
            this.lblAdmin.Click += new System.EventHandler(this.lblAdmin_Click);
            // 
            // Welcome
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(268, 472);
            this.Controls.Add(this.lblAdmin);
            this.Controls.Add(this.btnGoToForum);
            this.Controls.Add(this.lstForums);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "Welcome";
            this.Text = "Welcome";
            this.Load += new System.EventHandler(this.Welcome_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ListBox lstForums;
        private System.Windows.Forms.Button btnGoToForum;
        private System.Windows.Forms.Label lblAdmin;
    }
}