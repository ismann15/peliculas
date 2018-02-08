package view.administrator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Administrador;
import model.Director;
import model.Genero;
import model.Pelicula;
import model.Serie;
import control.Manager;
import javax.swing.JDialog;

public class EliminarPS extends JDialog implements ActionListener {

    private JPanel contentPane;
    private JTextField txtTitulo;
    private JTextField txtPais;
    private JTextField txtFechaP;
    private JTextField txtDuracion;
    private JTextField txtFechaF;
    private JTextField txtNumCaps;
    private JTextField txtNotaP;
    private JTextField txtNotaU;
    private JTextField txtBusqueda;
    private JTextField txtEstado;
    private JRadioButton rdbtnSerie;
    private JRadioButton rdbtnPelicula;
    private JButton btnBuscar;
    private JButton btnAadir;
    private JButton btnEliminar;
    private JButton btnCancelar;
    private JTextField txtDirector;
    private JLabel lblEstado;
    private JLabel lblFechaF;
    private JLabel lblNumCaps;
    private JComboBox boxResultado;
    private ArrayList<Pelicula> p;
    private JTextArea txtDescrip;
    private JTextArea txtGenero;
    private static Administrador admin;
    private int baseDeDatos;
    private Manager man;

   

    /**
     * Create the frame.
     */
    public EliminarPS(Administrador ad, int baseDeDatos) {
        admin = ad;
        this.baseDeDatos= baseDeDatos;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1097, 465);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        rdbtnPelicula = new JRadioButton("PELICULA");
        rdbtnPelicula.setBounds(34, 29, 109, 23);
        contentPane.add(rdbtnPelicula);

        rdbtnSerie = new JRadioButton("SERIE");
        rdbtnSerie.setBounds(34, 53, 109, 23);
        contentPane.add(rdbtnSerie);

        txtTitulo = new JTextField();
        txtTitulo.setEditable(false);
        txtTitulo.setBounds(34, 119, 115, 20);
        contentPane.add(txtTitulo);
        txtTitulo.setColumns(10);

        JLabel lblTitulo = new JLabel("Titulo");
        lblTitulo.setBounds(34, 103, 115, 14);
        contentPane.add(lblTitulo);

        txtPais = new JTextField();
        txtPais.setEditable(false);
        txtPais.setColumns(10);
        txtPais.setBounds(165, 119, 115, 20);
        contentPane.add(txtPais);

        JLabel lblPais = new JLabel("Pais");
        lblPais.setBounds(165, 103, 115, 14);
        contentPane.add(lblPais);

        txtFechaP = new JTextField();
        txtFechaP.setEditable(false);
        txtFechaP.setColumns(10);
        txtFechaP.setBounds(34, 162, 115, 20);
        contentPane.add(txtFechaP);

        JLabel lblFechaP = new JLabel("Fecha Publicacion");
        lblFechaP.setBounds(34, 146, 115, 14);
        contentPane.add(lblFechaP);

        txtDuracion = new JTextField();
        txtDuracion.setEditable(false);
        txtDuracion.setColumns(10);
        txtDuracion.setBounds(165, 162, 115, 20);
        contentPane.add(txtDuracion);

        JLabel lblDuracion = new JLabel("Duracion");
        lblDuracion.setBounds(171, 146, 109, 14);
        contentPane.add(lblDuracion);

        lblEstado = new JLabel("Estado");
        lblEstado.setBounds(313, 146, 112, 14);
        contentPane.add(lblEstado);
        lblEstado.setVisible(false);

        txtFechaF = new JTextField();
        txtFechaF.setEditable(false);
        txtFechaF.setColumns(10);
        txtFechaF.setBounds(453, 162, 115, 20);
        contentPane.add(txtFechaF);
        txtFechaF.setVisible(false);

        lblFechaF = new JLabel("Fecha finalizacion");
        lblFechaF.setBounds(453, 146, 115, 14);
        contentPane.add(lblFechaF);
        lblFechaF.setVisible(false);

        txtNumCaps = new JTextField();
        txtNumCaps.setEditable(false);
        txtNumCaps.setColumns(10);
        txtNumCaps.setBounds(593, 162, 115, 20);
        contentPane.add(txtNumCaps);
        txtNumCaps.setVisible(false);

        lblNumCaps = new JLabel("Numero capitulos");
        lblNumCaps.setBounds(488, 150, 115, 14);
        contentPane.add(lblNumCaps);
        lblNumCaps.setVisible(false);

        ButtonGroup opciones = new ButtonGroup();
        opciones.add(rdbtnSerie);
        opciones.add(rdbtnPelicula);

        txtDescrip = new JTextArea();
        txtDescrip.setEditable(false);
        txtDescrip.setBounds(34, 239, 234, 97);
        contentPane.add(txtDescrip);

        JLabel lblDescripcion = new JLabel("DESCRIPCION");
        lblDescripcion.setBounds(34, 214, 109, 14);
        contentPane.add(lblDescripcion);

        JLabel lblDirector = new JLabel("DIRECTOR");
        lblDirector.setBounds(303, 214, 92, 14);
        contentPane.add(lblDirector);

        JSeparator separator = new JSeparator();
        separator.setBounds(293, 202, 360, 2);
        contentPane.add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(293, 271, 360, 2);
        contentPane.add(separator_1);

        JSeparator separator_2 = new JSeparator();
        separator_2.setOrientation(SwingConstants.VERTICAL);
        separator_2.setBounds(293, 202, 2, 71);
        contentPane.add(separator_2);

        JSeparator separator_3 = new JSeparator();
        separator_3.setOrientation(SwingConstants.VERTICAL);
        separator_3.setBounds(651, 201, 2, 72);
        contentPane.add(separator_3);

        JSeparator separator_4 = new JSeparator();
        separator_4.setBounds(676, 348, 360, 2);
        contentPane.add(separator_4);

        txtGenero = new JTextArea();
        txtGenero.setEditable(false);
        txtGenero.setBounds(696, 261, 234, 74);
        contentPane.add(txtGenero);

        JSeparator separator_5 = new JSeparator();
        separator_5.setBounds(676, 201, 360, 2);
        contentPane.add(separator_5);

        JLabel lblGenero = new JLabel("GENERO");
        lblGenero.setBounds(686, 213, 92, 14);
        contentPane.add(lblGenero);

        JSeparator separator_6 = new JSeparator();
        separator_6.setOrientation(SwingConstants.VERTICAL);
        separator_6.setBounds(676, 201, 2, 150);
        contentPane.add(separator_6);

        JSeparator separator_7 = new JSeparator();
        separator_7.setOrientation(SwingConstants.VERTICAL);
        separator_7.setBounds(1034, 200, 2, 150);
        contentPane.add(separator_7);

        txtNotaP = new JTextField();
        txtNotaP.setEditable(false);
        txtNotaP.setColumns(10);
        txtNotaP.setBounds(290, 304, 115, 20);
        contentPane.add(txtNotaP);

        JLabel lblNotaP = new JLabel("NOTA PRENSA");
        lblNotaP.setBounds(290, 288, 109, 14);
        contentPane.add(lblNotaP);

        txtNotaU = new JTextField();
        txtNotaU.setEditable(false);
        txtNotaU.setColumns(10);
        txtNotaU.setBounds(421, 304, 115, 20);
        contentPane.add(txtNotaU);

        JLabel lblNotaU = new JLabel("NOTA USUARIOS");
        lblNotaU.setBounds(421, 288, 115, 14);
        contentPane.add(lblNotaU);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setBounds(951, 381, 89, 23);
        contentPane.add(btnEliminar);

        JSeparator separator_8 = new JSeparator();
        separator_8.setBounds(34, 83, 999, 2);
        contentPane.add(separator_8);

        txtBusqueda = new JTextField();
        txtBusqueda.setBounds(144, 30, 124, 20);
        contentPane.add(txtBusqueda);
        txtBusqueda.setColumns(10);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(278, 29, 89, 23);
        contentPane.add(btnBuscar);

        boxResultado = new JComboBox();
        boxResultado.setBounds(144, 54, 124, 20);
        contentPane.add(boxResultado);

        btnAadir = new JButton("A\u00F1adir");
        btnAadir.setBounds(278, 53, 89, 23);
        contentPane.add(btnAadir);

        txtEstado = new JTextField();
        txtEstado.setVisible(false);
        txtEstado.setEditable(false);
        txtEstado.setColumns(10);
        txtEstado.setBounds(310, 162, 115, 20);
        contentPane.add(txtEstado);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(852, 381, 89, 23);
        contentPane.add(btnCancelar);

        btnCancelar.addActionListener(this);
        btnBuscar.addActionListener(this);
        btnAadir.addActionListener(this);
        btnEliminar.addActionListener(this);
        rdbtnPelicula.addActionListener(this);
        rdbtnSerie.addActionListener(this);
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rdbtnSerie);
        grupo.add(rdbtnPelicula);
        rdbtnPelicula.setSelected(true);

        txtDirector = new JTextField();
        txtDirector.setBounds(309, 239, 175, 20);
        contentPane.add(txtDirector);
        txtDirector.setColumns(10);
        
        man= new Manager(baseDeDatos);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(rdbtnPelicula)) {
            if (rdbtnPelicula.isSelected()) {
                cambiarVisiblilidad(false);
            }
        } else if (e.getSource().equals(rdbtnSerie)) {
            if (rdbtnSerie.isSelected()) {
                cambiarVisiblilidad(true);
            }
        } else if (e.getSource().equals(btnBuscar)) {
            if (txtBusqueda.getText().toString().trim().isEmpty()) {
                JOptionPane.showMessageDialog(new JPanel(), "Debes escribir primero un titulo", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                //Buscamos en la BDlo que hay escrrito en la busqueda
                buscarPedido();
                //Cargamos el resultado de la busqueda en el coboBox
                cargarTitulos();
            }
        } else if (e.getSource().equals(btnAadir)) {
            cargarDatosPS();
            btnEliminar.setEnabled(true);
        } else if (e.getSource().equals(btnEliminar)) {
            eliminarPS();
        } else if (e.getSource().equals(btnCancelar)) {
            this.dispose();
            MenuAdmin a = new MenuAdmin(admin, baseDeDatos);
            a.setVisible(true);
        }

    }

    private void eliminarPS() {
        String titulo = boxResultado.getSelectedItem().toString();
        int i;
        for (i = 0; i < p.size(); i++) {
            if (p.get(i).getTituloP().equals(titulo)) {
                break;
            }
        }
        
        man.eliminarPS(p.get(i));

    }

    private void cargarDatosPS() {
        if (boxResultado.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(new JPanel(), "Debes seleccionar primero un titulo", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            String titulo = boxResultado.getSelectedItem().toString();
            int i;
            for (i = 0; i < p.size(); i++) {
                if (((Pelicula) p.get(i)).getTituloP().equals(titulo)) {
                    break;
                }
            }
            SimpleDateFormat a = new SimpleDateFormat("dd/MM/yyyy");
            txtTitulo.setText(((Pelicula) p.get(i)).getTituloP());
            txtPais.setText(((Pelicula) p.get(i)).getPaisP());
            txtFechaP.setText(a.format((((Pelicula) p.get(i)).getFechaP())));
            txtDuracion.setText(String.valueOf(((Pelicula) p.get(i)).getDuracionP()));
            txtDescrip.setText(((Pelicula) p.get(i)).getDescriP());
            txtNotaP.setText(String.valueOf(((Pelicula) p.get(i)).getNotaPren()));
            txtNotaP.setText(String.valueOf(((Pelicula) p.get(i)).getNotaUsu()));
            txtDirector.setText(((Pelicula) p.get(i)).getDirector());
            ArrayList<Genero> g = new ArrayList<Genero>();
            ((Pelicula) p.get(i)).getGeneros(g);
            for (Genero genero : g) {
                txtGenero.append(genero.getDescrip_gen() + "\n");
            }
            if (rdbtnSerie.isSelected()) {
                txtEstado.setText(((Serie) p.get(i)).getEstado());;
                txtFechaF.setText(a.format((((Serie) p.get(i)).getFechaFin())));
                txtNumCaps.setText(String.valueOf(((Serie) p.get(i)).getNumCap()));
            }
        }

    }

    private void cargarTitulos() {
        boolean ok = true;
        if (p.isEmpty()) {
            JOptionPane.showMessageDialog(new JPanel(), "No se han encontrado coincidencias");
            txtBusqueda.setText("");
            ok = false;
        } else {
            for (int i = 0; i < p.size(); i++) {
                String a = ((Pelicula) p.get(i)).getTituloP();
                boxResultado.addItem(a);
            }
            boxResultado.setSelectedIndex(-1);
        }

    }

    private void buscarPedido() {
        String busqueda = txtBusqueda.getText().toString();
        int i = 0;
        p = new ArrayList<Pelicula>();
        if (rdbtnSerie.isSelected()) {
            i = 1;
        } else if (rdbtnPelicula.isSelected()) {
            i = 2;
        }
        //Buscamos las peliculas
        p = man.obtenerPS(i, busqueda);
        //Buscamos los generos de las peliculas
        ArrayList<Genero> g = new ArrayList<Genero>();
        for (int j = 0; j < p.size(); j++) {
            g.clear();
            g = man.getGenerosPelicula(p.get(j).getId_P());
            p.get(j).setGeneros(g);
        }
        //Buscamos los directores de las peliculas
        Director dir;
        for (int j = 0; j < p.size(); j++) {
            dir = man.getDirectorPelicula(p.get(j).getId_P());
            p.get(j).setDir(dir);
        }

    }

    private void cambiarVisiblilidad(boolean b) {
        txtEstado.setVisible(b);
        lblEstado.setVisible(b);
        txtFechaF.setVisible(b);
        lblFechaF.setVisible(b);
        txtNumCaps.setVisible(b);
        lblNumCaps.setVisible(b);

    }
}
