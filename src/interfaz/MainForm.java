package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import negocio.Kruskal;
import negocio.Mapa;
import negocio.Prim;
import negocio.Regiones;

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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class MainForm 
{

	public JFrame frmProvinciasArgentinas;
	private JPanel panelMapa;
	private JPanel panelControlRelaciones;
	private JMapViewer _mapa;
	private ArrayList<Coordinate> _lasCoordenadas;
	private JButton btnEliminar;
	private MapPolygonImpl _poligono;
	private JButton btnDibujarPolgono ;
	private JComboBox comboBox_Provincia2;
	private JComboBox comboBox_Provincia1;
	private JComboBox comboBox_Algoritmo;
	
	private JComboBox<String> comboBoxProvincias;
	private JPanel panelControlRegiones;
	private JLabel lblBandera;
	
	private Mapa mapa;

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
		
		panelControlRelaciones = new JPanel();
		panelControlRelaciones.setToolTipText("");
		panelControlRelaciones.setBounds(457, 11, 297, 251);
		frmProvinciasArgentinas.getContentPane().add(panelControlRelaciones);		
		panelControlRelaciones.setLayout(null);
		
		_mapa = new JMapViewer();
		_mapa.setCenter(new Point(1075, 700));
		_mapa.setDisplayPosition(new Coordinate(-41,-63), 4);
		_mapa.setPreferredSize(new Dimension(437, 512));
				
		panelMapa.add(_mapa);
		
		panelControlRegiones = new JPanel();
		panelControlRegiones.setBounds(455, 273, 299, 249);
		frmProvinciasArgentinas.getContentPane().add(panelControlRegiones);
		panelControlRegiones.setLayout(null);

		
		lblBandera = new JLabel("");
		lblBandera.setIcon(new ImageIcon("fondoBandera.png"));
		lblBandera.setBounds(0, 0, 766, 528);
		frmProvinciasArgentinas.getContentPane().add(lblBandera);
		
		mapa = new Mapa();

		detectarCoordenadas();
		dibujarPoligono();
		eliminarPoligono();		
		cargarRelaciones();
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
				String nombre = JOptionPane.showInputDialog("Nombre provincia: ");
				//Agrega nombre a la lista
				if (nombre != null && !nombre.isEmpty()) {
					_lasCoordenadas.add(markeradd);
					_mapa.addMapMarker(new MapMarkerDot(nombre, markeradd));//coloca en el mapa el nombre de la prov. tipeada por usuario
					
					mapa.agregarProvincia(nombre, markeradd.getLat(), markeradd.getLon());

					comboBox_Provincia1.setModel(new DefaultComboBoxModel<>(mapa.obtenerProvincias().toArray(new String[0])));
					comboBox_Provincia2.setModel(new DefaultComboBoxModel<>(mapa.obtenerProvincias().toArray(new String[0])));

				}
			}}
		});
	}

	private void dibujarPoligono()  //va dibujando el grafo con las provincias agregadas en el mapa
	{
		btnDibujarPolgono = new JButton("Dibujar Grafo");
		btnDibujarPolgono.setBounds(18, 218, 123, 23);
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
		btnEliminar.setBounds(149, 218, 128, 23);
		panelControlRelaciones.add(btnEliminar);
		panelControlRelaciones.add(btnDibujarPolgono);		
	}
	
	
	private void dividirRegiones() {
		
	    JLabel lblTituloRegiones = new JLabel("Creacion de regiones");
	    lblTituloRegiones.setFont(new Font("Tahoma", Font.ITALIC, 16));
	    lblTituloRegiones.setBounds(25, 11, 208, 22);
	    panelControlRegiones.add(lblTituloRegiones);
	    
	    JLabel lblRegiones = new JLabel("Regiones");
	    lblRegiones.setBounds(25, 54, 66, 23);
	    panelControlRegiones.add(lblRegiones);
	    
	    JTextPane textCantidadRegiones = new JTextPane();
	    textCantidadRegiones.setBounds(133, 54, 62, 22);
	    panelControlRegiones.add(textCantidadRegiones);
	    
	    JLabel lblAlgoritmo = new JLabel("Algoritmo");
	    lblAlgoritmo.setBounds(25, 97, 86, 23);
	    panelControlRegiones.add(lblAlgoritmo);
	    
	    comboBox_Algoritmo = new JComboBox();
	    comboBox_Algoritmo.setToolTipText("");
	    comboBox_Algoritmo.setBounds(133, 97, 138, 22);
	    panelControlRegiones.add(comboBox_Algoritmo);
	    comboBox_Algoritmo.addItem("Prim");
	    comboBox_Algoritmo.addItem("Kruskal");
	    
	    
	    //Logica del Boton Crear Regiones
		JButton btnCrearRegiones = new JButton("Crear Regiones");
		btnCrearRegiones.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String input = textCantidadRegiones.getText();
		        try {
		            int numRegiones = Integer.parseInt(input);
		            if (numRegiones > 0) {
		                System.out.println("Numero de regiones: " + numRegiones);
		             // Obtener la opción seleccionada en el comboBox
		                String seleccion = (String) comboBox_Algoritmo.getSelectedItem();

		                // Verificar qué opción se ha seleccionado
		                if (seleccion.equals("Prim")) {
		                    Prim.crearAlgoritmoPrim();
		                } else if (seleccion.equals("Kruskal")) {
		                    Kruskal.crearAlgoritmoKruskal();
		                }

		                // Llamar al método para dividir regiones y pasar numRegiones como parámetro
		                Regiones.dividirRegiones(numRegiones);
		                
		            } else {
		                JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		btnCrearRegiones.setBounds(25, 200, 136, 23);
		panelControlRegiones.add(btnCrearRegiones);
	}
	
	private void cargarRelaciones() {
	    
	    comboBox_Provincia1 = new JComboBox();
	    comboBox_Provincia1.setToolTipText("Provincia");
	    comboBox_Provincia1.setBounds(133, 44, 138, 22);
	    panelControlRelaciones.add(comboBox_Provincia1);
	    
	    comboBox_Provincia2 = new JComboBox();
	    comboBox_Provincia2.setBounds(133, 77, 138, 22);
	    panelControlRelaciones.add(comboBox_Provincia2);
	    
	    JTextPane textSimilitud = new JTextPane();
	    textSimilitud.setBounds(133, 125, 62, 22);
	    panelControlRelaciones.add(textSimilitud);
	    
	    JLabel lblProvincia1 = new JLabel("Provincia 1");
	    lblProvincia1.setBounds(25, 44, 77, 23);
	    panelControlRelaciones.add(lblProvincia1);
	    
	    JLabel lblProvincia2 = new JLabel("Provincia 2");
	    lblProvincia2.setBounds(25, 78, 77, 23);
	    panelControlRelaciones.add(lblProvincia2);
	    
	    JLabel lblSimilitud = new JLabel("Similitud");
	    lblSimilitud.setBounds(25, 125, 77, 23);
	    panelControlRelaciones.add(lblSimilitud);
	    
	    JButton btnCrearRelacion = new JButton("Crear Relacion");
	    btnCrearRelacion.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String provincia1 = comboBox_Provincia1.getSelectedItem().toString();
	            String provincia2 = comboBox_Provincia2.getSelectedItem().toString();
	            String similitudText = textSimilitud.getText();
	            try {
	                int similitud = Integer.parseInt(similitudText);
	                if (similitud > 0) {
	                    if (!provincia1.equals(provincia2)) {
	                        mapa.agregarRelacion(provincia1, provincia2, similitud);
	                        JOptionPane.showMessageDialog(null, "La relacion ha sido cargada", "Relacion Cargada", JOptionPane.INFORMATION_MESSAGE);
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Las dos provincias seleccionadas son iguales, por favor seleccione provincias diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	    btnCrearRelacion.setBounds(25, 173, 136, 23);
	    panelControlRelaciones.add(btnCrearRelacion);
	    
	    JLabel lblTituloRelaciones = new JLabel("Creacion de relaciones");
	    lblTituloRelaciones.setFont(new Font("Tahoma", Font.ITALIC, 16));
	    lblTituloRelaciones.setBounds(25, 11, 208, 22);
	    panelControlRelaciones.add(lblTituloRelaciones);
	}
}