using System;
using System.Collections.Generic;
using StompTest;
using StompTest.Forms;

namespace CommandLineClient
{
    class Program
    {
        private static Dictionary<string, Action<string[]>> methods;
        private static ServerFacade server;

        static Program()
        {
            server = new ServerFacade();

            // here we add methods...
            methods = new Dictionary<string, Action<string[]>>();

            methods["LogInGuest"] = LogInGuest;
            methods["GetForums"] = GetForums;
            methods["LogInMember"] = LogInMember;
            // continue here...

            
        }

        static void Main(string[] args)
        {
            server.Connect();

            string methodName = args[0];

            methods[methodName].Invoke(args);

            server.Disconnect();
        }

        private static void LogInMember(string[] args)
        {
            server.User.LoginMember(args[1], args[2], args[3]);
        }

        private static void LogInGuest(string[] args)
        {
            string sid = server.LogInGuest(args[1]);
            Console.Out.WriteLine(sid);
        }

        private static void GetForums(string[] args)
        {
            string[] forums = server.GetForums();
            foreach (var forum in forums)
            {
                Console.Out.WriteLine(forum);
            }
        }
    }
}
