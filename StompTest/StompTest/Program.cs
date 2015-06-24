using System;
using System.Windows.Forms;
using StompTest.Forms;

namespace StompTest
{
    static class Program
    {
        public static readonly ServerFacade Server = new ServerFacade("10.0.0.1", 12345);
        public static string SID = string.Empty;

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Server.Connect();
            
            if (Server.IsSystemInitialized())
            {
                Application.Run(new Welcome());
            }
            else
            {
                Application.Run(new InitSystem());
            }
            
        }
    }
}
