using System;
using System.Drawing;
using System.Windows.Forms;
using System.Threading;
using System.Diagnostics;

namespace W7.Hilos
{
	public class Hilos : System.Windows.Forms.Form
	{
		private System.Windows.Forms.Button btnStartPgm;
        private System.Windows.Forms.Button btnStartPgmShort;
		private System.Windows.Forms.Button btnStopPgm;
		private System.Windows.Forms.Button btnShowProcesses;
		private System.Windows.Forms.ProgressBar progressBar1;
		private System.Windows.Forms.GroupBox groupBox1;
		private System.Windows.Forms.GroupBox groupBox2;
		private System.Windows.Forms.Button btnStartThread;
		private System.Windows.Forms.Button btnStopThread;
		private System.ComponentModel.Container components = null;
		private Thread timerThread;

		public Hilos()
		{
			InitializeComponent();
		}

		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
            this.btnStartPgm = new System.Windows.Forms.Button();
            this.btnStartPgmShort = new System.Windows.Forms.Button();
            this.btnStopPgm = new System.Windows.Forms.Button();
            this.btnShowProcesses = new System.Windows.Forms.Button();
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.btnStopThread = new System.Windows.Forms.Button();
            this.btnStartThread = new System.Windows.Forms.Button();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnStartPgm
            // 
            this.btnStartPgm.Location = new System.Drawing.Point(16, 19);
            this.btnStartPgm.Name = "btnStartPgm";
            this.btnStartPgm.Size = new System.Drawing.Size(144, 23);
            this.btnStartPgm.TabIndex = 0;
            this.btnStartPgm.Text = "Proceso bloc de notas";
            this.btnStartPgm.Click += new System.EventHandler(this.btnStartPgm_Click);
            // 
            // btnStartPgmShort
            // 
            this.btnStartPgmShort.Location = new System.Drawing.Point(162, 19);
            this.btnStartPgmShort.Name = "btnStartPgmShort";
            this.btnStartPgmShort.Size = new System.Drawing.Size(144, 23);
            this.btnStartPgmShort.TabIndex = 1;
            this.btnStartPgmShort.Text = "Bloc por 5 segundos";
            this.btnStartPgmShort.Click += new System.EventHandler(this.btnStartPgmShort_Click);
            // 
            // btnStopPgm
            // 
            this.btnStopPgm.Location = new System.Drawing.Point(16, 48);
            this.btnStopPgm.Name = "btnStopPgm";
            this.btnStopPgm.Size = new System.Drawing.Size(144, 23);
            this.btnStopPgm.TabIndex = 3;
            this.btnStopPgm.Text = "Salir del bloc";
            this.btnStopPgm.Click += new System.EventHandler(this.btnStopPgm_Click);
            // 
            // btnShowProcesses
            // 
            this.btnShowProcesses.Location = new System.Drawing.Point(16, 119);
            this.btnShowProcesses.Name = "btnShowProcesses";
            this.btnShowProcesses.Size = new System.Drawing.Size(144, 23);
            this.btnShowProcesses.TabIndex = 4;
            this.btnShowProcesses.Text = "Procesos de windows";
            this.btnShowProcesses.Click += new System.EventHandler(this.btnShowProcesses_Click);
            // 
            // progressBar1
            // 
            this.progressBar1.Location = new System.Drawing.Point(16, 24);
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(280, 24);
            this.progressBar1.TabIndex = 5;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.btnStopThread);
            this.groupBox1.Controls.Add(this.btnStartThread);
            this.groupBox1.Controls.Add(this.progressBar1);
            this.groupBox1.Location = new System.Drawing.Point(16, 192);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(312, 112);
            this.groupBox1.TabIndex = 6;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Hilos (dentro del proceso)";
            // 
            // btnStopThread
            // 
            this.btnStopThread.Location = new System.Drawing.Point(171, 72);
            this.btnStopThread.Name = "btnStopThread";
            this.btnStopThread.Size = new System.Drawing.Size(75, 23);
            this.btnStopThread.TabIndex = 7;
            this.btnStopThread.Text = "Detener";
            this.btnStopThread.Click += new System.EventHandler(this.btnStopThread_Click);
            // 
            // btnStartThread
            // 
            this.btnStartThread.Location = new System.Drawing.Point(67, 72);
            this.btnStartThread.Name = "btnStartThread";
            this.btnStartThread.Size = new System.Drawing.Size(75, 23);
            this.btnStartThread.TabIndex = 6;
            this.btnStartThread.Text = "Iniciar";
            this.btnStartThread.Click += new System.EventHandler(this.btnStartThread_Click);
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.btnStartPgmShort);
            this.groupBox2.Controls.Add(this.btnStartPgm);
            this.groupBox2.Controls.Add(this.btnStopPgm);
            this.groupBox2.Controls.Add(this.btnShowProcesses);
            this.groupBox2.Location = new System.Drawing.Point(16, 8);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(312, 160);
            this.groupBox2.TabIndex = 7;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Procesos";
            // 
            // ManagingProcesses
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(344, 325);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.groupBox2);
            this.Name = "ManagingProcesses";
            this.Text = "Procesos";
            this.groupBox1.ResumeLayout(false);
            this.groupBox2.ResumeLayout(false);
            this.ResumeLayout(false);

		}
		#endregion

	
		[STAThread]
		static void Main() 
		{
			Application.Run(new Hilos());
		}


		private void btnStartPgm_Click(object sender, System.EventArgs e)
		{
			Process myProcess = new Process();
			myProcess.StartInfo.FileName = "Notepad";
			myProcess.StartInfo.WindowStyle = ProcessWindowStyle.Maximized;
			myProcess.Start();
		}

		private void btnStartPgmShort_Click(object sender, System.EventArgs e)
		{
			Process process = new Process();
			process.StartInfo.FileName = "Notepad";
			process.Start();
			        
			process.WaitForInputIdle();
			        
			Thread.Sleep(5000);
			if(!process.CloseMainWindow())
			{
				process.Kill();
			}
		}
 

		private void btnStopPgm_Click(object sender, System.EventArgs e)
		{
			Process[] myProcesses;

			myProcesses = Process.GetProcessesByName("Notepad");
			foreach(Process myProcess in myProcesses)
			{
				if (myProcess.Responding) 
				{
					myProcess.CloseMainWindow();
				}
				else 
				{
					myProcess.Kill();
				}
			}
		}

		private void btnShowProcesses_Click(object sender, System.EventArgs e)
		{
			Process[] myProcesses = Process.GetProcesses();
			String msg = "";
			foreach(Process myProcess in myProcesses)
			{				
				msg += myProcess.ProcessName + "\n";
			}
			MessageBox.Show(msg, "Procesos en Ejecución",
				MessageBoxButtons.OK, MessageBoxIcon.Information);
		}

		private void btnStartThread_Click(object sender, System.EventArgs e)
		{
			StopThread();
			timerThread = new Thread(new ThreadStart(ThreadProc));
			timerThread.IsBackground = true;
			timerThread.Start();
		}

		private void btnStopThread_Click(object sender, System.EventArgs e)
		{
			StopThread();
		}

	
		public void ThreadProc() 
		{
			try 
			{
				MethodInvoker mi = new MethodInvoker(this.UpdateProgress);
				while (true) 
				{
		
					this.BeginInvoke(mi);
					Thread.Sleep(500) ;
				}
			}
	
			catch (ThreadInterruptedException) 
			{
				 
			}
			catch (Exception) 
			{
			}
		}

		
		private void UpdateProgress() 
		{

			if (progressBar1.Value == progressBar1.Maximum) 
			{
				progressBar1.Value = progressBar1.Minimum ;
			}
			progressBar1.PerformStep() ;
		}

		
		private void StopThread()
		{
			if (timerThread != null)
			{
				timerThread.Interrupt();
				timerThread = null;
			}
		}
	}
}
