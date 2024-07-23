package Vistas.Restaurante;

import javax.swing.*;

public class Ui_InformacionRestaurante extends JFrame{
    private JPanel grbPrincipal;
    private JLabel lblNombre;
    public JTextField txtNombreRestaurante;
    public JButton btnGuardarCambios;
    private JLabel lblRuc;
    private JLabel lblRazonSocial;
    private JLabel lblDireccion;
    private JLabel lblEmail;
    private JLabel lblTelefono;
    public JTextField txtRuc;
    public JTextField txtRazonSocial;
    public JTextField txtDireccion;
    public JTextField txtEmail;
    public JTextField txtTelefono;

    public Ui_InformacionRestaurante(){initPanelPrincipal();}
    private void initPanelPrincipal(){
        setTitle("Información restaurante");
        setVisible(true);
        setResizable(false);
        setSize(1200, 500);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}
