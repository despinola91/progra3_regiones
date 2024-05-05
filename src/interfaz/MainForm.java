package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import negocio.Mapa;
import negocio.Provincia;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class MainForm 
{

	public JFrame frmProvinciasArgentinas;
	private JPanel panelMapa;
	private JPanel panelControles;
	private JMapViewer _mapa;
	private ArrayList<Coordinate> _lasCoordenadas;
	private JButton btnEliminar;
	private MapPolygonImpl _poligono;
	private JButton btnDibujarPolgono ;
	private boolean ventanaCargaSimilitudesAbierta = false;
	private JComboBox comboBox_Provincia2;
	private JComboBox comboBox_Provincia1;
	
	private JComboBox<String> comboBoxProvincias;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmProvinciasArgentinas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmProvinciasArgentinas = new JFrame();
		frmProvinciasArgentinas.setResizable(false);
		frmProvinciasArgentinas.setTitle("Provincias Argentinas");
		frmProvinciasArgentinas.setBounds(200, 25, 780, 575);
		frmProvinciasArgentinas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProvinciasArgentinas.getContentPane().setLayout(null);
		
		panelMapa = new JPanel();
		panelMapa.setBounds(8, 10, 437, 512);
		frmProvinciasArgentinas.getContentPane().add(panelMapa);
		
		panelControles = new JPanel();
		panelControles.setToolTipText("");
		panelControles.setBounds(457, 11, 297, 446);
		frmProvinciasArgentinas.getContentPane().add(panelControles);		
		panelControles.setLayout(null);
		
		_mapa = new JMapViewer();
		_mapa.setCenter(new Point(1075, 700));
		_mapa.setDisplayPosition(new Coordinate(-41,-63), 4);
		_mapa.setPreferredSize(new Dimension(437, 512));
				
		panelMapa.add(_mapa);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("fondoBandera.png"));
		lblNewLabel_3.setBounds(0, 0, 766, 528);
		frmProvinciasArgentinas.getContentPane().add(lblNewLabel_3);

		detectarCoordenadas();
		dibujarPoligono();
		eliminarPoligono();		
		cargaSimilaridades();
		dividirRegiones();
	}
	
	private void detectarCoordenadas() 
	{
		_lasCoordenadas = new ArrayList<Coordinate>();
				
		_mapa.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				Coordinate markeradd = (Coordinate)
				_mapa.getPosition(e.getPoint());
				_lasCoordenadas.add(markeradd);
				String nombre = JOptionPane.showInputDialog("Nombre provincia: ");
				_mapa.addMapMarker(new MapMarkerDot(nombre, markeradd));//coloca en el mapa el nombre de la prov. tipeada por usuario
				//Agrega nombre a la lista
				if (nombre != null && !nombre.isEmpty()) {
					comboBox_Provincia1.addItem(nombre); // Agregar provincia a la lista desplegable
					
				}
			}}
		});
	}

	private void dibujarPoligono()  //va dibujando el grafo con las provincias agregadas en el mapa
	{
		btnDibujarPolgono = new JButton("Dibujar Grafo");
		btnDibujarPolgono.setBounds(61, 26, 195, 23);
		btnDibujarPolgono.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				_poligono = new MapPolygonImpl(_lasCoordenadas);
				_mapa.addMapPolygon(_poligono);
				
			}
		});
	}

	private void eliminarPoligono() 
	{
		btnEliminar = new JButton("Eliminar Grafo");
		btnEliminar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				 _mapa.removeMapPolygon(_poligono);
			}
		});
		btnEliminar.setBounds(61, 77, 195, 23);
		panelControles.add(btnEliminar);
		panelControles.add(btnDibujarPolgono);		
	}
	
	
	private void dividirRegiones() {
	    JButton btnDividirRegiones = new JButton("Dividir Regiones");
	    btnDividirRegiones.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String input = JOptionPane.showInputDialog("En cuantas Regiones desea dividir?");
	            try {
	                int numRegiones = Integer.parseInt(input);
	                if (numRegiones > 0) {
	                    System.out.println("Numero de regiones: " + numRegiones); //De prueba, despues lo eliminamos
	                } else {
	                    JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
		btnDividirRegiones.setBounds(61, 348, 195, 23);
	    panelControles.add(btnDividirRegiones);
	}
	
	private void cargaSimilaridades() {
	    
	    comboBox_Provincia1 = new JComboBox();
	    comboBox_Provincia1.setToolTipText("Provincia");
	    comboBox_Provincia1.setBounds(25, 151, 77, 22);
	    panelControles.add(comboBox_Provincia1);
	    
	    comboBox_Provincia2 = new JComboBox();
	    comboBox_Provincia2.setBounds(112, 151, 77, 22);
	    panelControles.add(comboBox_Provincia2);
	    
	    JTextPane textPane = new JTextPane();
	    textPane.setBounds(209, 151, 62, 22);
	    panelControles.add(textPane);
	    
	    JLabel lblNewLabel = new JLabel("Provincia");
	    lblNewLabel.setBounds(25, 127, 77, 23);
	    panelControles.add(lblNewLabel);
	    
	    JLabel lblNewLabel_1 = new JLabel("Provincia");
	    lblNewLabel_1.setBounds(112, 127, 77, 23);
	    panelControles.add(lblNewLabel_1);
	    
	    JLabel lblNewLabel_2 = new JLabel("Similaridad");
	    lblNewLabel_2.setBounds(209, 127, 77, 23);
	    panelControles.add(lblNewLabel_2);
	    
	    JButton btnCargarSimilitud = new JButton("Cargar Similitud");
	    btnCargarSimilitud.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	}
	    });
	    btnCargarSimilitud.setBounds(94, 196, 136, 23);
	    panelControles.add(btnCargarSimilitud);
	}
}