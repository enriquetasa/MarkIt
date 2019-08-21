namespace MarkItDesktop
{
    partial class pathBtn
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
            this.selectPathBtn = new System.Windows.Forms.Button();
            this.assignmentLbl = new System.Windows.Forms.Label();
            this.assignmentCB = new System.Windows.Forms.ComboBox();
            this.downloadLbl = new System.Windows.Forms.Label();
            this.downloadBtn = new System.Windows.Forms.Button();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.XMLocationLbl = new System.Windows.Forms.Label();
            this.uploadBtn = new System.Windows.Forms.Button();
            this.findXMLBtn = new System.Windows.Forms.Button();
            this.tabControl1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.SuspendLayout();
            // 
            // selectPathBtn
            // 
            this.selectPathBtn.Location = new System.Drawing.Point(6, 136);
            this.selectPathBtn.Name = "selectPathBtn";
            this.selectPathBtn.Size = new System.Drawing.Size(99, 33);
            this.selectPathBtn.TabIndex = 0;
            this.selectPathBtn.Text = "Select Path";
            this.selectPathBtn.UseVisualStyleBackColor = true;
            this.selectPathBtn.Click += new System.EventHandler(this.selectPathBtn_Click);
            // 
            // assignmentLbl
            // 
            this.assignmentLbl.AutoSize = true;
            this.assignmentLbl.Location = new System.Drawing.Point(2, 12);
            this.assignmentLbl.Name = "assignmentLbl";
            this.assignmentLbl.Size = new System.Drawing.Size(93, 20);
            this.assignmentLbl.TabIndex = 1;
            this.assignmentLbl.Text = "Assignment";
            // 
            // assignmentCB
            // 
            this.assignmentCB.FormattingEnabled = true;
            this.assignmentCB.Location = new System.Drawing.Point(6, 35);
            this.assignmentCB.Name = "assignmentCB";
            this.assignmentCB.Size = new System.Drawing.Size(226, 28);
            this.assignmentCB.TabIndex = 2;
            // 
            // downloadLbl
            // 
            this.downloadLbl.AutoSize = true;
            this.downloadLbl.Location = new System.Drawing.Point(2, 81);
            this.downloadLbl.Name = "downloadLbl";
            this.downloadLbl.Size = new System.Drawing.Size(147, 20);
            this.downloadLbl.TabIndex = 3;
            this.downloadLbl.Text = "Download location: ";
            // 
            // downloadBtn
            // 
            this.downloadBtn.Location = new System.Drawing.Point(123, 136);
            this.downloadBtn.Name = "downloadBtn";
            this.downloadBtn.Size = new System.Drawing.Size(99, 33);
            this.downloadBtn.TabIndex = 4;
            this.downloadBtn.Text = "Download";
            this.downloadBtn.UseVisualStyleBackColor = true;
            this.downloadBtn.Click += new System.EventHandler(this.downloadBtn_Click_1);
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Location = new System.Drawing.Point(12, 12);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(354, 230);
            this.tabControl1.TabIndex = 5;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.downloadLbl);
            this.tabPage1.Controls.Add(this.downloadBtn);
            this.tabPage1.Controls.Add(this.selectPathBtn);
            this.tabPage1.Controls.Add(this.assignmentLbl);
            this.tabPage1.Controls.Add(this.assignmentCB);
            this.tabPage1.Location = new System.Drawing.Point(4, 29);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(311, 180);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "Download Grades";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.XMLocationLbl);
            this.tabPage2.Controls.Add(this.uploadBtn);
            this.tabPage2.Controls.Add(this.findXMLBtn);
            this.tabPage2.Location = new System.Drawing.Point(4, 29);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(346, 197);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Upload Mark Scheme";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // XMLocationLbl
            // 
            this.XMLocationLbl.AutoSize = true;
            this.XMLocationLbl.Location = new System.Drawing.Point(6, 16);
            this.XMLocationLbl.Name = "XMLocationLbl";
            this.XMLocationLbl.Size = new System.Drawing.Size(105, 20);
            this.XMLocationLbl.TabIndex = 6;
            this.XMLocationLbl.Text = "XML location:";
            // 
            // uploadBtn
            // 
            this.uploadBtn.Location = new System.Drawing.Point(115, 71);
            this.uploadBtn.Name = "uploadBtn";
            this.uploadBtn.Size = new System.Drawing.Size(99, 33);
            this.uploadBtn.TabIndex = 7;
            this.uploadBtn.Text = "Upload";
            this.uploadBtn.UseVisualStyleBackColor = true;
            this.uploadBtn.Click += new System.EventHandler(this.uploadBtn_Click);
            // 
            // findXMLBtn
            // 
            this.findXMLBtn.Location = new System.Drawing.Point(10, 71);
            this.findXMLBtn.Name = "findXMLBtn";
            this.findXMLBtn.Size = new System.Drawing.Size(99, 33);
            this.findXMLBtn.TabIndex = 5;
            this.findXMLBtn.Text = "Find XML";
            this.findXMLBtn.UseVisualStyleBackColor = true;
            this.findXMLBtn.Click += new System.EventHandler(this.findXMLBtn_Click);
            // 
            // pathBtn
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(378, 254);
            this.Controls.Add(this.tabControl1);
            this.Name = "pathBtn";
            this.Text = "Mark It Desktop";
            this.tabControl1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.tabPage1.PerformLayout();
            this.tabPage2.ResumeLayout(false);
            this.tabPage2.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button selectPathBtn;
        private System.Windows.Forms.Label assignmentLbl;
        private System.Windows.Forms.ComboBox assignmentCB;
        private System.Windows.Forms.Label downloadLbl;
        private System.Windows.Forms.Button downloadBtn;
        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.Label XMLocationLbl;
        private System.Windows.Forms.Button uploadBtn;
        private System.Windows.Forms.Button findXMLBtn;
    }
}

