package view.noUser;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Administrador;
import control.Manager;
import javax.swing.JDialog;
import view.administrator.MenuAdmin;

public class Inicio_sesion extends JDialog implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNombreUsu;
    private JPasswordField passwordField;
    private JTextField txtContrasena;
    private JButton btnIniciarSesion;
    private JCheckBox chckbxVerContrasea;
    private Manager man;
    private int baseDeDatos;

    

    /**
     * Create the frame.
     */
    public Inicio_sesion(int baseDeDatos) {
        this.baseDeDatos=baseDeDatos;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 358, 219);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtNombreUsu = new JTextField();
        txtNombreUsu.setBounds(30, 56, 193, 20);
        contentPane.add(txtNombreUsu);
        txtNombreUsu.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(30, 101, 193, 20);
        contentPane.add(passwordField);

        chckbxVerContrasea = new JCheckBox("Ver contrase\u00F1a");
        chckbxVerContrasea.setBounds(125, 128, 117, 23);
        contentPane.add(chckbxVerContrasea);
        chckbxVerContrasea.addActionListener(this);

        btnIniciarSesion = new JButton("Iniciar sesion");
        btnIniciarSesion.setBounds(221, 154, 111, 23);
        contentPane.add(btnIniciarSesion);
        btnIniciarSesion.addActionListener(this);

        JLabel lblNombreUsuario = new JLabel("Nombre usuario");
        lblNombreUsuario.setBounds(30, 41, 111, 14);
        contentPane.add(lblNombreUsuario);

        JLabel lblContrasea = new JLabel("Contrase\u00F1a");
        lblContrasea.setBounds(30, 86, 79, 14);
        contentPane.add(lblContrasea);

        txtContrasena = new JTextField();
        txtContrasena.setBounds(10, 155, 193, 20);
        contentPane.add(txtContrasena);
        txtContrasena.setColumns(10);
        txtContrasena.setVisible(false);
        
        man= new Manager(baseDeDatos);       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Al pulsar el boton de iniciar sesion
        if (e.getSource().equals(btnIniciarSesion)) {
            boolean ok = comprobarUsuario();
            //si ok es true, cargaremos la ventana de administrador
            if (ok) {
                JOptionPane.showMessageDialog(null, "Usuario correcto");
                this.dispose();
                Administrador ad = new Administrador();
                ad = man.obtenerAdmin(txtNombreUsu.getText());
                MenuAdmin a = new MenuAdmin(ad, baseDeDatos);
                a.setVisible(true);
            }
        //Al marcar o desmarcar  la check box de ver contraseña
        } else if (e.getSource().equals(chckbxVerContrasea)) {
            if (chckbxVerContrasea.isSelected()) {
                passwordField.setVisible(false);
                txtContrasena.setText(String.valueOf(passwordField.getPassword()));
                txtContrasena.setBounds(30, 101, 193, 20);
                txtContrasena.setVisible(true);
            } else {
                passwordField.setVisible(true);
                txtContrasena.setBounds(10, 115, 193, 20);
                txtContrasena.setVisible(false);
            }
        }

    }
    /**
     * Metodo que comprueba si los campos de  usuario y contraseña estan 
     * informados y son correctos
     * @return ok si es correcto false si no lo es
     */
    public boolean comprobarUsuario() {
        boolean ok = true;
        String nombreU = txtNombreUsu.getText();
        String pass = String.copyValueOf(passwordField.getPassword());
        //comprueba si los campos estan informados
        if (nombreU.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios", "Error", JOptionPane.ERROR_MESSAGE);
            ok = false;
        } else {
            //si estan informados, se pasa a compobar si los campos son correctos
            ok = man.comprobarPass(nombreU, pass);
            if(!ok){
                //Si no es correcto,mostraremos un mensaje de error
                JOptionPane.showMessageDialog(null, "Usuario incorrecto");
            }
        }
        return ok;

    }
}