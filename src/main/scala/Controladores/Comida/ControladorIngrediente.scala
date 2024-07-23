package Controladores.Comida

import Modelos.Comida.{InformacionComida, Ingrediente}
import Modelos.Restaurante.Restaurante
import Modelos.Usuario.GestionUsuarios
import Vistas.Comida.Ui_Ingrediente

import java.awt.event.{ActionEvent, ActionListener, ItemEvent, ItemListener, WindowAdapter, WindowEvent}
import javax.swing.{JFrame, JOptionPane}
import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.table.DefaultTableModel

class ControladorIngrediente (private val seccion:Int, private val controladorAnterior:JFrame) extends ActionListener with ChangeListener with ItemListener{
  private var restaurante = Restaurante
  private val ui_Ingrediente:Ui_Ingrediente = new Ui_Ingrediente()

  verificarPestana()
  initSeccion(seccion)()
  initActions()


  private def initActions(): Unit = {
    ui_Ingrediente.tbIngrediente.addChangeListener(this)
    ui_Ingrediente.cmbNombreModificar.addItemListener(this)
    ui_Ingrediente.btnGuardarCambios.addActionListener(this)
    ui_Ingrediente.btnEliminarIngrediente.addActionListener(this)
    ui_Ingrediente.btnIngresarIngrediente.addActionListener(this)

  }

  private def verificarPestana():Unit ={
    if(restaurante.ingredientes.nonEmpty) (1 until 4).foreach(indice => ui_Ingrediente.tbIngrediente.setEnabledAt(indice, true))
    else{
      ui_Ingrediente.tbIngrediente.setSelectedIndex(0)
      (1 until 4).foreach(indice => ui_Ingrediente.tbIngrediente.setEnabledAt(indice, false))
    }

  }

  private def nombreModificarAction(): Unit = {
    try {
      ui_Ingrediente.cmbTipoModificar.setSelectedIndex(InformacionComida.tiposIngredientes.indexOf(restaurante.ingredientes(ui_Ingrediente.cmbNombreModificar.getSelectedItem.toString).tipo))
      ui_Ingrediente.cmbMedidaModificar.setSelectedIndex(InformacionComida.medidaIngrediente.indexOf(restaurante.ingredientes(ui_Ingrediente.cmbNombreModificar.getSelectedItem.toString).medida))

    }catch {
      case _: Exception =>
    }
  }

  private def ingresarIngrediente():Unit ={
    if(ui_Ingrediente.txtNombreIngresar.getText.nonEmpty){
      restaurante.ingredientes += (ui_Ingrediente.txtNombreIngresar.getText -> new Ingrediente(ui_Ingrediente.txtNombreIngresar.getText, ui_Ingrediente.cmbTipoIngresar.getSelectedItem.toString, ui_Ingrediente.cmbMedidaIngresar.getSelectedItem.toString))
      initSeccion(0)()
      verificarPestana()
      dialogoInformacion("Éxito", "Ingreso de ingrediente exitoso")

    }
    else dialogoInformacion("Alerta", "Ingrese el nombre del ingrediente")
  }

  private def modificarIngredienteAction(): Unit = {
    restaurante.ingredientes(ui_Ingrediente.cmbNombreModificar.getSelectedItem.toString).tipo = ui_Ingrediente.cmbTipoModificar.getSelectedItem.toString
    restaurante.ingredientes(ui_Ingrediente.cmbNombreModificar.getSelectedItem.toString).medida = ui_Ingrediente.cmbMedidaModificar.getSelectedItem.toString
    initSeccion(1)()
    dialogoInformacion("Éxito", "Ingrediente modificado exitosamente")
  }

  private def eliminarIngredienteAction(): Unit = {
    restaurante.ingredientes -= (ui_Ingrediente.cmbNombreEliminar.getSelectedItem.toString)
    initSeccion(2)()
    verificarPestana()
    dialogoInformacion("Éxito", "Ingrediente eliminado exitosamente")
  }

  private def listarIngredientesAction(): Unit = {
    ui_Ingrediente.jgdIngredientes.setRowCount(0)
    restaurante.ingredientes.values.foreach(ingrediente => ui_Ingrediente.jgdIngredientes.addRow(Array[Object](ingrediente.nombre, ingrediente.tipo, ingrediente.medida)))
  }


  private def initSeccion(seccion:Int): () => Unit ={
    seccion match {
      //Ingresar
      case 0 => () => {
        ui_Ingrediente.tbIngrediente.setSelectedIndex(0)
        ui_Ingrediente.txtNombreIngresar.setText("")
        ui_Ingrediente.cmbTipoIngresar.removeAllItems()
        ui_Ingrediente.cmbMedidaIngresar.removeAllItems()
        InformacionComida.tiposIngredientes.foreach(tipo => ui_Ingrediente.cmbTipoIngresar.addItem(tipo))
        InformacionComida.medidaIngrediente.foreach(medida => ui_Ingrediente.cmbMedidaIngresar.addItem(medida))
      }
      //Modificar
      case 1 => () => {
        ui_Ingrediente.tbIngrediente.setSelectedIndex(1)
        ui_Ingrediente.cmbTipoModificar.removeAllItems()
        ui_Ingrediente.cmbMedidaModificar.removeAllItems()
        ui_Ingrediente.cmbNombreModificar.removeAllItems()

        InformacionComida.tiposIngredientes.foreach(tipo => ui_Ingrediente.cmbTipoModificar.addItem(tipo))
        InformacionComida.medidaIngrediente.foreach(medida => ui_Ingrediente.cmbMedidaModificar.addItem(medida))
        restaurante.ingredientes.keys.foreach(ingrediente => ui_Ingrediente.cmbNombreModificar.addItem(ingrediente))
        nombreModificarAction()
      }

      //Eliminar
      case 2 => () => {
        ui_Ingrediente.tbIngrediente.setSelectedIndex(2)
        ui_Ingrediente.cmbNombreEliminar.removeAllItems()
        restaurante.ingredientes.keys.foreach(ingrediente => ui_Ingrediente.cmbNombreEliminar.addItem(ingrediente))

      }
      //Listar
      case 3 => () => {
        ui_Ingrediente.tbIngrediente.setSelectedIndex(3)
        listarIngredientesAction()


      }


    }
  }


  private def dialogoInformacion(titulo:String, cadena:String):Unit = {
    JOptionPane.showMessageDialog(null, cadena, titulo, JOptionPane.INFORMATION_MESSAGE)
  }

  override def stateChanged(e: ChangeEvent): Unit = {
    if(e.getSource == ui_Ingrediente.tbIngrediente) initSeccion(ui_Ingrediente.tbIngrediente.getSelectedIndex)()
  }
  override def actionPerformed(e: ActionEvent): Unit = {
    if(e.getSource == ui_Ingrediente.btnIngresarIngrediente) ingresarIngrediente()
    else if(e.getSource == ui_Ingrediente.btnGuardarCambios) modificarIngredienteAction()
    else if(e.getSource == ui_Ingrediente.btnEliminarIngrediente) eliminarIngredienteAction()
  }

  override def itemStateChanged(e: ItemEvent): Unit = {
    if(e.getSource == ui_Ingrediente.cmbNombreModificar) nombreModificarAction()
  }



  //Cerrar ventana
  ui_Ingrediente.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      controladorAnterior.setVisible(true)
      ui_Ingrediente.dispose()
    }
  })



}
