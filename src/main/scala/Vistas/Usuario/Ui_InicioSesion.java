package Vistas.Usuario;

import javax.swing.*;

public class Ui_InicioSesion extends JFrame{
    private JLabel lblClave;
    public JButton btnEntrar;
    private JLabel lblUsuario;
    private JPanel grbPrincipal;
    private JLabel lblInicioSesion;
    public JTextField txtUsuario;
    public JPasswordField txtClave;

    public Ui_InicioSesion(){
        initPanelPrincipal();
    }

    private void initPanelPrincipal(){
        setTitle("Inicio sesión");
        setVisible(true);
        setResizable(false);
        setSize(600, 400);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
