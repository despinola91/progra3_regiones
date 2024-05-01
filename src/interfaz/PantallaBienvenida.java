package interfaz;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class PantallaBienvenida {

	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaBienvenida window = new PantallaBienvenida();
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
	public PantallaBienvenida() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Mapa de Argentina"); // Titulo de la ventana
		frame.getContentPane().setBackground(new Color(210, 180, 140));
		frame.setBounds(300, 20, 628, 399);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton botonPlay = new JButton("Ingresar");
		botonPlay.setForeground(new Color(244, 164, 96));
		botonPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPantallaMapa();
			}
		});
		botonPlay.setFont(new Font("Segoe UI Black", Font.PLAIN, 40));
		botonPlay.setBounds(139, 200, 301, 65);
		frame.getContentPane().add(botonPlay);

		botonPlay.setBackground(new Color(192, 224, 255));
		// llamar a revalidate() y repaint() sino no cambia el color de fondo...
		botonPlay.revalidate();
		botonPlay.repaint();
		
		JLabel lblNewLabel = new JLabel("Provincias Argentinas");
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 40));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(90, 50, 464, 65);
		frame.getContentPane().add(lblNewLabel);
		
		// agregamos la figura de fondo
		JLabel lblFondo = new JLabel("");
		lblFondo.setForeground(SystemColor.controlHighlight);
		lblFondo.setIcon(new ImageIcon("fondoBandera.png"));
		lblFondo.setBounds(0, -100, 907, 480);
		frame.getContentPane().add(lblFondo);

	}

	protected void mostrarPantallaMapa() { // Crea la nueva ventana, la hace visible y cierra la primera
		MainForm frame2 = new MainForm();
		frame2.frmProvinciasArgentinas.setVisible(true);
		getFrame().dispose();
	}

	/*
	 * Necesario tener el jFrame de pantallaInical para poder volver a esta pantalla
	 * cuando se cierre la PantallaJuego, ya que se borran todas las referencias
	 */
	public JFrame getFrame() {
		return frame;
	}
}
