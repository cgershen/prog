//Carreras, corredores en diferentes hilos
//por Gaspar Munguia
// basado en https://sites.google.com/site/javaejercicios/home/hilos-caballos
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JScrollPane;


public class hilos {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hilos window = new hilos();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public hilos() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(300, 200, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Apuesten para las carreras!");
		
		final JProgressBar progressBar_c1 = new JProgressBar();
		final JProgressBar progressBar_c2 = new JProgressBar();		
		final JProgressBar progressBar_c3 = new JProgressBar();
		final JProgressBar progressBar_c4 = new JProgressBar();

		final JLabel lblNewLabel_c1 = new JLabel("Corredor 1");		
		final JLabel lblNewLabel_c2 = new JLabel("Corredor 2");		
		final JLabel lblNewLabel_c3 = new JLabel("Corredor 3");
		final JLabel lblNewLabel_c4 = new JLabel("Corredor 4");

	        
		JButton btnEmpezar = new JButton("Empezar carrera");
			
		final JScrollPane scrollPane = new JScrollPane();
		

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_c3)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblNewLabel_c1)
									.addComponent(lblNewLabel_c2)
									.addComponent(lblNewLabel_c4)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnEmpezar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
								.addComponent(progressBar_c2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
								.addComponent(progressBar_c3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
								.addComponent(progressBar_c1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
								.addComponent(progressBar_c4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
								)
							.addGap(127))
					)))
		;
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(13)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar_c1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_c1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_c2)
						.addComponent(progressBar_c2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar_c3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_c3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar_c4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_c4))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEmpezar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)

		));
		groupLayout.linkSize(SwingConstants.VERTICAL, new Component[] {progressBar_c1, progressBar_c2, progressBar_c3,progressBar_c4});
		
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		frame.getContentPane().setLayout(groupLayout);
		
		
		btnEmpezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
				btnEmpezar.setEnabled(false);
				new hiloCarreras("Corredor 1", progressBar_c1, textArea, lblNewLabel_c1,btnEmpezar).start();
				new hiloCarreras("Corredor 2", progressBar_c2, textArea, lblNewLabel_c2,btnEmpezar).start();
				new hiloCarreras("Corredor 3", progressBar_c3, textArea, lblNewLabel_c3,btnEmpezar).start();
				new hiloCarreras("Corredor 4", progressBar_c4, textArea, lblNewLabel_c4,btnEmpezar).start();
			}
		});	
	}	
}




