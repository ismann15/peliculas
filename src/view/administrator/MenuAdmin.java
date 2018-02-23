package view.administrator;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Administrador;
import model.Pelicula;
import control.Manager;
import view.noUser.Inicio_sesion;

public class MenuAdmin extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JRadioButton rdbtnAltaDirectores;
    private JRadioButton rdbtnAltaGenero;
    private JRadioButton rdbtnEliminarPS;
    private JRadioButton rdbtnModificarPS;
    private JRadioButton rdbtnAltaPS;
    private JRadioButton rdbtnBuscarPeliculas;
    private JButton btnCerrarSesion;
    private JButton btnIr;
    private Manager man;
    Pelicula p = new Pelicula();
    private Administrador admin = new Administrador();
    private int baseDeDatos;

    /**
     * Create the frame.
     *
     * @param string
     */
    public MenuAdmin(Administrador ad, int baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
        admin = ad;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 467, 270);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnCerrarSesion = new JButton("Cerrar Sesion");
        btnCerrarSesion.setBounds(319, 65, 113, 23);
        contentPane.add(btnCerrarSesion);

        btnIr = new JButton("Ir");
        btnIr.setEnabled(false);
        btnIr.setBounds(319, 31, 113, 23);
        contentPane.add(btnIr);

        JLabel lblOpciones = new JLabel("OPCIONES");
        lblOpciones.setBounds(107, 11, 71, 14);
        contentPane.add(lblOpciones);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        panel.setBounds(10, 31, 299, 194);
        contentPane.add(panel);
        panel.setLayout(null);

        rdbtnAltaDirectores = new JRadioButton("A\u00F1adir Directores");
        rdbtnAltaDirectores.setBounds(6, 111, 206, 23);
        panel.add(rdbtnAltaDirectores);

        rdbtnAltaPS = new JRadioButton("Dar Alta Pelicula o Serie");
        rdbtnAltaPS.setBounds(6, 7, 272, 23);
        panel.add(rdbtnAltaPS);

        rdbtnModificarPS = new JRadioButton("Modificar Datos Pelicula o Serie");
        rdbtnModificarPS.setBounds(6, 33, 206, 23);
        panel.add(rdbtnModificarPS);

        rdbtnAltaGenero = new JRadioButton("A\u00F1adir Generos");
        rdbtnAltaGenero.setBounds(6, 85, 162, 23);
        panel.add(rdbtnAltaGenero);

        rdbtnEliminarPS = new JRadioButton("Eliminar Pelicula o Serie");
        rdbtnEliminarPS.setBounds(6, 59, 151, 23);
        panel.add(rdbtnEliminarPS);

        rdbtnBuscarPeliculas = new JRadioButton("Buscar Peliculas");
        rdbtnBuscarPeliculas.setBounds(6, 165, 109, 23);
        panel.add(rdbtnBuscarPeliculas);

        ButtonGroup opciones = new ButtonGroup();
        opciones.add(rdbtnAltaGenero);
        opciones.add(rdbtnModificarPS);
        opciones.add(rdbtnAltaPS);
        opciones.add(rdbtnAltaDirectores);
        opciones.add(rdbtnEliminarPS);

        opciones.add(rdbtnBuscarPeliculas);

        rdbtnAltaGenero.addActionListener(this);
        rdbtnModificarPS.addActionListener(this);
        rdbtnAltaPS.addActionListener(this);
        rdbtnAltaDirectores.addActionListener(this);
        rdbtnEliminarPS.addActionListener(this);

        rdbtnBuscarPeliculas.addActionListener(this);
        btnCerrarSesion.addActionListener(this);
        btnIr.addActionListener(this);

        man = new Manager(baseDeDatos);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(rdbtnAltaGenero)) {
            btnIr.setEnabled(true);
        } else if (e.getSource().equals(rdbtnModificarPS)) {
            btnIr.setEnabled(true);
        } else if (e.getSource().equals(rdbtnAltaPS)) {
            btnIr.setEnabled(true);
        } else if (e.getSource().equals(rdbtnAltaDirectores)) {
            btnIr.setEnabled(true);
        } else if (e.getSource().equals(rdbtnEliminarPS)) {
            btnIr.setEnabled(true);
        }else if (e.getSource().equals(rdbtnBuscarPeliculas)) {
            btnIr.setEnabled(true);
        } else if (e.getSource().equals(btnCerrarSesion)) {
            Inicio_sesion a = new Inicio_sesion(baseDeDatos);
            a.setVisible(true);
        } else if (e.getSource().equals(btnIr)) {
            abrirVentana();
        }
    }

    /**
     * Metodo que comprueba que radio boton esta abierto y abre la ventana
     * seleccionada
     */
    private void abrirVentana() {
        this.dispose();
        if (rdbtnAltaGenero.isSelected()) {
            AniadirGenero a = new AniadirGenero(1, admin, p, baseDeDatos);
            a.setVisible(true);
        } else if (rdbtnModificarPS.isSelected()) {
            ModificarPS a = new ModificarPS(admin, baseDeDatos);
            a.setVisible(true);
        } else if (rdbtnAltaPS.isSelected()) {
            DarAltaPS a = new DarAltaPS(admin, p, baseDeDatos);
            a.setVisible(true);
        } else if (rdbtnAltaDirectores.isSelected()) {
            AniadirDirector a = new AniadirDirector(1, admin, p, baseDeDatos);
            a.setVisible(true);
        } else if (rdbtnEliminarPS.isSelected()) {
            EliminarPS a = new EliminarPS(admin, baseDeDatos);
            a.setVisible(true);

        } else if (rdbtnBuscarPeliculas.isSelected()) {
            BuscarPeliculas a = new BuscarPeliculas(admin, baseDeDatos);
            a.setVisible(true);
        }

    }
}
