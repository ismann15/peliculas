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

public class BuscarPeliculas extends JDialog implements ActionListener{

    private JPanel contentPane;
    private JTextField txtTitulo;
    private JLabel lblTitulo;
    private JCheckBox boxEsSerie;
    private JButton btnBuscar;
    private JButton btnCancelar;
    private Administrador admin;
    private JComboBox boxGenero;
    private JComboBox boxDirector;
    private Manager man;
    private int baseDeDatos;
    
    /**
     * Create the frame.
     */
    public BuscarPeliculas(Administrador admin, int baseDeDatos) {
        this.admin = admin;
        this.baseDeDatos= baseDeDatos;
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

        JButton btnVolver = new JButton("Volver");
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
        btnCancelar.addActionListener(this);
        
        man= new Manager(baseDeDatos);
        cargarDatos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnBuscar)){
            buscarP();
        }else if(e.getSource().equals(btnCancelar)){
            this.dispose();
            MenuAdmin a= new MenuAdmin(admin, baseDeDatos);
            a.setVisible(true);
        }
    }
    /**
     * Metodo para buscar las pliculas segun los filtros selecciionados
     */
    private void buscarP() {
        String titulo= txtTitulo.getText();
        String genero= (String) boxGenero.getSelectedItem();
        String director= (String) boxDirector.getSelectedItem();
        //comprobamos si se buscan series o no
        Boolean esSerie=boxEsSerie.isSelected();
        ArrayList <Pelicula> pelis= new ArrayList <Pelicula>();
        if(esSerie){
            //Buscamos todas las series
            pelis=man.getAllSeries();
        }else{
            //Buscamos todas las peliculas
            pelis=man.getAllPeliculas();
        }
        //Añadimos los generos de cada pelicula
        ArrayList <Genero> g= new ArrayList<Genero>();
        for(int i=0;i<pelis.size();i++){
            //Buscamos los generos de la pelicula
            g=man.getGenerosPelicula(pelis.get(i).getId_P());
            //insertamos los generos a la pelicula
            pelis.get(i).setGeneros(g);
            //vaciamos el array
            g.clear();
        }
        //Añadimos al director de cada pelicula
        Director dir;
        for(int i=0;i<pelis.size();i++){
            //Buscamos el director de la pelicula
            dir=man.getDirectorPelicula(pelis.get(i).getId_P());
            //Insertamos el director
            pelis.get(i).setDir(dir);
        }
        //Si titulo no esta vacio, filtramos por titulo
        if(!(titulo.trim().equals(""))){
            pelis=filtrarTitulo(pelis,titulo);
        }
        //Si director no esta vacio, filtramos por director
        if(!(director.trim().equals(""))){
            pelis=filtrarDirector(pelis,director);
        }
        //Si genero no esta vacio, filtramos por genero
        if(!(genero.trim().equals(""))){
            pelis=filtrarGenero(pelis,genero);
        }
        //Si  no hay peliculas, se muetyra un mensaje informando 
        if(pelis.size()==0){
            JOptionPane.showMessageDialog(null,
                "No se han encontrado registros que coincidan.");
        }else{
            //Se ciertra esta ventana y se carga la pagina de resultado
            this.dispose();
            ResultadoBuscarPeliculas a = new ResultadoBuscarPeliculas(pelis, admin, baseDeDatos);
            a.setVisible(true);
        }
        
        
    }
    /**
     * Metodo para filtrat peliculas por titulo
     * @param pelis pleiculas a filtrar
     * @param titulo tiulo por el que filtrar
     * @return ArrayLIst de peliculas filtradas
     */
    private ArrayList<Pelicula> filtrarTitulo(ArrayList<Pelicula> pelis, String titulo) {
        Integer i=0;
        for (Pelicula peli : pelis) {
            //si el titulo de la peli no coicide, la pelicula se elimina de la lista
            if(!(peli.getTituloP().equalsIgnoreCase(titulo))){
                pelis.remove(i);
            }
            i++;
        }
        return pelis;
    }
    /**
     * Metodo para filtrar peliculas por director
     * @param pelis peliculas a filtrar 
     * @param director director por el que se va a filtrar
     * @return ArrayList de peliculas filtradas
     */
    private ArrayList<Pelicula> filtrarDirector(ArrayList<Pelicula> pelis, String director) {
        Integer i=0;
        String dir=null;
        for (Pelicula peli : pelis) {
            dir=peli.getDirector();
            if(!(dir.equalsIgnoreCase(director))){
                pelis.remove(i);
            }
            i++;
        }
        return pelis;
    }
    /**
     * Metodo para filtrar peliculas por un genero
     * @param pelis ArrayList de peliculas que van a ser filtradas
     * @param genero Genero por el que se van a flitrar
     * @return ArrayList de palisculas filtradas
     */
    private ArrayList<Pelicula> filtrarGenero(ArrayList<Pelicula> pelis, String genero) {
        Integer i=0;
        Boolean ok=false;
        ArrayList <Genero> gens;
        for (Pelicula peli : pelis) {
            gens= new ArrayList<>();
            //cargamos los generos de la pelicula
            gens=peli.getGeneros(gens);
            for(int j=0;j<gens.size();j++){
                //si se encuentra el genero, se cambia el valor de el booleano a true
                //y sale del bucle
                if(gens.get(j).getDescrip_gen().equalsIgnoreCase(genero)){
                    ok=true;
                    break;
                }
            }
            //si el valor del booleano es false, la pelicula no tiene el genero y 
            //se borrara del ArrayList
            if(!ok){
                pelis.remove(i);
            }
            //se vuelve a poner el valor del booleano a false.
            ok=false;
            i++;
        }
        return pelis;
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
        ArrayList <Genero> gens= man.cargarGeneros();
        //se insertan los nombres de los generos en el comboBox
        for(int i=0;i<gens.size();i++){
            boxGenero.addItem(gens.get(i).getDescrip_gen());
        }
    }
    /**
     * Metodo para recoger todos los directores almacenados en la BD
     */
    private void cargarDirectores() {
        //se recogen todos los directores almacenados en la BD
        ArrayList <Director> dires= man.cargarDirectores();
        //se insertan los nombres y apellidos de los directores en el comboBox
        for(int i=0;i<dires.size();i++){
            boxGenero.addItem(dires.get(i).getNombre()+" "+dires.get(i).getApell());
        }
    }
}
