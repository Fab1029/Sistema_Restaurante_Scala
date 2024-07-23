package Vistas.Comida;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ui_Ingrediente extends JFrame{
    public JTabbedPane tbIngrediente;
    private JPanel grbPrincipal;
    private JPanel grbIngresar;
    private JPanel grbModificar;
    private JPanel grbEliminar;
    private JPanel grbListar;
    public JTextField txtNombreIngresar;
    private JLabel lblNombreIngresar;
    private JLabel lblTipoIngresar;
    private JLabel lblMedidaIngresar;
    public JComboBox<String> cmbTipoIngresar;
    public JComboBox<String> cmbMedidaIngresar;
    public JButton btnIngresarIngrediente;
    public JComboBox<String> cmbNombreModificar;
    public JComboBox<String> cmbTipoModificar;
    public JComboBox<String> cmbMedidaModificar;
    public JButton btnGuardarCambios;
    private JLabel lblNombreModificar;
    private JLabel lblTipoModificar;
    private JLabel lblMedidaModificar;
    public JComboBox<String> cmbNombreEliminar;
    public JButton btnEliminarIngrediente;
    private JLabel lblNombreEliminar;

    public DefaultTableModel jgdIngredientes;
    private JTable ingredientes;

    public Ui_Ingrediente(){
        initPanelPrincipal();
        initTable();
    }
    private void initPanelPrincipal(){
        setTitle("Ingrediente");

        setVisible(true);
        setResizable(false);
        setSize(1200, 500);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initTable() {
        jgdIngredientes = new DefaultTableModel(new String[]{"Nombre", "Tipo", "Medida"}, 0);
        ingredientes.setModel(jgdIngredientes);
    }

}
