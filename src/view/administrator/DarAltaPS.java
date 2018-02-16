package view.administrator;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
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
import javax.swing.JScrollPane;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;

public class DarAltaPS extends JDialog implements ActionListener {

    private JPanel contentPane;
    private JTextField txtTitulo;
    private JTextField txtPais;
    private JTextField txtFechaP;
    private JTextField txtDuracion;
    private JTextField txtFechaF;
    private JTextField txtNumCaps;
    private JTextField txtNotaP;
    private JTextField txtNotaU;
    private JTextField txtDirector;
    private JRadioButton rdbtnPelicula;
    private JRadioButton rdbtnSerie;
    private JButton btnSeleccionarDir;
    private JButton btnCrearDirector;
    private JButton btnCrearGenero;
    private JButton btnLimpiarG;
    private JButton btnAnadirG;
    private JButton btnBorrarG;
    private JButton btnCancelar;
    private JButton btnAadir;
    private JComboBox<String> boxEstado;
    private JLabel lblEstado;
    private JLabel lblFechaF;
    private JLabel lblNumCaps;
    private JComboBox<String> boxDirector;
    private JComboBox<String> boxGeneros;
    private JTextArea txtGenero;
    private JTextArea txtDescrip;
    private JTextField txtDuracionAl;

    private Manager man;
    private static Pelicula p;
    private Pelicula pel;
    private static Administrador admin;
    private Boolean tieneDatos = false;
    private int baseDeDatos;

    

    /**
     * Create the frame.
     *
     * @param pel
     */
    public DarAltaPS(Administrador a, Pelicula p, int baseDeDatos) {
        this.p = p;
        admin = a;
        this.baseDeDatos= baseDeDatos;
        man = new Manager(baseDeDatos);
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
        txtTitulo.setBounds(34, 119, 115, 20);
        contentPane.add(txtTitulo);
        txtTitulo.setColumns(10);

        JLabel lblTitulo = new JLabel("Titulo");
        lblTitulo.setBounds(34, 103, 46, 14);
        contentPane.add(lblTitulo);

        txtPais = new JTextField();
        txtPais.setColumns(10);
        txtPais.setBounds(165, 119, 115, 20);
        contentPane.add(txtPais);

        JLabel lblPais = new JLabel("Pais");
        lblPais.setBounds(165, 103, 46, 14);
        contentPane.add(lblPais);

        txtFechaP = new JTextField();
        txtFechaP.setColumns(10);
        txtFechaP.setBounds(34, 162, 115, 20);
        contentPane.add(txtFechaP);

        JLabel lblFechaP = new JLabel("Fecha Publicacion");
        lblFechaP.setBounds(34, 146, 115, 14);
        contentPane.add(lblFechaP);

        txtDuracion = new JTextField();
        txtDuracion.setColumns(10);
        txtDuracion.setBounds(171, 162, 115, 20);
        contentPane.add(txtDuracion);

        JLabel lblDuracion = new JLabel("Duracion");
        lblDuracion.setBounds(171, 146, 109, 14);
        contentPane.add(lblDuracion);

        lblEstado = new JLabel("Estado");
        lblEstado.setBounds(313, 146, 46, 14);
        contentPane.add(lblEstado);
        lblEstado.setVisible(false);

        txtFechaF = new JTextField();
        txtFechaF.setColumns(10);
        txtFechaF.setBounds(453, 162, 115, 20);
        contentPane.add(txtFechaF);
        txtFechaF.setVisible(false);

        lblFechaF = new JLabel("Fecha finalizacion");
        lblFechaF.setBounds(453, 146, 115, 14);
        contentPane.add(lblFechaF);
        lblFechaF.setVisible(false);

        txtNumCaps = new JTextField();
        txtNumCaps.setColumns(10);
        txtNumCaps.setBounds(593, 162, 115, 20);
        contentPane.add(txtNumCaps);
        txtNumCaps.setVisible(false);

        lblNumCaps = new JLabel("Numero capitulos");
        lblNumCaps.setBounds(593, 146, 115, 14);
        contentPane.add(lblNumCaps);
        lblNumCaps.setVisible(false);

        boxEstado = new JComboBox();
        boxEstado.setBounds(313, 162, 115, 20);
        contentPane.add(boxEstado);
        boxEstado.setVisible(false);

        ButtonGroup opciones = new ButtonGroup();
        opciones.add(rdbtnSerie);
        opciones.add(rdbtnPelicula);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(34, 239, 234, 97);
        contentPane.add(scrollPane_1);

        txtDescrip = new JTextArea();
        scrollPane_1.setViewportView(txtDescrip);

        JLabel lblDescripcion = new JLabel("DESCRIPCION");
        lblDescripcion.setBounds(34, 214, 109, 14);
        contentPane.add(lblDescripcion);

        boxDirector = new JComboBox();
        boxDirector.setBounds(313, 236, 234, 23);
        contentPane.add(boxDirector);

        JLabel lblDirector = new JLabel("DIRECTOR");
        lblDirector.setBounds(303, 214, 92, 14);
        contentPane.add(lblDirector);

        JSeparator separator = new JSeparator();
        separator.setBounds(293, 202, 360, 2);
        contentPane.add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(293, 291, 360, 2);
        contentPane.add(separator_1);

        JSeparator separator_2 = new JSeparator();
        separator_2.setOrientation(SwingConstants.VERTICAL);
        separator_2.setBounds(293, 202, 2, 91);
        contentPane.add(separator_2);

        JSeparator separator_3 = new JSeparator();
        separator_3.setOrientation(SwingConstants.VERTICAL);
        separator_3.setBounds(651, 201, 2, 92);
        contentPane.add(separator_3);

        JSeparator separator_4 = new JSeparator();
        separator_4.setBounds(676, 347, 385, 3);
        contentPane.add(separator_4);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(696, 262, 234, 74);
        contentPane.add(scrollPane);

        txtGenero = new JTextArea();
        scrollPane.setViewportView(txtGenero);

        boxGeneros = new JComboBox();
        boxGeneros.setBounds(696, 236, 234, 23);
        contentPane.add(boxGeneros);

        JSeparator separator_5 = new JSeparator();
        separator_5.setBounds(676, 202, 385, 2);
        contentPane.add(separator_5);

        JLabel lblGenero = new JLabel("GENERO");
        lblGenero.setBounds(686, 213, 92, 14);
        contentPane.add(lblGenero);

        JSeparator separator_6 = new JSeparator();
        separator_6.setOrientation(SwingConstants.VERTICAL);
        separator_6.setBounds(676, 201, 2, 150);
        contentPane.add(separator_6);

        btnAnadirG = new JButton("+");
        btnAnadirG.setBounds(940, 236, 46, 23);
        contentPane.add(btnAnadirG);

        btnBorrarG = new JButton("-");
        btnBorrarG.setBounds(990, 236, 46, 23);
        contentPane.add(btnBorrarG);

        btnLimpiarG = new JButton("Limpiar");
        btnLimpiarG.setBounds(940, 262, 89, 23);
        contentPane.add(btnLimpiarG);

        JSeparator separator_7 = new JSeparator();
        separator_7.setOrientation(SwingConstants.VERTICAL);
        separator_7.setBounds(1059, 200, 2, 150);
        contentPane.add(separator_7);

        txtNotaP = new JTextField();
        txtNotaP.setColumns(10);
        txtNotaP.setBounds(407, 316, 115, 20);
        contentPane.add(txtNotaP);

        JLabel lblNotaP = new JLabel("NOTA PRENSA");
        lblNotaP.setBounds(407, 300, 109, 14);
        contentPane.add(lblNotaP);

        txtNotaU = new JTextField();
        txtNotaU.setColumns(10);
        txtNotaU.setBounds(538, 316, 115, 20);
        contentPane.add(txtNotaU);

        JLabel lblNotaU = new JLabel("NOTA USUARIOS");
        lblNotaU.setBounds(538, 300, 115, 14);
        contentPane.add(lblNotaU);

        btnAadir = new JButton("A\u00F1adir");
        btnAadir.setBounds(951, 381, 89, 23);
        contentPane.add(btnAadir);

        JSeparator separator_8 = new JSeparator();
        separator_8.setBounds(34, 83, 999, 2);
        contentPane.add(separator_8);

        txtDirector = new JTextField();
        txtDirector.setBounds(313, 263, 115, 20);
        contentPane.add(txtDirector);
        txtDirector.setColumns(10);

        btnSeleccionarDir = new JButton("Seleccionar");
        btnSeleccionarDir.setBounds(552, 236, 89, 23);
        contentPane.add(btnSeleccionarDir);

        btnCrearDirector = new JButton("Crear Director");
        btnCrearDirector.setBounds(438, 262, 130, 23);
        contentPane.add(btnCrearDirector);

        btnCrearGenero = new JButton("Crear Genero");
        btnCrearGenero.setBounds(940, 291, 109, 23);
        contentPane.add(btnCrearGenero);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(858, 381, 89, 23);
        contentPane.add(btnCancelar);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rdbtnSerie);
        grupo.add(rdbtnPelicula);

        txtDuracionAl = new JTextField();
        txtDuracionAl.setBounds(295, 316, 86, 20);
        contentPane.add(txtDuracionAl);
        txtDuracionAl.setColumns(10);

        JLabel lblTiempoAlquiler = new JLabel("Tiempo alquiler");
        lblTiempoAlquiler.setBounds(297, 300, 98, 14);
        contentPane.add(lblTiempoAlquiler);

        btnCrearDirector.addActionListener(this);
        btnSeleccionarDir.addActionListener(this);
        btnAnadirG.addActionListener(this);
        btnBorrarG.addActionListener(this);
        btnLimpiarG.addActionListener(this);
        btnCrearGenero.addActionListener(this);
        btnAadir.addActionListener(this);
        btnCancelar.addActionListener(this);
        rdbtnPelicula.addActionListener(this);
        rdbtnSerie.addActionListener(this);
        cargarDatos();
        if (!(this.p == null)) {
            pel = p;
            tieneDatos = true;
            cargarDatosPelicula(p);
        }
        

    }

    public void cargarDatos() {
        //cargar Estados
        cargarEstados();
        //cargarDirectores
        cargarDirectores();
        //cargarGeneros
        cargarGeneros();

    }

    public void cargarGeneros() {

        ArrayList<Genero> gen = man.cargarGeneros();
        for (Genero genero : gen) {
            boxGeneros.addItem(genero.getDescrip_gen());
        }
        boxGeneros.setSelectedItem(null);

    }

    public void cargarDirectores() {
        ArrayList<Director> dir = man.cargarDirectores();
        for (Director director : dir) {
            boxDirector.addItem(director.getNombre() + " " + director.getApell());
        }
        boxDirector.setSelectedItem(null);

    }

    public void cargarEstados() {
        boxEstado.addItem("En emision");
        boxEstado.addItem("Finalizada");
        boxEstado.setSelectedIndex(-1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(rdbtnPelicula)) {
            if (rdbtnPelicula.isSelected()) {
                boxEstado.setVisible(false);
                lblEstado.setVisible(false);
                txtNumCaps.setVisible(false);
                lblNumCaps.setVisible(false);
                txtFechaF.setVisible(false);
                lblFechaF.setVisible(false);
                if (!tieneDatos) {
                    pel = new Pelicula();
                }
            }
        } else if (e.getSource().equals(rdbtnSerie)) {
            if (rdbtnSerie.isSelected()) {
                boxEstado.setVisible(true);
                lblEstado.setVisible(true);
                txtNumCaps.setVisible(true);
                lblNumCaps.setVisible(true);
                txtFechaF.setVisible(true);
                lblFechaF.setVisible(true);
                if (!tieneDatos) {
                    pel = new Serie();
                }
            }
        } else if (e.getSource().equals(btnSeleccionarDir)) {
            selecionarDir();
        } else if (e.getSource().equals(btnAnadirG)) {
            anadirGenero(pel);
        } else if (e.getSource().equals(btnBorrarG)) {
            borrarGenero(pel);
        } else if (e.getSource().equals(btnLimpiarG)) {
            limpiarGeneros(pel);
        } else if (e.getSource().equals(btnCrearDirector)) {
            goToCrearDirector();
        } else if (e.getSource().equals(btnCrearGenero)) {
            goToCrearGenero();
        } else if (e.getSource().equals(btnCancelar)) {
            goToMenu();
        } else if (e.getSource().equals(btnAadir)) {
            aniadirPelicula(1);
        }

    }

    private void selecionarDir() {

        if (boxDirector.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un director primero", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            txtDirector.setText((String) boxDirector.getSelectedItem());
        }
    }

    /**
     * Metodo para añadir un genero a la pelicula seleccionada
     *
     * @param pel pelicula seleccionada
     */
    private void anadirGenero(Pelicula pel) {
        if (boxGeneros.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un genero primero", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Boolean ok = false;
            Genero g = man.buscaGenero((String) boxGeneros.getSelectedItem());
            for (int i = 0; i < pel.getGeneros().size(); i++) {
                //comprueba si l genero a insertar en la pelicula, ya esta insertado
                if (pel.getGeneros().get(i).getDescrip_gen().equals((String) boxGeneros.getSelectedItem())) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                pel.setGenero(g);
                imprimirGeneros(pel);
            } else {
                JOptionPane.showMessageDialog(null, "Genero ya insertado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Metodo que elimina un genero de la pelicula seleccionada
     *
     * @param pel pelicula seleccionada
     */
    private void borrarGenero(Pelicula pel) {
        if (boxGeneros.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un genero primero", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Boolean ok = false;
            Genero g = man.buscaGenero((String) boxGeneros.getSelectedItem());
            for (int i = 0; i < pel.getGeneros().size(); i++) {
                //comprueba si l genero a insertar en la pelicula, ya esta insertado
                if (pel.getGeneros().get(i).getDescrip_gen().equals((String) boxGeneros.getSelectedItem())) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                pel.delGen(g);
                imprimirGeneros(pel);
            } else {
                JOptionPane.showMessageDialog(null, "Genero ya insertado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Metodo que borra todos los generos ded una pelicula
     *
     * @param pel pelicula con generos
     */
    private void limpiarGeneros(Pelicula pel) {
        pel.borrarGeneros();
        imprimirGeneros(pel);
    }

    /**
     * Metodo que imprime todos los generos de una pelicula
     *
     * @param p pelicula que tiene los generos
     */
    private void imprimirGeneros(Pelicula p) {
        ArrayList<Genero> generos = p.getGeneros();
        txtGenero.setText("");
        for (Genero genero : generos) {
            txtGenero.append(genero.getDescrip_gen() + "\n");
        }
    }

    /**
     * Metodo para añadir una pelicula en la BD
     *
     * @param accion si es 1 se añadira la pelicula, si es 0 no se añadira
     */
    private void aniadirPelicula(Integer accion) {
        Boolean ok = comprobarDatos(pel);
        SimpleDateFormat dma = new SimpleDateFormat("dd/MM/yyyy");
        if (ok) {
            try {
                pel.setTituloP(txtTitulo.getText());
                pel.setPaisP(txtPais.getText());
                pel.setDuracionP(Integer.parseInt(txtDuracion.getText()));
                pel.setDescriP(txtDescrip.getText());
                pel.setNotaPren(Float.parseFloat(txtNotaP.getText()));
                pel.setNotaUsu(Float.parseFloat(txtNotaU.getText()));
                String nombreD = txtDirector.getText().substring(0, txtDirector.getText().indexOf(" "));
                String apellidoD = txtDirector.getText().substring(txtDirector.getText().indexOf(" "));
                Director d = man.buscarDirector(nombreD, apellidoD);
                pel.setDir(d);
                pel.setFechaP(dma.parse(txtFechaP.getText()));
                if (pel instanceof Serie) {
                    pel.setId_P(man.obtenerIdSerieMax());
                    ((Serie) pel).setEstado((String) boxEstado.getSelectedItem());
                    ((Serie) pel).setFechaFin(dma.parse(txtFechaF.getText()));
                    ((Serie) pel).setNumCap(Integer.parseInt(txtNumCaps.getText()));
                }else{
                    pel.setId_P(man.obtenerIdPeliculaMax());
                }
                p = pel;
                if (accion == 1) {
                    man.aniadirPelicula(p);
                }
            } catch (ParseException ex) {
                Logger.getLogger(ModificarPS.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private Boolean comprobarDatos(Pelicula pel) {
        Boolean ok = true;
        SimpleDateFormat dma = new SimpleDateFormat("dd/MM/yyyy");
        try {
            //Recogemos el valor de todos los campos
            String titulo = txtTitulo.getText();
            String pais = txtPais.getText();
            //Se compruba que esta escrito y que es un numero
            Integer duracion = Integer.parseInt(txtDuracion.getText());
            String descripcion = txtDescrip.getText();
            //Se compruba que esta escrito y que es un numero
            Float notaP = Float.parseFloat(txtNotaP.getText());
            //Se compruba que esta escrito y que es un numero
            Float notaU = Float.parseFloat(txtNotaU.getText());
            //Se comprueba el director
            String director = txtDirector.getText();
            //Se comprueba que la fecha esta escrita y con el formato adecuado
            java.util.Date fechaP = dma.parse(txtFechaP.getText());
            if (pel instanceof Serie) {
                String estado = (String) boxEstado.getSelectedItem();
                //Se compruba que esta escrito y que es un numero
                Integer numCaps = Integer.parseInt(txtNumCaps.getText());
                //Se comprueba que la fecha esta escrita y con el formato adecuado
                java.util.Date fechaF = dma.parse(txtFechaF.getText());
            }
            if (titulo.trim().equals("") || pais.trim().equals("") || descripcion.trim().equals("") || director.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "No puede haber campos vacios", "Error", JOptionPane.ERROR_MESSAGE);
                ok = false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Revisa los campos numericos", "Error", JOptionPane.ERROR_MESSAGE);
            ok = false;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Revisa las fechas, tienen que tener el formato DD/MM/AAAA", "Error", JOptionPane.ERROR_MESSAGE);
            ok = false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error", "Error", JOptionPane.ERROR_MESSAGE);
            ok = false;
        }
        return ok;
    }

    /**
     * Metodo para crear un director
     */
    private void goToCrearDirector() {
        aniadirPelicula(0);
        this.dispose();
        AniadirDirector a = new AniadirDirector(2, admin, p, baseDeDatos);
        a.setVisible(true);
    }

    /**
     * Metodo para crear un genero
     */
    private void goToCrearGenero() {
        aniadirPelicula(0);
        this.dispose();
        AniadirGenero a = new AniadirGenero(2, admin, p, baseDeDatos);
        a.setVisible(true);
    }

    /**
     * Metodo para volver al menu de administrador
     */
    private void goToMenu() {
        p = null;
        this.dispose();
        MenuAdmin m = new MenuAdmin(admin, baseDeDatos);
        m.setVisible(true);
    }
    /**
     * Metodo que imprime los datos de una pelicula que recibe
     * @param p pelicula cuyos datos van a ser impresos
     */
    private void cargarDatosPelicula(Pelicula p) {
        SimpleDateFormat dma= new SimpleDateFormat("dd/MM/yyyy");
        txtTitulo.setText(p.getTituloP());
        txtPais.setText(p.getPaisP());
        txtDuracion.setText(String.valueOf(p.getDuracionP()));
        txtDescrip.setText(p.getDescriP());
        //txtDuracionAl.setText(String.valueOf(p.getDuracionAl()));
        txtNotaP.setText(String.valueOf(p.getNotaPren()));
        txtNotaU.setText(String.valueOf(p.getNotaUsu()));
        txtDirector.setText(p.getDirector());
        txtFechaP.setText(dma.format(p.getFechaP()));
        for (Genero genero : p.getGen()) {
            txtGenero.append(genero.getDescrip_gen()+"\n");
        }
        if(p instanceof Serie){
            boxEstado.setSelectedItem(((Serie) p).getEstado());
            txtFechaF.setText(dma.format((((Serie) p).getFechaFin())));
            txtNumCaps.setText(String.valueOf(((Serie) p).getNumCap()));
        }
        
   }
}
