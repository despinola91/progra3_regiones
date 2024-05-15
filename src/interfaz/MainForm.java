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
import negocio.Relacion;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;



public class MainForm 
{
	public JFrame frmProvinciasArgentinas;

	private JPanel panelMapa;
	private JPanel panelControlRelaciones;
	private JPanel panelControlRegiones;
	private JPanel panelMuestraRelaciones;

	private JComboBox comboBox_Provincia2;
	private JComboBox comboBox_Provincia1;
	private JComboBox comboBox_Algoritmo;
	private JButton btnReset;
	
	private JTextPane textPaneMuestraRelaciones;
	private JTextPane textSimilitud;
	private JTextPane textCantidadRegiones;

	private JMapViewer _mapa;
	private JLabel lblBandera;
	
	private Mapa mapa;

	private JButton btnCrearRelacion;
	private JButton btnEliminarRelacion;

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
		frmProvinciasArgentinas.setBounds(200, 25, 1016, 575);
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
		
		panelMuestraRelaciones = new JPanel();
	    panelMuestraRelaciones.setBounds(764, 10, 238, 470);
	    frmProvinciasArgentinas.getContentPane().add(panelMuestraRelaciones);
	    panelMuestraRelaciones.setLayout(null);
	    panelMuestraRelaciones.setOpaque(false);

		btnReset = new JButton("Reiniciar Mapa");
		btnReset.setBounds(786, 499, 190, 23);
		frmProvinciasArgentinas.getContentPane().add(btnReset);
		
		lblBandera = new JLabel("");
		lblBandera.setIcon(new ImageIcon("fondoBandera.png"));
		lblBandera.setBounds(8, 0, 1000, 536);
		frmProvinciasArgentinas.getContentPane().add(lblBandera);
		
		mapa = new Mapa();

		detectarCoordenadas();	
		cargarRelaciones();
		dividirRegiones();
		mostrarRelaciones(false);
		reset();
	}
	
	private void detectarCoordenadas() 
	{	
		_mapa.addMouseListener(new MouseAdapter() 
		{
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) 
			{
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				Coordinate coordenadas = (Coordinate)_mapa.getPosition(e.getPoint());
				String nombre = JOptionPane.showInputDialog("Nombre provincia: ");

				if (nombre != null && !nombre.isEmpty()) {
					_mapa.addMapMarker(new MapMarkerDot(nombre, coordenadas));
					mapa.agregarProvincia(nombre, coordenadas);
					
					cargarDesplegablesProvincias();
				}
			}}
		});
	}
	
	private void dibujarArista(Coordinate coordenadaProv1, Coordinate coordenadaProv2) {
	    ArrayList<Coordinate> listaCoordenadas = new ArrayList<>();
	    
		//Para dibujar una arista entre dos vertices, el objeto MapPolygonImpl requiere unir A con B y luego A nuevamente.
	    listaCoordenadas.add(coordenadaProv1);
	    listaCoordenadas.add(coordenadaProv2);
	    listaCoordenadas.add(coordenadaProv1);
	    
		MapPolygonImpl relacion = new MapPolygonImpl(listaCoordenadas);
	    _mapa.addMapPolygon(relacion);
	}
	
	private void dibujarAristaRegiones(Coordinate coordenadaProv1, Coordinate coordenadaProv2, Color color) {
	    ArrayList<Coordinate> listaCoordenadas = new ArrayList<>();
	    
	    listaCoordenadas.add(coordenadaProv1);
	    listaCoordenadas.add(coordenadaProv2);
	    listaCoordenadas.add(coordenadaProv1);
	    
	    MapPolygonImpl relacion = new MapPolygonImpl(listaCoordenadas);
	    relacion.setColor(color);
	    _mapa.addMapPolygon(relacion);
	}
	
	private void dividirRegiones() {
		
	    JLabel lblTituloRegiones = new JLabel("Creacion de regiones");
	    lblTituloRegiones.setFont(new Font("Tahoma", Font.ITALIC, 16));
	    lblTituloRegiones.setBounds(25, 11, 208, 22);
	    panelControlRegiones.add(lblTituloRegiones);
	    
	    JLabel lblRegiones = new JLabel("Regiones");
	    lblRegiones.setBounds(25, 54, 66, 23);
	    panelControlRegiones.add(lblRegiones);
	    
	    textCantidadRegiones = new JTextPane();
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
	    
	    
		JButton btnCrearRegiones = new JButton("Crear Regiones");
		btnCrearRegiones.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String input = textCantidadRegiones.getText();
		        try {
		            int numRegiones = Integer.parseInt(input);
		            if (numRegiones > 0) {
		                System.out.println("Numero de regiones: " + numRegiones);
		                String algoritmo = (String) comboBox_Algoritmo.getSelectedItem();
		                
		                if (mapa.esMapaConexo(mapa.obtenerMatrizRelacion())) {
		                	mapa.generarRegiones(numRegiones, algoritmo);
		                	dibujarRegiones(mapa.obtenerMatrizRegiones());
		                	mostrarRelaciones(true);

							btnCrearRelacion.setEnabled(false);
							btnEliminarRelacion.setEnabled(false);
		                }else {
							JOptionPane.showMessageDialog(null, "Todas las provincias deben tener al menos una similitud cargada (Grafo inconexo!)", "Error", JOptionPane.ERROR_MESSAGE);
						}
		                
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
	    comboBox_Provincia2.setBounds(133, 80, 138, 22);
	    panelControlRelaciones.add(comboBox_Provincia2);
	    
	    textSimilitud = new JTextPane();
	    textSimilitud.setBounds(133, 130, 62, 22);
	    panelControlRelaciones.add(textSimilitud);
	    
	    JLabel lblProvincia1 = new JLabel("Provincia 1");
	    lblProvincia1.setBounds(25, 44, 77, 23);
	    panelControlRelaciones.add(lblProvincia1);
	    
	    JLabel lblProvincia2 = new JLabel("Provincia 2");
	    lblProvincia2.setBounds(25, 80, 77, 23);
	    panelControlRelaciones.add(lblProvincia2);
	    
	    JLabel lblSimilitud = new JLabel("Similitud");
	    lblSimilitud.setBounds(25, 130, 77, 23);
	    panelControlRelaciones.add(lblSimilitud);
	    
	    btnCrearRelacion = new JButton("Crear Relacion");
		btnCrearRelacion.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String nombreProvincia1 = comboBox_Provincia1.getSelectedItem().toString();	            
	            String nombreProvincia2 = comboBox_Provincia2.getSelectedItem().toString();
				
	            String similitudText = textSimilitud.getText();
	            try {
	                int similitud = Integer.parseInt(similitudText);
	                if (similitud > 0) {
	                    if (!nombreProvincia1.equals(nombreProvincia2)) {
	                        mapa.agregarRelacion(nombreProvincia1, nombreProvincia2, similitud);
							dibujarMapa(mapa.obtenerMatrizRelacion());
							mostrarRelaciones(false);
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Las dos provincias seleccionadas son iguales, por favor seleccione provincias diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "Debe ingresar un numero entero", "Error", JOptionPane.ERROR_MESSAGE);
	            }
				catch (IllegalArgumentException ex) {
	                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	    btnCrearRelacion.setBounds(9, 185, 134, 23);
	    panelControlRelaciones.add(btnCrearRelacion);
	    
	    btnEliminarRelacion = new JButton("Eliminar Relacion");
		btnEliminarRelacion.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String nombreProvincia1 = comboBox_Provincia1.getSelectedItem().toString();            
	            String nombreProvincia2 = comboBox_Provincia2.getSelectedItem().toString();
	            
	            mapa.eliminarRelacion(nombreProvincia1, nombreProvincia2);
				dibujarMapa(mapa.obtenerMatrizRelacion());
				mostrarRelaciones(false);
	        }
	    });
	    btnEliminarRelacion.setBounds(151, 185, 138, 23);
	    panelControlRelaciones.add(btnEliminarRelacion);
	    
	    JLabel lblTituloRelaciones = new JLabel("Creacion de relaciones");
	    lblTituloRelaciones.setFont(new Font("Tahoma", Font.ITALIC, 16));
	    lblTituloRelaciones.setBounds(25, 11, 208, 22);
	    panelControlRelaciones.add(lblTituloRelaciones);
	}

	private void dibujarMapa(int[][] matrizDeRelacion) {

		_mapa.removeAllMapPolygons();
		for (int i = 0; i < matrizDeRelacion.length; i++) {
			for (int j = 0; j < matrizDeRelacion.length; j++) {  
				if (matrizDeRelacion[i][j] > 0) {
					dibujarArista(mapa.obtenerProvinciaPorId(i).obtenerCoordenadas(), mapa.obtenerProvinciaPorId(j).obtenerCoordenadas());
				}
			}
		}
	}

	private void dibujarRegiones(int[][] matrizDeRelacion) {
		_mapa.removeAllMapPolygons();
		for (int i = 0; i < matrizDeRelacion.length; i++) {
			for (int j = 0; j < matrizDeRelacion.length; j++) {  
				if (matrizDeRelacion[i][j] > 0) {
					dibujarAristaRegiones(mapa.obtenerProvinciaPorId(i).obtenerCoordenadas(), mapa.obtenerProvinciaPorId(j).obtenerCoordenadas(), Color.RED);
				}
			}
		}
	}

	private void mostrarRelaciones(boolean soloRegiones) {
		JLabel lblRelaciones = new JLabel("Similitudes");
		lblRelaciones.setForeground(new Color(0, 255, 0));
		lblRelaciones.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblRelaciones.setBounds(10, 6, 140, 22);
		panelMuestraRelaciones.add(lblRelaciones);
		
	    String[] columnas = {"Origen", "Destino", "Similaridad"};
	    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
	    
		ArrayList<Relacion> relaciones = new ArrayList<Relacion>();
		if(soloRegiones) {
			relaciones = mapa.obtenerRelacionesRegiones();
		}
		else {
			relaciones = mapa.obtenerRelaciones();
		}

	    for (Relacion relacion : relaciones) {
	        Provincia provinciaA = relacion.obtenerProvincias().get(0);
	        Provincia provinciaB = relacion.obtenerProvincias().get(1);
	        int similitud = relacion.obtenerSimilitud();
	        Object[] fila = {provinciaA.obtenerNombre(), provinciaB.obtenerNombre(), similitud};
	        modelo.addRow(fila);
	    }
	    
	    JTable tablaRelaciones = new JTable(modelo);
	    JScrollPane scrollPane = new JScrollPane(tablaRelaciones);
	    scrollPane.setBounds(0, 39, 228, 427);
	    panelMuestraRelaciones.add(scrollPane);
	}
	
	private void limpiarScrollPane() {
	    panelMuestraRelaciones.revalidate();
	    panelMuestraRelaciones.repaint();
	}

	private void reset() {   
	    btnReset.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	            _mapa.removeAllMapMarkers();
	            _mapa.removeAllMapPolygons();
	            mapa.reiniciarMapa();
	            limpiarScrollPane();
				cargarDesplegablesProvincias();

				btnCrearRelacion.setEnabled(true);
				btnEliminarRelacion.setEnabled(true);
				textSimilitud.setText(null);
				textCantidadRegiones.setText(null);
	        }
	    });
	}

	private void cargarDesplegablesProvincias() {
		comboBox_Provincia1.setModel(new DefaultComboBoxModel<>(mapa.obtenerProvincias().toArray(new String[0])));
		comboBox_Provincia2.setModel(new DefaultComboBoxModel<>(mapa.obtenerProvincias().toArray(new String[0])));
	}	
}