package view.administrator;

import model.Administrador;
import model.Pelicula;
import model.Serie;
import control.Manager;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import model.Genero;

public class ResultadoBuscarPeliculas extends JDialog implements ActionListener{

	private JPanel contentPane;
        private JButton btnVolver;
        private JButton btnVerDatos;
        private JButton btnImagen;
        private JComboBox comboBox;
        private JTextArea textArea;
        private Manager man;
        private ArrayList <Pelicula> resultados;
        private Administrador admin;
        private int baseDeDatos;
	/**
	 * Create the frame.
	 */
	public ResultadoBuscarPeliculas(ArrayList <Pelicula> resultados, Administrador admin, int baseDeDatos)  {
                this.resultados=resultados;
                this.baseDeDatos= baseDeDatos;
                this.admin=admin;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 842, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(271, 11, 297, 20);
		contentPane.add(comboBox);
                btnVerDatos = new JButton("Ver Datos ");
		btnVerDatos.setBounds(578, 10, 137, 23);
		contentPane.add(btnVerDatos);
		
		btnImagen = new JButton("Imagen");
		btnImagen.setEnabled(false);
		btnImagen.setIcon(null);
		btnImagen.setBounds(10, 11, 251, 346);
		contentPane.add(btnImagen);
		
		textArea = new JTextArea();
		textArea.setBounds(271, 44, 545, 313);
                textArea.setEditable(false);
		contentPane.add(textArea);
		
		btnVolver = new JButton("Volver");
		btnVolver.setBounds(720, 10, 96, 23);
		contentPane.add(btnVolver);
                
                btnVolver.addActionListener(this);
                btnVerDatos.addActionListener(this);
                
                man= new Manager(baseDeDatos);
                cargarDatos();
                
	}
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnVolver)){
            volver();
        }else if(e.getSource().equals(btnVerDatos)){
            //Comprobamos si se ha seleccionado algo en el comboBox
            if(comboBox.getSelectedIndex()!=-1){
                //Se ha seleccionado y se compriueba si es una serie o una palicula
                Boolean ok=seleccionadaEsSerie(comboBox.getSelectedIndex());
                Pelicula p;
                if(ok){
                    //Es una Serie
                    p= new Serie();
                }else{
                    //Es una Pelicula
                    p= new Pelicula();
                }
                //recogemos en una variable la seleccionada
                p=resultados.get(comboBox.getSelectedIndex());
                //Imprimimos los datos
                verDatos(p,ok);
                
            }else{
                //No se ha seleccionado nada 
                JOptionPane.showInternalConfirmDialog(null,
                    "Primero tienes que seleccionar una", "Selecciona una", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    /**
     * Metodo para ver si la seleccionada es una serie
     * @param indice posicion de la pelicula o serie sweleccionada en el ComboBox
     * @return true si es una serie, false si es una pelicula
     */
    private Boolean seleccionadaEsSerie(int indice){
        return man.esSerie(resultados.get(indice).getId_P());
    }
    /**
     * Metodo para imprimir en el TextArea los datos de una Pelicula o Serie
     * @param p Pelicula o Serie que hay que imprimir
     * @param ok Si es true es una Serie, si es false es una Plelicula
     */
    private void verDatos(Pelicula p, Boolean ok) {
        SimpleDateFormat dma= new SimpleDateFormat("dd/MM/yyyy");
        //Imprimimos un blanco en el TextArea, por si hubieran datos de otra pelicula escritos
        textArea.setText("");
        //Empezamos a imprimir los datos
        textArea.append(p.getTituloP()+"\n");
        textArea.append(dma.format(p.getFechaP())+"\n");
        //Si es una serie se imprimiran estos datos
        if(ok){
            textArea.append("Numero de capitulos: "+((Serie)p).getNumCap()+"\n");
            textArea.append("Estado: "+((Serie)p).getEstado()+"\n");
            //Si es estado es "finalizada" se imprimira la fecha de finalizacion
            if(((Serie)p).getEstado().equals("finalizada")) textArea.append("Fecha finalizacion: "+dma.format(((Serie)p).getFechaFin())+"\n");
        }
        textArea.append("\nSinopsis\n"+p.getDescriP()+"\n\n");
        textArea.append("Generos: \n");
        for (Genero gen : p.getGen()) {
            textArea.append("-"+gen.getDescrip_gen()+"\n\t");
        }
        textArea.append("Director: "+p.getDirector()+"\n");
        textArea.append("Pais: "+p.getPaisP()+"\n");
        textArea.append("Duracion: "+p.getDuracionP()+"min\n");
        textArea.append("Nota Prensa: "+p.getNotaPren()+"\tNota Usuarios: "+p.getNotaUsu()+"\n");
    }
    /**
     * Metodo que cierra esta ventana y vuelve a la ventana de BuscarPeliculas
     */
    private void volver() {
        this.dispose();
        BuscarPeliculas b= new BuscarPeliculas(admin, baseDeDatos);
        b.setVisible(true);
    }

    private void cargarDatos() {
        //Añadimos al comboBox los datos que hay en el ArrayList que esta ventana recibe
        for (Pelicula p : resultados) {
            comboBox.addItem(p.getTituloP());
        }
        //Añadir imagen 
    }
}
