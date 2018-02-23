package view.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Administrador;
import model.Genero;
import model.Pelicula;
import control.Manager;

public class AniadirGenero extends JDialog implements ActionListener {

    private JPanel contentPane;
    private JTextField txtNombre;
    private int marcador;
    private Administrador admin;
    private Pelicula pel;
    private JButton btnAadir;
    private JButton btnCancelar;
    private Manager man;
    private int baseDeDatos;

    /**
     * Create the frame.
     */
    public AniadirGenero(int num, Administrador a, Pelicula p, int baseDeDAtos) {
        this.baseDeDatos= baseDeDAtos;
        marcador = num;
        admin = a;
        pel = p;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 366, 136);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtNombre = new JTextField();
        txtNombre.setBounds(24, 36, 86, 20);
        contentPane.add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(24, 23, 46, 14);
        contentPane.add(lblNombre);

        btnAadir = new JButton("A\u00F1adir");
        btnAadir.setBounds(130, 35, 89, 23);
        contentPane.add(btnAadir);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(240, 35, 89, 23);
        contentPane.add(btnCancelar);

        btnAadir.addActionListener(this);
        btnCancelar.addActionListener(this);
        
        man = new Manager(baseDeDAtos);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAadir)) {
            comprobarGenero();
        } else if (e.getSource().equals(btnCancelar)) {
            this.dispose();
            //se comprueba por que ventana se a llegado a esta
            if (marcador == 1) {
                //ha llegado desde el menu de andmin, se vuelve a esa ventana
                MenuAdmin a = new MenuAdmin(admin, baseDeDatos);
                a.setVisible(true);
            } else if (marcador == 2) {
                //ha llegado desde la ventana de crear pelicula, se vuelve a esa ventana
                DarAltaPS a = new DarAltaPS(admin, pel, baseDeDatos);
                a.setVisible(true);
            }
        }

    }
    /**
     * Metodo que comprueba si el genero a insertar es correcto
     */
    private void comprobarGenero() {
        String nombreGen = txtNombre.getText();
        //Comprueba si el campo esta informado
        if (nombreGen.trim().isEmpty()) {
            //no esta informado
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //esta informado, se para a cpomprobar si el genero ya esta insertado
            boolean ok = man.comprobarGeneroExiste(nombreGen);
            if (ok) {
                //Esta insertado en la base de datos
                JOptionPane.showMessageDialog(null, "Genero ya insertado", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //Se inserta el genero
                Genero gen = new Genero();
                gen.setDescrip_gen(nombreGen);
                gen.setId_gen(man.obtenerIdGeneroMax());
                man.crearGenero(gen);
                JOptionPane.showMessageDialog(null, "Genero insertado correctamente");
                txtNombre.setText("");
            }
        }

    }

}