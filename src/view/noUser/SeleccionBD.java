/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.noUser;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.noUser.Inicio_sesion;

/**
 *
 * @author Gigabyte
 */
public class SeleccionBD extends JFrame implements ActionListener {
    private final JPanel contentPane;
    private final JButton btnMysql;
    private final JButton btnHibernate;
    private final JButton btnOracle;
    private final JButton btnMongodb;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SeleccionBD frame = new SeleccionBD();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Create the frame.
     */
    public SeleccionBD() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 402, 240);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTextPane txtpnParaIniciarEl = new JTextPane();
        txtpnParaIniciarEl.setFont(new Font("Tahoma", Font.BOLD, 13));
        txtpnParaIniciarEl.setBackground(Color.LIGHT_GRAY);
        txtpnParaIniciarEl.setText("Para iniciar el programa, primero tienes que seleccionar el tipo de base de datos que quieres utilizar");
        txtpnParaIniciarEl.setBounds(10, 11, 369, 66);
        contentPane.add(txtpnParaIniciarEl);

        btnMysql = new JButton("MySQL");
        btnMysql.setBounds(10, 88, 174, 35);
        contentPane.add(btnMysql);

        btnHibernate = new JButton("Hibernate");
        btnHibernate.setBounds(10, 140, 174, 35);
        contentPane.add(btnHibernate);

        btnOracle = new JButton("Oracle");
        btnOracle.setBounds(205, 88, 174, 35);
        contentPane.add(btnOracle);

        btnMongodb = new JButton("MongoDB");
        btnMongodb.setBounds(205, 140, 174, 35);
        contentPane.add(btnMongodb);
        
        btnHibernate.addActionListener(this);
        btnMongodb.addActionListener(this);
        btnMysql.addActionListener(this);
        btnOracle.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int bd=0;
        if(e.getSource().equals(btnOracle)){
            bd=4;
        }else if(e.getSource().equals(btnMongodb)){
            bd=2;
        }else if(e.getSource().equals(btnMysql)){
            bd=1;
        }else if(e.getSource().equals(btnHibernate)){
            bd=3;
        }
        Inicio_sesion i= new Inicio_sesion(bd);
        i.setVisible(true);
        this.dispose();
    }

   
    
}
