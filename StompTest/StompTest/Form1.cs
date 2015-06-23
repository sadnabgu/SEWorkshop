using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using StompTest;

namespace St1ompTest
{
    public partial class Form1 : Form
    {
        StompMessage toSend;
        StompClient client;
        Thread listenThread;
        private bool connected;


        public Form1()
        {
            InitializeComponent();
            client = new StompClient("127.0.0.1", 12345);
            client.OnReceived += Client_OnReceived;
            toSend = new StompMessage();
            connected = false;
        }

        private void Client_OnReceived(object sender, StompMessage msg)
        {
            txtInput.Invoke(new Action(() => txtInput.Text += "\n" + msg));
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void btnPreview_Click(object sender, EventArgs e)
        {
            txtPreview.Text = toSend.ToString();
        }

        private void txtType_TextChanged(object sender, EventArgs e)
        {
            toSend.Type = txtType.Text;
            txtPreview.Text = toSend.ToString();
        }

        private void txtContent_TextChanged(object sender, EventArgs e)
        {
            toSend.Content = txtContent.Text;
            txtPreview.Text = toSend.ToString();
        }

        private void btnAdd_Click(object sender, EventArgs e)
        {
            toSend.Headers.Add(txtName.Text, txtValue.Text);
            txtPreview.Text = toSend.ToString();
        }

        private void btnConnect_Click(object sender, EventArgs e)
        {
            client.Connect();
        }

        private void btnSend_Click(object sender, EventArgs e)
        {
            client.Send(toSend);
        }

        private void btnAcceptance_Click(object sender, EventArgs e)
        {
            FormAcceptance acceptance = new FormAcceptance();
            acceptance.Show();

            
        }

        private void txtPreview_TextChanged(object sender, EventArgs e)
        {

        }

        private void txtName_TextChanged(object sender, EventArgs e)
        {

        }

        private void txtInput_TextChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            toSend = new StompMessage();
        }
    }
}
