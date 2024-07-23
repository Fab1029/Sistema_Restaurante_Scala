package Vistas.Usuario;

import javax.swing.*;

public class Ui_AcercaDe extends JFrame{
    private JPanel grbPrincipal;
    private JLabel lblNombre;

    public Ui_AcercaDe(){initPanelPrincipal();}

    private void initPanelPrincipal(){
        setTitle("Acerca de");
        setVisible(true);
        setResizable(false);
        setSize(1200, 500);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
