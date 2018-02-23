package view.administrator;

import model.Administrador;
import model.Director;
import model.Genero;
import model.Pelicula;
import control.Manager;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class BuscarPeliculas extends JDialog implements ActionListener {

    private JPanel contentPane;
    private JTextField txtTitulo;
    private JLabel lblTitulo;
    private JCheckBox boxEsSerie;
    private JButton btnBuscar;
    private JButton btnVolver;
    private Administrador admin;
    private JComboBox boxGenero;
    private JComboBox boxDirector;
    private Manager man;
    private int baseDeDatos;

    private ArrayList<Genero> generos = new ArrayList<>();
    private ArrayList<Director> directores = new ArrayList<>();
    private ArrayList<Pelicula> pelis = new ArrayList<>();

    /**
     * Create the frame.
     */
    public BuscarPeliculas(Administrador admin, int baseDeDatos) {
        this.admin = admin;
        this.baseDeDatos = baseDeDatos;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 458, 149);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        boxGenero = new JComboBox();
        boxGenero.setBounds(29, 30, 116, 20);
        contentPane.add(boxGenero);

        boxDirector = new JComboBox();
        boxDirector.setBounds(164, 30, 116, 20);
        contentPane.add(boxDirector);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(29, 73, 116, 20);
        contentPane.add(txtTitulo);
        txtTitulo.setColumns(10);

        lblTitulo = new JLabel("Titulo");
        lblTitulo.setBounds(29, 59, 46, 14);
        contentPane.add(lblTitulo);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(345, 29, 89, 23);
        contentPane.add(btnBuscar);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(345, 72, 89, 23);
        contentPane.add(btnVolver);

        JLabel lblGenero = new JLabel("Genero");
        lblGenero.setBounds(29, 11, 46, 14);
        contentPane.add(lblGenero);

        JLabel lblDirector = new JLabel("Director");
        lblDirector.setBounds(164, 11, 46, 14);
        contentPane.add(lblDirector);

        boxEsSerie = new JCheckBox("Es serie");
        boxEsSerie.setBounds(164, 72, 116, 23);
        contentPane.add(boxEsSerie);

        btnBuscar.addActionListener(this);
        btnVolver.addActionListener(this);

        man = new Manager(baseDeDatos);
        cargarDatos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnBuscar)) {
            buscarP();
        } else if (e.getSource().equals(btnVolver)) {
            this.dispose();
            MenuAdmin a = new MenuAdmin(admin, baseDeDatos);
            a.setVisible(true);
        }
    }

    /**
     * Metodo para buscar las pliculas segun los filtros selecciionados
     */
    private void buscarP() {
        String titulo = txtTitulo.getText();
        String genero = (String) boxGenero.getSelectedItem();
        String director = (String) boxDirector.getSelectedItem();
        //comprobamos si se buscan series o no
        Boolean esSerie = boxEsSerie.isSelected();
        ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
        if (esSerie) {
            //Buscamos todas las series
            peliculas = man.getAllSeries();
        } else {
            //Buscamos todas las peliculas
            peliculas = man.getAllPeliculas();
        }
        //Añadimos los generos de cada pelicula
        ArrayList<Genero> g;
        for (int i = 0; i < peliculas.size(); i++) {
            g = new ArrayList<>();
            //Buscamos los generos de la pelicula
            g = man.getGenerosPelicula(peliculas.get(i).getId_P());
            //insertamos los generos a la pelicula
            peliculas.get(i).setGeneros(g);
        }
        //Añadimos al director de cada pelicula
        Director dir;
        for (int i = 0; i < peliculas.size(); i++) {
            //Buscamos el director de la pelicula
            dir = man.getDirectorPelicula(peliculas.get(i).getId_P());
            //Insertamos el director
            peliculas.get(i).setDir(dir);
        }

        //Si titulo no esta vacio, filtramos por titulo
        if (!(titulo.trim().equals(""))) {
            peliculas = filtrarTitulo(peliculas, titulo);
        }
        //Si director no esta vacio, filtramos por director
        if (!(director.trim().equals(""))) {
            peliculas = filtrarDirector(peliculas, director);
        }
        //Si genero no esta vacio, filtramos por genero
        if (!(genero.trim().equals(""))) {
            peliculas = filtrarGenero(peliculas, genero);
        }
        //Si  no hay peliculas, se muetyra un mensaje informando 
        if (peliculas.size() == 0) {
            JOptionPane.showMessageDialog(null,
                    "No se han encontrado registros que coincidan.");
        } else {
            pelis = peliculas;
            //Se ciertra esta ventana y se carga la pagina de resultado
            this.dispose();
            ResultadoBuscarPeliculas a = new ResultadoBuscarPeliculas(pelis, admin, baseDeDatos);
            a.setVisible(true);
        }

    }

    /**
     * Metodo para filtrat peliculas por titulo
     *
     * @param peliculas pleiculas a filtrar
     * @param titulo tiulo por el que filtrar
     * @return ArrayLIst de peliculas filtradas
     */
    private ArrayList<Pelicula> filtrarTitulo(ArrayList<Pelicula> peliculas, String titulo) {
        ArrayList<Pelicula> p = new ArrayList<>();
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getTituloP().equalsIgnoreCase(titulo)) {
                p.add(pelicula);
            }
        }
        return p;
    }

    /**
     * Metodo para filtrar peliculas por director
     *
     * @param peliculas peliculas a filtrar
     * @param director director por el que se va a filtrar
     * @return ArrayList de peliculas filtradas
     */
    private ArrayList<Pelicula> filtrarDirector(ArrayList<Pelicula> peliculas, String director) {
        ArrayList<Pelicula> p = new ArrayList<>();
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getDirector().equalsIgnoreCase(director)) {
                p.add(pelicula);
            }
        }
        return p;
    }

    /**
     * Metodo para filtrar peliculas por un genero
     *
     * @param peliculas ArrayList de peliculas que van a ser filtradas
     * @param genero Genero por el que se van a flitrar
     * @return ArrayList de palisculas filtradas
     */
    private ArrayList<Pelicula> filtrarGenero(ArrayList<Pelicula> peliculas, String genero) {
        ArrayList<Pelicula> p = new ArrayList<>();
        for (Pelicula pelicula : peliculas) {
            for(Genero g: pelicula.getGen()){
                if(g.getDescrip_gen().equals(genero)){
                    p.add(pelicula);
                    break;
                }
            }  
        }
        return p;
    }

    /**
     * Metodo para cargarDatos
     */
    private void cargarDatos() {
        cargarGeneros();
        cargarDirectores();

    }

    /**
     * Metodo para recoger todos los generos almacenados en la BD
     */
    private void cargarGeneros() {
        //se recogen todos los generos almacenados en la BD
        generos = man.cargarGeneros();
        boxGenero.addItem("");
        //se insertan los nombres de los generos en el comboBox
        for (Genero genero : generos) {
            boxGenero.addItem(genero.getDescrip_gen());
        }
    }

    /**
     * Metodo para recoger todos los directores almacenados en la BD
     */
    private void cargarDirectores() {
        //se recogen todos los directores almacenados en la BD
        directores = man.cargarDirectores();
        boxDirector.addItem("");
        //se insertan los nombres y apellidos de los directores en el comboBox
        for (Director director : directores) {
            boxDirector.addItem(director.getNombre() + " " + director.getApell());

        }
    }
}
