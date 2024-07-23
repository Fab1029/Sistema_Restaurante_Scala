package Vistas.Comida;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ui_Producto extends JFrame{
    private JPanel grbPrincipal;
    public JTabbedPane tbProducto;
    private JPanel grbIngresar;
    private JPanel grbModificar;
    private JPanel grbEliminar;
    private JPanel grbListar;
    private JLabel lblNombreIngresar;
    public JTextField txtNombreIngresar;
    public JSpinner dsbPrecioIngresar;
    public JComboBox<String> cmbIngredienteIngresar;
    public JButton btnAgregarIngredienteIngresar;
    private JLabel lblDescripcionIngresar;
    public JTextField txtDescripcionIngresar;
    private JLabel lblPrecioIngresar;
    private JLabel lblAgregarIngredienteIngresar;
    public JButton btnIngresarProducto;
    private JLabel lblNombreModificar;
    public JComboBox<String> cmbNombreModificar;
    private JLabel lblDescripcionModificar;
    public JTextField txtDescripcionModificar;
    private JLabel lblPrecioModificar;
    public JSpinner dsbPrecioModificar;
    private JLabel lblAgregarIngredienteModificar;
    private JLabel lblIngredienteModificar;
    public JComboBox<String> cmbAgregarIngredienteModificar;
    public JButton btnAgregarIngredienteModificar;
    public JComboBox<String > cmbIngredienteModificar;
    public JButton btnEliminarIngrediente;
    public JButton btnGuardarCambios;
    private JLabel lblNombreEliminar;
    public JComboBox<String> cmbNombreEliminar;
    public JButton btnEliminarProducto;
    private JTable productos;
    public DefaultTableModel jgdProductos;

    public Ui_Producto(){
        initPanelPrincipal();
        initTable();
        initSpinner();

    }

    private void initPanelPrincipal(){
        setTitle("Producto");
        setVisible(true);
        setResizable(false);
        setSize(1200, 500);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initTable() {
        jgdProductos = new DefaultTableModel(new String[]{"Nombre", "Descripción", "Precio"}, 0);
        productos.setModel(jgdProductos);
    }

    private void initSpinner(){
        dsbPrecioIngresar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 0.1));
        dsbPrecioModificar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 0.1));
    }
}
