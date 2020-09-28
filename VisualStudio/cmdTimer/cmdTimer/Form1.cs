using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace cmdTimer
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();

        }

        /*powercfg -hibernate off  && %windir%\System32\rundll32.exe powrprof.dll,SetSuspendState 0,1,0 &&  ping -n 3 127.0.0.1  &&  powercfg -hibernate on*/
        void cmdProcess(string strCmdText)
        {
            System.Diagnostics.Process process = new System.Diagnostics.Process();
            process.StartInfo.WindowStyle = System.Diagnostics.ProcessWindowStyle.Hidden;
            process.StartInfo.CreateNoWindow = true;
            process.StartInfo.UseShellExecute = false;
            process.StartInfo.RedirectStandardOutput = true;
            process.StartInfo.FileName = "cmd.exe";
            process.StartInfo.Arguments = strCmdText;
            process.Start();
            string output = process.StandardOutput.ReadLine();
            if (output != null && output.Length > 0)
                MessageBox.Show(output);

            //process.WaitForExit();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string strCmdText;
            strCmdText = @"/k C:\Windows\System32\shutdown.exe -s -t " + (numericUpDown1.Value * 3600 + numericUpDown2.Value * 60 + numericUpDown3.Value).ToString();
            //System.Diagnostics.Process.Start("CMD.exe", strCmdText);
            cmdProcess(strCmdText);

        }
        private void button6_Click(object sender, EventArgs e)
        {
            string strCmdText;
            int x = ((Convert.ToInt32(numericUpDown1.Value) * 3600 + Convert.ToInt32(numericUpDown2.Value) * 60 + Convert.ToInt32(numericUpDown3.Value)) - (DateTime.Now.Hour * 3600 + DateTime.Now.Minute * 60 + DateTime.Now.Second) + 24 * 3600) % (24 * 3600);

            // strCmdText = "/K C:\\Windows\\System32\\schtasks -create -tn test -sc once -tr \"ping 8.8.8.8 - t\" -st 23:37 /f";
            strCmdText = @"/k C:\Windows\System32\shutdown.exe -s -t " + x.ToString();
            cmdProcess(strCmdText);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            // System.Diagnostics.ProcessStartInfo startInfo = new System.Diagnostics.ProcessStartInfo();
            //process.StartInfo = startInfo;
            cmdProcess(@"/C C:\Windows\System32\shutdown.exe -a");

        }

        private void button3_Click(object sender, EventArgs e)
        {

            string strCmdText;
            strCmdText = @"/k C:\Windows\System32\ping.exe 8.8.8.8 -t";
            System.Diagnostics.Process.Start("CMD.exe", strCmdText);
        }

        private void button4_Click(object sender, EventArgs e)
        {
            string strCmdText;
            strCmdText = @"/k C:\Windows\System32\ping.exe 104.160.142.3 -t";
            System.Diagnostics.Process.Start("CMD.exe", strCmdText);
        }

        private void button5_Click(object sender, EventArgs e)
        {

            string strCmdText;
            strCmdText = @"/k C:\Windows\System32\ping.exe " + textBox1.Text + " -t";
            System.Diagnostics.Process.Start("CMD.exe", strCmdText);
        }

        private void button7_Click(object sender, EventArgs e) //test
        {
            //string strCmdText;
            //  int x = (((int)numericUpDown1.Value + DateTime.Now.Hour) * 60 + (int)numericUpDown2.Value + DateTime.Now.Minute) % (24 * 60);
            //strCmdText = String.Format("{0:HHt}", DateTime.Now);

            MessageBox.Show(DateTime.Now.ToString("HH:mm:ss"));
        }

        private void button8_Click(object sender, EventArgs e)
        {
            // strCmdText = "/K C:\\Windows\\System32\\schtasks -create -tn standby -sc once -tr \" %windir%\\System32\\rundll32.exe powrprof.dll,SetSuspendState Standby \" -st " + (int)numericUpDown1.Value + ":" + (int)numericUpDown2.Value + " /f";
            // strCmdText = String.Format("/K C:\\Windows\\System32\\schtasks -create -tn standby -sc once -tr \" %windir%\\System32\\rundll32.exe powrprof.dll,SetSuspendState Standby \" -st " + (int)numericUpDown1.Value + ":" + (int)numericUpDown2.Value + " /f");

            string strCmdText;
            strCmdText = String.Format("/K C:\\Windows\\System32\\schtasks -create -tn Standby -sc once -tr \" %windir%\\System32\\rundll32.exe powrprof.dll,SetSuspendState Standby \" -st {0:00}:{1:00}  /f", (int)numericUpDown1.Value, (int)numericUpDown2.Value);

            cmdProcess(strCmdText);

        }

        private void button10_Click(object sender, EventArgs e)
        {
            string strCmdText;

            strCmdText = String.Format("/K C:\\Windows\\System32\\schtasks /Delete /tn Standby /f");

            cmdProcess(strCmdText);


        }

        private void button9_Click(object sender, EventArgs e)
        {

            string strCmdText;
            int x = (((int)numericUpDown1.Value + DateTime.Now.Hour) * 60 + (int)numericUpDown2.Value + DateTime.Now.Minute) % (24 * 60);
            strCmdText = String.Format("/K C:\\Windows\\System32\\schtasks -create -tn Standby -sc once -tr \" %windir%\\System32\\rundll32.exe powrprof.dll,SetSuspendState Standby \" -st {0:00}:{1:00}  /f", x / 60, x % 60);

            cmdProcess(strCmdText);

        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
    }
}
