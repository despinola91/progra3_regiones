package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Component;
import java.awt.Dimension;

public class MainForm 
{

	private JFrame frmProvinciasArgentinas;
	private JPanel panelMapa;
	private JPanel panelControles;
	private JMapViewer _mapa;
	private ArrayList<Coordinate> _lasCoordenadas;
	private JButton btnEliminar;
	private MapPolygonImpl _poligono;
	private JButton btnDibujarPolgono ;

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
		frmProvinciasArgentinas.setTitle("Provincias Argentinas");
		frmProvinciasArgentinas.setBounds(200, 25, 725, 560);
		frmProvinciasArgentinas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProvinciasArgentinas.getContentPane().setLayout(null);
		
		panelMapa = new JPanel();
		panelMapa.setBounds(8, 10, 437, 512);
		frmProvinciasArgentinas.getContentPane().add(panelMapa);
		
		panelControles = new JPanel();
		panelControles.setBounds(457, 11, 242, 446);
		frmProvinciasArgentinas.getContentPane().add(panelControles);		
		panelControles.setLayout(null);
		
		_mapa = new JMapViewer();
		_mapa.setCenter(new Point(1075, 700));
		_mapa.setDisplayPosition(new Coordinate(-41,-63), 4);
		_mapa.setPreferredSize(new Dimension(437, 512));
				
		panelMapa.add(_mapa);

		detectarCoordenadas();
		dibujarPoligono();
		eliminarPoligono();		
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
				String nombre = JOptionPane.showInputDialog("Nombre de la provincia: ");
				_mapa.addMapMarker(new MapMarkerDot(nombre, markeradd));
				
			}}
		});
	}

	private void dibujarPoligono() 
	{
		btnDibujarPolgono = new JButton("Dibujar Grafo");
		btnDibujarPolgono.setBounds(21, 71, 195, 23);
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
		btnEliminar.setBounds(21, 123, 195, 23);
		panelControles.add(btnEliminar);
		panelControles.add(btnDibujarPolgono);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}	
}
