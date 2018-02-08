package view.administrator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ModificarPS extends JDialog implements ActionListener {

    private Manager man;
    private JPanel contentPane;
    private JTextField txtTitulo;
    private JTextField txtPais;
    private JTextField txtFechaP;
    private JTextField txtDuracion;
    private JTextField txtFechaF;
    private JTextField txtNumCaps;
    private JTextField txtNotaP;
    private JTextField txtNotaU;
    private JTextField textField;
    private JButton btnBuscar;
    private JButton btnAadir;
    private JRadioButton rdbtnSerie;
    private JRadioButton rdbtnPelicula;
    private JButton btnLimpiarG;
    private JButton btnBorrarG;
    private JButton btnAnadirG;
    private JComboBox boxGeneros;
    private JButton btnAnadirD;
    private JComboBox boxDirector;
    private JButton btnModif;
    private JComboBox boxEstado;
    private JComboBox boxResultado;
    private JLabel lblNumCaps;
    private JLabel lblFechaF;
    private JLabel lblEstado;
    private JTextArea txtDescrip;
    private JTextArea txtGenero;
    private JTextField txtDirector;
    private Pelicula pel;
    private ArrayList<Pelicula> peliculas = new ArrayList<>();
    private ArrayList<Genero> generos = new ArrayList<>();
    private ArrayList<Director> directores = new ArrayList<>();
    private Administrador admin;
    private int baseDeDatos;
    

    /**
     * Create the frame.
     */
    public ModificarPS(Administrador admin, int baseDeDatos) {
        this.admin = admin;
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
        lblDuracion.setBounds(171, 146, 46, 14);
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

        txtDescrip = new JTextArea();
        txtDescrip.setBounds(34, 239, 234, 176);
        contentPane.add(txtDescrip);

        JLabel lblDescripcion = new JLabel("DESCRIPCION");
        lblDescripcion.setBounds(34, 214, 109, 14);
        contentPane.add(lblDescripcion);

        txtDirector = new JTextField();
        txtDirector.setBounds(313, 267, 234, 20);
        contentPane.add(txtDirector);
        txtDirector.setEditable(false);

        boxDirector = new JComboBox();
        boxDirector.setBounds(313, 236, 234, 23);
        contentPane.add(boxDirector);

        JLabel lblDirector = new JLabel("DIRECTOR");
        lblDirector.setBounds(303, 214, 92, 14);
        contentPane.add(lblDirector);

        btnAnadirD = new JButton("Seleccionar");
        btnAnadirD.setBounds(553, 236, 88, 23);
        contentPane.add(btnAnadirD);

        JSeparator separator = new JSeparator();
        separator.setBounds(293, 202, 360, 2);
        contentPane.add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(293, 298, 360, 2);
        contentPane.add(separator_1);

        JSeparator separator_2 = new JSeparator();
        separator_2.setOrientation(SwingConstants.VERTICAL);
        separator_2.setBounds(293, 202, 2, 97);
        contentPane.add(separator_2);

        JSeparator separator_3 = new JSeparator();
        separator_3.setOrientation(SwingConstants.VERTICAL);
        separator_3.setBounds(651, 201, 2, 99);
        contentPane.add(separator_3);

        JSeparator separator_4 = new JSeparator();
        separator_4.setBounds(676, 348, 360, 2);
        contentPane.add(separator_4);

        txtGenero = new JTextArea();
        txtGenero.setBounds(696, 261, 234, 74);
        contentPane.add(txtGenero);

        boxGeneros = new JComboBox();
        boxGeneros.setBounds(696, 235, 234, 23);
        contentPane.add(boxGeneros);

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

        btnAnadirG = new JButton("+");
        btnAnadirG.setBounds(936, 235, 46, 23);
        contentPane.add(btnAnadirG);

        btnBorrarG = new JButton("-");
        btnBorrarG.setBounds(936, 264, 46, 23);
        contentPane.add(btnBorrarG);

        btnLimpiarG = new JButton("Limpiar");
        btnLimpiarG.setBounds(936, 291, 89, 23);
        contentPane.add(btnLimpiarG);

        JSeparator separator_7 = new JSeparator();
        separator_7.setOrientation(SwingConstants.VERTICAL);
        separator_7.setBounds(1034, 200, 2, 150);
        contentPane.add(separator_7);

        txtNotaP = new JTextField();
        txtNotaP.setColumns(10);
        txtNotaP.setBounds(293, 330, 115, 20);
        contentPane.add(txtNotaP);

        JLabel lblNotaP = new JLabel("NOTA PRENSA");
        lblNotaP.setBounds(293, 314, 109, 14);
        contentPane.add(lblNotaP);

        txtNotaU = new JTextField();
        txtNotaU.setColumns(10);
        txtNotaU.setBounds(424, 330, 115, 20);
        contentPane.add(txtNotaU);

        JLabel lblNotaU = new JLabel("NOTA USUARIOS");
        lblNotaU.setBounds(424, 314, 115, 14);
        contentPane.add(lblNotaU);

        btnModif = new JButton("Modificar");
        btnModif.setBounds(951, 381, 89, 23);
        contentPane.add(btnModif);

        JSeparator separator_8 = new JSeparator();
        separator_8.setBounds(34, 83, 999, 2);
        contentPane.add(separator_8);

        textField = new JTextField();
        textField.setBounds(144, 30, 124, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(278, 29, 89, 23);
        contentPane.add(btnBuscar);

        boxResultado = new JComboBox();
        boxResultado.setVisible(false);
        boxResultado.setBounds(144, 54, 124, 20);
        contentPane.add(boxResultado);

        btnAadir = new JButton("A\u00F1adir");
        btnAadir.setVisible(false);
        btnAadir.setBounds(278, 53, 89, 23);
        contentPane.add(btnAadir);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rdbtnPelicula);
        grupo.add(rdbtnSerie);
        //Añadir a los botones un escuchador de acciones
        btnAadir.addActionListener(this);
        btnBuscar.addActionListener(this);
        btnAnadirD.addActionListener(this);
        btnAnadirG.addActionListener(this);
        btnBorrarG.addActionListener(this);
        btnLimpiarG.addActionListener(this);
        btnModif.addActionListener(this);
        rdbtnPelicula.addActionListener(this);
        rdbtnSerie.addActionListener(this);
        //Por defecto se buscara por pelicula
        rdbtnPelicula.setSelected(true);
        //Cargar comboBox con datos
        cargarCombos();
        man = new Manager(baseDeDatos);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(rdbtnSerie)) {
            descubrirCampos(true);
        } else if (e.getSource().equals(rdbtnPelicula)) {
            descubrirCampos(false);
        } else if (e.getSource().equals(btnBuscar)) {
            buscarPS();
        } else if (e.getSource().equals(btnAadir)) {
            if (boxResultado.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Tienes que seleccionar una primero", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int indice = boxResultado.getSelectedIndex();
                pel = peliculas.get(indice);
                imprimirDatosPS(pel);
            }
        } else if (e.getSource().equals(btnAnadirD)) {
            seleccionarDir();
        } else if (e.getSource().equals(btnAnadirG)) {
            anadirGenero(pel);
        } else if (e.getSource().equals(btnBorrarG)) {
            borrarGenero(pel);
        } else if (e.getSource().equals(btnLimpiarG)) {
            limpiarGeneros(pel);
        } else if (e.getSource().equals(btnModif)) {
            modificarPelicula(pel);
        }
    }

    /**
     * Metodo que oculta o visualiza ciertos campos segun lo que reciba
     *
     * @param b si es true los visualiza, si es false los oculta
     */
    private void descubrirCampos(boolean b) {
        lblEstado.setVisible(b);
        boxEstado.setVisible(b);
        lblFechaF.setVisible(b);
        txtFechaF.setVisible(b);
        lblNumCaps.setVisible(b);
        txtNumCaps.setVisible(b);
    }

    /**
     * Metodo que busca peliculas o series por titulo
     */
    private void buscarPS() {
        String busqueda = textField.getText().toLowerCase();
        Boolean esSerie = rdbtnSerie.isSelected();
        ArrayList<Pelicula> p = new ArrayList<>();
        peliculas.clear();

        if (esSerie) {
            p = man.getAllSeries();
        } else {
            p = man.getAllPeliculas();
        }
        //Añadimos los generos de cada pelicula
        ArrayList<Genero> g = new ArrayList<Genero>();
        for (int i = 0; i < p.size(); i++) {
            //Buscamos los generos de la pelicula
            g = man.getGenerosPelicula(p.get(i).getId_P());
            //insertamos los generos a la pelicula
            p.get(i).setGeneros(g);
            //vaciamos el array
            g.clear();
        }
        //Añadimos al director de cada pelicula
        Director dir;
        for (int i = 0; i < p.size(); i++) {
            //Buscamos el director de la pelicula
            dir = man.getDirectorPelicula(p.get(i).getId_P());
            //Insertamos el director
            peliculas.get(i).setDir(dir);
        }
        //Si se ha escrito algo en el campo de busqueda se aplicara el filtro
        if (!(busqueda.trim().equals(""))) {
            for (int i = 0; i < p.size(); i++) {
                if (p.get(i).getTituloP().toLowerCase().equals(busqueda)) {
                    peliculas.add(p.get(i));
                }
            }
        } else {
            //si no hay nada escrito, se añaden todas las peliculas
            peliculas = p;
        }
        //se añaden las peliculas al comboBox
        anadirAlCombo();

    }

    /**
     * Metodo para añadir las peliculas al comboBox
     */
    private void anadirAlCombo() {
        for (Pelicula pelicula : peliculas) {
            boxResultado.addItem(pelicula.getTituloP());
        }
    }

    /**
     * Metodo que imprime los datos de la pelicula seleccionada
     */
    private void imprimirDatosPS(Pelicula p) {
        SimpleDateFormat dma = new SimpleDateFormat("dd/MM/yyyy");
        txtTitulo.setText(p.getTituloP());
        txtPais.setText(p.getPaisP());
        txtFechaP.setText(dma.format(p.getFechaP()));
        txtDuracion.setText(String.valueOf(p.getDuracionP()));
        txtDescrip.setText(p.getDescriP());
        txtDirector.setText(p.getDirector());
        txtNotaP.setText(String.valueOf(p.getNotaPren()));
        txtNotaU.setText(String.valueOf(p.getNotaUsu()));
        if (p instanceof Serie) {
            txtFechaF.setText(dma.format(((Serie) p).getFechaFin()));
            txtNumCaps.setText(String.valueOf(((Serie) p).getNumCap()));
            boxEstado.setSelectedItem(((Serie) p).getEstado());
        }
        imprimirGeneros(p);

    }

    /**
     * Metodo que añade a los comboBox de estado, directores y generos datos
     */
    private void cargarCombos() {
        //añadimos los estados de la serie al comboBox
        boxEstado.addItem("En emision");
        boxEstado.addItem("Finalizada");

        generos = man.cargarGeneros();
        for (Genero genero : generos) {
            boxGeneros.addItem(genero.getDescrip_gen());
        }
        directores = man.cargarDirectores();
        for (Director director : directores) {
            boxDirector.addItem(director.getNombre() + " " + director.getApell());

        }
    }

    /**
     * Metodo que imprime en el campo director, el director seleccionado de la
     * lista
     */
    private void seleccionarDir() {
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
     * Metodo que recoje los nuevos datos de la pelicula a modificar, y los sube
     * a la BD
     * @param pel pelicula a modificar
     */
    private void modificarPelicula(Pelicula pel) {
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
                    ((Serie)pel).setEstado((String) boxEstado.getSelectedItem());
                    ((Serie)pel).setFechaFin(dma.parse(txtFechaF.getText()));
                    ((Serie)pel).setNumCap(Integer.parseInt(txtNumCaps.getText()));
                }
                man.modificarP(pel);
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
            Date fechaP = dma.parse(txtFechaP.getText());
            if (pel instanceof Serie) {
                String estado = (String) boxEstado.getSelectedItem();
                //Se compruba que esta escrito y que es un numero
                Integer numCaps = Integer.parseInt(txtNumCaps.getText());
                //Se comprueba que la fecha esta escrita y con el formato adecuado
                Date fechaF = dma.parse(txtFechaF.getText());
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

}
