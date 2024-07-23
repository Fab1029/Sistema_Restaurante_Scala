package Controladores.Comida

import Modelos.Comida.Producto
import Modelos.Restaurante.Restaurante
import Vistas.Comida.Ui_Producto

import java.awt.event.{ActionEvent, ActionListener, ItemEvent, ItemListener, WindowAdapter, WindowEvent}
import javax.swing.{JFrame, JOptionPane}
import javax.swing.event.{ChangeEvent, ChangeListener}
import scala.collection.mutable

class ControladorProducto (private val seccion:Int, private val controladorAnterior:JFrame) extends ActionListener with ChangeListener with ItemListener{
  private var restaurante = Restaurante
  private val ui_Producto:Ui_Producto = new Ui_Producto()
  private var ingredientes:mutable.Set[String] = mutable.Set[String]()

  verificarPestana()
  initSeccion(seccion)()
  initActions()


  private def initActions(): Unit = {
    ui_Producto.tbProducto.addChangeListener(this)
    ui_Producto.cmbNombreModificar.addItemListener(this)
    ui_Producto.btnGuardarCambios.addActionListener(this)
    ui_Producto.btnIngresarProducto.addActionListener(this)
    ui_Producto.btnEliminarProducto.addActionListener(this)
    ui_Producto.btnEliminarIngrediente.addActionListener(this)
    ui_Producto.btnAgregarIngredienteIngresar.addActionListener(this)
    ui_Producto.btnAgregarIngredienteModificar.addActionListener(this)

  }

  private def verificarPestana():Unit ={
    if(restaurante.productos.nonEmpty) (1 until 4).foreach(indice => ui_Producto.tbProducto.setEnabledAt(indice, true))
    else{
      ui_Producto.tbProducto.setSelectedIndex(0)
      (1 until 4).foreach(indice =>ui_Producto.tbProducto.setEnabledAt(indice, false))
    }
  }

  private def nombreModificarAction(): Unit = {
    try {
      ui_Producto.cmbIngredienteModificar.removeAllItems()
      ui_Producto.dsbPrecioModificar.setValue(restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).precio)
      ui_Producto.txtDescripcionModificar.setText(restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).descripcion)
      restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).ingredientes.foreach(ingrediente => ui_Producto.cmbIngredienteModificar.addItem(ingrediente))

    }catch {
      case _: Exception =>
    }
  }

  private def eliminarIngredienteAction(): Unit = {
    restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).ingredientes -= (ui_Producto.cmbIngredienteModificar.getSelectedItem.toString)
    ui_Producto.cmbIngredienteModificar.removeAllItems()
    restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).ingredientes.foreach(ingrediente => ui_Producto.cmbIngredienteModificar.addItem(ingrediente))
    dialogoInformacion("Éxito", "Eliminación exitosa")

  }
  private def ingresarProductoAction():Unit ={
    if(ui_Producto.txtNombreIngresar.getText.nonEmpty && ui_Producto.txtDescripcionIngresar.getText.nonEmpty && ingredientes.nonEmpty){
      restaurante.productos += (ui_Producto.txtNombreIngresar.getText -> new Producto(ui_Producto.txtNombreIngresar.getText, ui_Producto.txtDescripcionIngresar.getText, ui_Producto.dsbPrecioIngresar.getValue.asInstanceOf[Double].toFloat, mutable.Set(ingredientes.toSeq: _*)))

      initSeccion(0)()
      verificarPestana()
      dialogoInformacion("Éxito", "Producto ingresado exitosamente")

    }
    else dialogoInformacion("Alerta", "Ingrese todos los campos")
  }

  private def modificarProductoAction(): Unit = {
    if(ui_Producto.txtDescripcionModificar.getText.nonEmpty && restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).ingredientes.nonEmpty){
      restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).descripcion = ui_Producto.txtDescripcionModificar.getText
      restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).precio = ui_Producto.dsbPrecioModificar.getValue.asInstanceOf[Float]
      restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).ingredientes = restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).ingredientes
      initSeccion(3)()
      dialogoInformacion("Éxito", "Cambios aplicados exitosamente")
    }
    else dialogoInformacion("Alerta", "Ingrese todos los campos")
  }

  private def eliminarProductoAction(): Unit = {
    restaurante.productos -= (ui_Producto.cmbNombreEliminar.getSelectedItem.toString)
    initSeccion(2)()
    verificarPestana()
    dialogoInformacion("Éxito", "Producto eliminado")
  }

  private def listarProductosAction(): Unit = {
    ui_Producto.jgdProductos.setRowCount(0)
    restaurante.productos.values.foreach(producto => ui_Producto.jgdProductos.addRow(Array[Object](producto.nombre, producto.descripcion, producto.precio.toString)))
  }


  private def initSeccion(seccion:Int): () => Unit ={
    seccion match {
      //Ingresar
      case 0 => () => {
        ui_Producto.tbProducto.setSelectedIndex(0)
        ingredientes.clear()
        ui_Producto.txtNombreIngresar.setText("")
        ui_Producto.dsbPrecioIngresar.setValue(0)
        ui_Producto.txtDescripcionIngresar.setText("")
        ui_Producto.cmbIngredienteIngresar.removeAllItems()
        restaurante.ingredientes.keys.foreach(ingrediente => ui_Producto.cmbIngredienteIngresar.addItem(ingrediente))

       }
      //Modificar
      case 1 => () => {
        ui_Producto.tbProducto.setSelectedIndex(1)
        ui_Producto.cmbNombreModificar.removeAllItems()
        ui_Producto.cmbAgregarIngredienteModificar.removeAllItems()
        restaurante.productos.keys.foreach(producto => ui_Producto.cmbNombreModificar.addItem(producto))
        nombreModificarAction()
        restaurante.ingredientes.keys.foreach(ingrediente => ui_Producto.cmbAgregarIngredienteModificar.addItem(ingrediente))

      }
      //Eliminar
      case 2 => () => {
        ui_Producto.tbProducto.setSelectedIndex(2)
        ui_Producto.cmbNombreEliminar.removeAllItems()
        restaurante.productos.keys.foreach(producto => ui_Producto.cmbNombreEliminar.addItem(producto))

      }
      //Listar
      case 3 => () => {
        ui_Producto.tbProducto.setSelectedIndex(3)
        listarProductosAction()

      }

    }
  }

  private def agregarIngredienteAction(accion: Int): () => Unit = {

    accion match {
      //Agregar ingrediente ingresar
      case 1 => () => {
        ingredientes += (ui_Producto.cmbIngredienteIngresar.getSelectedItem.toString)
        dialogoInformacion("Éxito", "Ingreso exitoso")
      }
      //Agregar ingrediente modificar
      case 2 => () =>{
        restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).ingredientes += (ui_Producto.cmbAgregarIngredienteModificar.getSelectedItem.toString)
        ui_Producto.cmbIngredienteModificar.removeAllItems()
        restaurante.productos(ui_Producto.cmbNombreModificar.getSelectedItem.toString).ingredientes.foreach(ingrediente => ui_Producto.cmbIngredienteModificar.addItem(ingrediente))
        dialogoInformacion("Éxito", "Ingreso exitoso")

      }
    }
  }

  private def dialogoInformacion(titulo:String, cadena:String):Unit = {
    JOptionPane.showMessageDialog(null, cadena, titulo, JOptionPane.INFORMATION_MESSAGE)
  }

  override def stateChanged(e: ChangeEvent): Unit = {
    if(e.getSource == ui_Producto.tbProducto) initSeccion(ui_Producto.tbProducto.getSelectedIndex)()
  }
  override def actionPerformed(e: ActionEvent): Unit = {
    if(e.getSource == ui_Producto.btnGuardarCambios) modificarProductoAction()
    else if(e.getSource == ui_Producto.btnIngresarProducto) ingresarProductoAction()
    else if(e.getSource == ui_Producto.btnEliminarProducto) eliminarProductoAction()
    else if(e.getSource == ui_Producto.btnEliminarIngrediente) eliminarIngredienteAction()
    else if(e.getSource == ui_Producto.btnAgregarIngredienteIngresar) agregarIngredienteAction(1)()
    else if(e.getSource == ui_Producto.btnAgregarIngredienteModificar) agregarIngredienteAction(2)()

  }

  override def itemStateChanged(e: ItemEvent): Unit = {
    if(e.getSource == ui_Producto.cmbNombreModificar) nombreModificarAction()

  }


  //Cerrar ventana
  ui_Producto.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      controladorAnterior.setVisible(true)
      ui_Producto.dispose()
    }
  })

}
