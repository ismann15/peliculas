package view.administrator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Administrador;
import model.Director;
import model.Pelicula;
import control.Manager;

public class AniadirDirector extends JDialog implements ActionListener {

    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtApell;
    private JTextField txtPais;
    private JButton btnCancelar;
    private JButton btnAadir;
    private int marcador;
    private Administrador admin;
    private Pelicula pel;
    private int baseDeDatos;
    private Manager man;

    /**
     * Create the frame.
     */
    public AniadirDirector(int num, Administrador admin, Pelicula p, int baseDeDatos) {
        this.baseDeDatos= baseDeDatos;
        marcador = num;
        this.admin = admin;
        pel = p;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 404, 213);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtNombre = new JTextField();
        txtNombre.setBounds(24, 49, 86, 20);
        contentPane.add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(24, 34, 86, 14);
        contentPane.add(lblNombre);

        txtApell = new JTextField();
        txtApell.setBounds(24, 97, 86, 20);
        contentPane.add(txtApell);
        txtApell.setColumns(10);

        JLabel lblApellido = new JLabel("Apellido 1");
        lblApellido.setBounds(24, 82, 46, 14);
        contentPane.add(lblApellido);

        txtPais = new JTextField();
        txtPais.setBounds(216, 97, 86, 20);
        contentPane.add(txtPais);
        txtPais.setColumns(10);

        JLabel lblPais = new JLabel("Pais");
        lblPais.setBounds(216, 82, 46, 14);
        contentPane.add(lblPais);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 128, 420, 2);
        contentPane.add(separator);

        btnAadir = new JButton("A\u00F1adir");
        btnAadir.setBounds(274, 141, 89, 23);
        contentPane.add(btnAadir);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(175, 141, 89, 23);
        contentPane.add(btnCancelar);

        btnCancelar.addActionListener(this);
        btnAadir.addActionListener(this);
        man = new Manager(baseDeDatos);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAadir)) {
            comprobarDirector();
        } else if (e.getSource().equals(btnCancelar)) {
            this.dispose();
            if (marcador == 1) {
                MenuAdmin a = new MenuAdmin(admin, baseDeDatos);
                a.setVisible(true);

            } else if (marcador == 2) {
                DarAltaPS a = new DarAltaPS(admin, pel, baseDeDatos);
                a.setVisible(true);
            }
        }

    }
    /**
     * Metodo que comprueba los datos del director que se quiere
     * añadir, para ver si estos son correctos
     */
    private void comprobarDirector() {
        String nombre = txtNombre.getText();
        String apell = txtApell.getText();
        String pais= txtPais.getText();
        //Comprobar si los campos estan vacios
        if (nombre.trim().isEmpty() || apell.trim().isEmpty()||
                pais.trim().isEmpty()) {
            //estan vacios
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //no estan vacios, se va a comprobar si el director ya esta almacenado
            
            boolean ok = man.directorExiste(nombre, apell);
            if (ok) {
                //El director que se intenta almacenar ya existe
                JOptionPane.showMessageDialog(null, "Director ya insertado", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //se inserta el director
                Director dir = new Director();
                dir.setNombre(nombre);
                dir.setApell(apell);
                dir.setPais_dir(pais);
                dir.setId_Dir(man.obtenerIdDirectorMax());
                man.crearDirector(dir);
                JOptionPane.showMessageDialog(null, "Director insertado correctamente");

            }
        }

    }

}
