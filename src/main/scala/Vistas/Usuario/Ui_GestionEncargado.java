package Vistas.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ui_GestionEncargado extends JFrame{
    private JPanel grbPrincipal;
    public JTabbedPane tbEncargado;
    private JPanel grbIngresar;
    private JPanel grbModificar;
    private JPanel grbEliminar;
    private JPanel grbListar;
    public JTextField txtCedulaIngresar;
    public JTextField txtNombreIngresar;
    public JTextField txtDireccionIngresar;
    private JLabel lblCedulaIngresar;
    private JLabel lblNombreIngresar;
    private JLabel lblDireccionIngresar;
    private JLabel lblClaveIngresar;
    public JButton btnIngresarEncargado;
    public JButton btnLimpiarCampos;
    private JLabel lblCedulaModificar;
    public JPasswordField txtClaveIngresar;
    private JLabel lblNombreModificar;
    private JLabel lblClaveModificar;
    public JTextField txtCedulaModificar;
    public JTextField txtNombreModifcar;
    public JPasswordField txtClaveModificar;
    public JButton btnGuardarCambios;
    private JLabel lblDireccionModificar;
    public JTextField txtDireccionModificar;
    private JLabel lblCedulaEliminar;
    public JTextField txtCedulaEliminar;
    private JLabel lblNombreEliminar;
    public JTextField txtNombreEliminar;
    private JLabel lblDireccionEliminar;
    public JTextField txtDireccionEliminar;
    private JTable encargados;
    public JButton btnEliminar;
    public DefaultTableModel jgdEncargados;

    public Ui_GestionEncargado(){
        initPanelPrincipal();
        initTable();
    }

    private void initPanelPrincipal(){
        setTitle("Gestión encargados");
        setVisible(true);
        setResizable(false);
        setSize(1200, 500);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initTable() {
        jgdEncargados = new DefaultTableModel(new String[]{"Cédula", "Nombre", "Dirección"}, 0);
        encargados.setModel(jgdEncargados);
    }

}
