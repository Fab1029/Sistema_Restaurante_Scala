package Controladores.Restaurante

import Modelos.Restaurante.{Carta, Restaurante}
import Vistas.Restaurante.Ui_Carta

import java.awt.event.{ActionEvent, ActionListener, ItemEvent, ItemListener, WindowAdapter, WindowEvent}
import java.text.SimpleDateFormat

import java.util.Date
import javax.swing.{JFrame, JOptionPane}
import javax.swing.event.{ChangeEvent, ChangeListener}
import scala.collection.mutable

class ControladorCarta (private val seccion:Int, private val controladorAnterior:JFrame) extends ActionListener with ChangeListener with ItemListener{
  private var restaurante = Restaurante
  private val ui_Carta:Ui_Carta = new Ui_Carta()
  private var productos:mutable.Set[String] = mutable.Set[String]()

  verificarPestana()
  initSeccion(seccion)()
  initActions()

  private def initActions(): Unit = {
    ui_Carta.tbCarta.addChangeListener(this)
    ui_Carta.cmbNombreModificar.addItemListener(this)
    ui_Carta.btnEliminarCarta.addActionListener(this)
    ui_Carta.btnIngresarCarta.addActionListener(this)
    ui_Carta.btnGuardarCambios.addActionListener(this)
    ui_Carta.btnAgregarProductoIngresar.addActionListener(this)
    ui_Carta.btnEliminarProductoIngresar.addActionListener(this)
    ui_Carta.btnAgregarProductoModificar.addActionListener(this)
    ui_Carta.btnEliminarProductoModificar.addActionListener(this)
  }

  private def eliminarProductoAction(accion:Int): () => Unit = {
    accion match {
      //Eliminar producto ingresar
      case 1 => () => {
        if(productos.nonEmpty){
          productos -= (ui_Carta.cmbEliminarProductoIngresar.getSelectedItem.toString)
          productoAction()
          dialogoInformacion("Éxito", "Se ha borrado producto de carta")
        }
        else dialogoInformacion("Alerta", "Ingrese productos previamente")
      }
      //Eliminar producto modificar
      case 2 => () => {
        if(restaurante.cartas(ui_Carta.cmbNombreModificar.getSelectedItem.toString).productos.nonEmpty){
          restaurante.cartas(ui_Carta.cmbNombreModificar.getSelectedItem.toString).productos -= (ui_Carta.cmbEliminarProductoModificar.getSelectedItem.toString)
          nombreModificarAction()
          dialogoInformacion("Éxito", "Se ha borrado producto de carta")
        }
        else dialogoInformacion("Alerta", "Ingrese productos previamente")
      }
    }

  }

  private def agregarProductoAction(accion:Int): () => Unit = {
    accion match {
      //Agregar producto ingresar
      case 1 => () => {
        productos += (ui_Carta.cmbProductosIngresar.getSelectedItem.toString)
        productoAction()
        dialogoInformacion("Éxito", "Se ha agregado el producto exitosamente")
      }
      //Agregar producto modificar
      case 2 => () => {
        restaurante.cartas(ui_Carta.cmbNombreModificar.getSelectedItem.toString).productos += (ui_Carta.cmbProductosModificar.getSelectedItem.toString)
        nombreModificarAction()
        dialogoInformacion("Éxito", "Se ha agregado el producto exitosamente")
      }
    }
  }

  private def eliminarCartaAction(): Unit = {
    restaurante.cartas -= (ui_Carta.cmbNombreEliminar.getSelectedItem.toString)
    dialogoInformacion("Éxito", "Se ha eliminado la carta")
    initSeccion(2)()
    verificarPestana()
  }

  private def ingresarCartaAction(): Unit = {
    if(ui_Carta.dtpFechaValidezIngresar != null && ui_Carta.dtpFechaValidezIngresar.getDate.compareTo(new Date()) >= 0 && ui_Carta.txtNombreIngresar.getText.nonEmpty && productos.nonEmpty){
      restaurante.cartas += (ui_Carta.txtNombreIngresar.getText -> new Carta(ui_Carta.txtNombreIngresar.getText, new SimpleDateFormat(ui_Carta.dtpFechaValidezIngresar.getDateFormatString).format(ui_Carta.dtpFechaValidezIngresar.getDate), mutable.Set(productos.toSeq: _*)))
      initSeccion(0)()
      verificarPestana()
      dialogoInformacion("Éxito", "Se ha agregado carta")
    }
    else dialogoInformacion("Alerta", "Llene todo los campos antes de ingresar")
  }

  private def modificarCartaAction(): Unit = {
    if(ui_Carta.dtpFechaValidezModificar != null && ui_Carta.dtpFechaValidezModificar.getDate.compareTo(new Date()) >= 0 && restaurante.cartas(ui_Carta.cmbNombreModificar.getSelectedItem.toString).productos.nonEmpty){
      restaurante.cartas(ui_Carta.cmbNombreModificar.getSelectedItem.toString).fechaValidez = new SimpleDateFormat(ui_Carta.dtpFechaValidezModificar.getDateFormatString).format(ui_Carta.dtpFechaValidezModificar.getDate)
      restaurante.cartas(ui_Carta.cmbNombreModificar.getSelectedItem.toString).productos = mutable.Set(restaurante.cartas(ui_Carta.cmbNombreModificar.getSelectedItem.toString).productos.toSeq: _*)
      initSeccion(3)()
      dialogoInformacion("Éxito", "Se ha modificado la carta")
    }
    else dialogoInformacion("Alerta", "Llene todo los campos antes de modificar")
  }

  private def listarCartasAction(): Unit = {
    ui_Carta.jgdCartas.setRowCount(0)
    restaurante.cartas.values.foreach(carta => ui_Carta.jgdCartas.addRow(Array[Object](carta.nombre, carta.fechaValidez)))

  }

  private def productoAction(): Unit = {
    ui_Carta.cmbEliminarProductoIngresar.removeAllItems()
    productos.foreach(producto => ui_Carta.cmbEliminarProductoIngresar.addItem(producto))
  }

  private def nombreModificarAction(): Unit = {
    try{
      ui_Carta.cmbEliminarProductoModificar.removeAllItems()
      restaurante.cartas(ui_Carta.cmbNombreModificar.getSelectedItem.toString).productos.foreach(producto => ui_Carta.cmbEliminarProductoModificar.addItem(producto))
      ui_Carta.dtpFechaValidezModificar.setDate(new SimpleDateFormat(ui_Carta.dtpFechaValidezModificar.getDateFormatString).parse(restaurante.cartas(ui_Carta.cmbNombreModificar.getSelectedItem.toString).fechaValidez))

    }catch {
      case _: Exception =>
    }
  }

  private def initSeccion(seccion:Int): () => Unit ={
    seccion match {
      //Ingresar
      case 0 => () => {
        ui_Carta.tbCarta.setSelectedIndex(0)
        productos.clear()
        ui_Carta.txtNombreIngresar.setText("")
        ui_Carta.cmbProductosIngresar.removeAllItems()
        ui_Carta.cmbEliminarProductoIngresar.removeAllItems()
        ui_Carta.dtpFechaValidezIngresar.setDate(null)
        ui_Carta.dtpFechaValidezIngresar.setMinSelectableDate(new Date())
        restaurante.productos.keys.foreach(producto => ui_Carta.cmbProductosIngresar.addItem(producto))
      }
      //Modificar
      case 1 => () => {
        ui_Carta.tbCarta.setSelectedIndex(1)
        ui_Carta.cmbNombreModificar.removeAllItems()
        ui_Carta.cmbProductosModificar.removeAllItems()
        ui_Carta.cmbEliminarProductoModificar.removeAllItems()
        restaurante.cartas.keys.foreach(carta => ui_Carta.cmbNombreModificar.addItem(carta))
        restaurante.productos.keys.foreach(producto => ui_Carta.cmbProductosModificar.addItem(producto))
        nombreModificarAction()
      }
      //Eliminar
      case 2 => () => {
        ui_Carta.tbCarta.setSelectedIndex(2)
        ui_Carta.cmbNombreEliminar.removeAllItems()
        restaurante.cartas.keys.foreach(carta => ui_Carta.cmbNombreEliminar.addItem(carta))
      }
      //Listar
      case 3 => () => {
        ui_Carta.tbCarta.setSelectedIndex(3)
        listarCartasAction()
      }

    }
  }

  private def verificarPestana():Unit ={
    if(restaurante.cartas.nonEmpty) (1 until 4).foreach(indice => ui_Carta.tbCarta.setEnabledAt(indice, true))
    else{
      ui_Carta.tbCarta.setSelectedIndex(0)
      (1 until 4).foreach(indice => ui_Carta.tbCarta.setEnabledAt(indice, false))
    }
  }

  private def dialogoInformacion(titulo:String, cadena:String):Unit = {
    JOptionPane.showMessageDialog(null, cadena, titulo, JOptionPane.INFORMATION_MESSAGE)
  }
  //Cerrar ventana
  ui_Carta.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      controladorAnterior.setVisible(true)
      ui_Carta.dispose()
    }
  })


  override def actionPerformed(e: ActionEvent): Unit = {
    if(e.getSource == ui_Carta.btnIngresarCarta) ingresarCartaAction()
    else if(e.getSource == ui_Carta.btnEliminarCarta) eliminarCartaAction()
    else if(e.getSource == ui_Carta.btnGuardarCambios) modificarCartaAction()
    else if(e.getSource == ui_Carta.btnAgregarProductoIngresar) agregarProductoAction(1)()
    else if(e.getSource == ui_Carta.btnEliminarProductoIngresar) eliminarProductoAction(1)()
    else if(e.getSource == ui_Carta.btnAgregarProductoModificar) agregarProductoAction(2)()
    else if(e.getSource == ui_Carta.btnEliminarProductoModificar) eliminarProductoAction(2)()
  }

  override def stateChanged(e: ChangeEvent): Unit = {
    if(e.getSource == ui_Carta.tbCarta) initSeccion(ui_Carta.tbCarta.getSelectedIndex)()
  }

  override def itemStateChanged(e: ItemEvent): Unit = {
    if(e.getSource == ui_Carta.cmbNombreModificar) nombreModificarAction()
  }
}
