package Controladores.Restaurante

import Modelos.Restaurante.{Carta, Reserva, Restaurante}
import Vistas.Restaurante.Ui_Reserva

import java.awt.event.{ActionEvent, ActionListener, ItemEvent, ItemListener, WindowAdapter, WindowEvent}
import java.beans.{PropertyChangeEvent, PropertyChangeListener}
import java.text.SimpleDateFormat
import java.util.Date
import javax.swing.{JFrame, JOptionPane}
import javax.swing.event.{ChangeEvent, ChangeListener}
import scala.collection.mutable
import scala.util.Random

class ControladorReserva (private val seccion:Int, private val controladorAnterior:JFrame) extends ActionListener with ChangeListener with ItemListener {

  private var restaurante = Restaurante
  private val ui_Reserva:Ui_Reserva = new Ui_Reserva()
  private var codigoReserva:String = obtenerCodigoReserva()
  private var pedido:mutable.TreeMap[String, Int] = mutable.TreeMap[String, Int]()

  verificarPestana()
  initSeccion(seccion)()
  initAction()
  addDateListeners()


  private def initAction(): Unit = {
    ui_Reserva.cmbPedidoIngresar.addItemListener(this)
    ui_Reserva.cmbPedidoModificar.addItemListener(this)
    ui_Reserva.cmbNumeroReservaModificar.addItemListener(this)
    ui_Reserva.cmbAgregarProductosIngresar.addItemListener(this)
    ui_Reserva.cmbAgregarProductoModificar.addItemListener(this)

    ui_Reserva.tbReserva.addChangeListener(this)
    ui_Reserva.btnReservar.addActionListener(this)
    ui_Reserva.btnGuardarCambios.addActionListener(this)
    ui_Reserva.btnEliminarReserva.addActionListener(this)
    ui_Reserva.btnEliminarPedidoIngresar.addActionListener(this)
    ui_Reserva.btnAgregarProductoIngresar.addActionListener(this)
    ui_Reserva.btnEliminarPedidoModificar.addActionListener(this)
    ui_Reserva.btnAgregarProductoModificar.addActionListener(this)
    ui_Reserva.btnGuardarCambiosPedidoIngresar.addActionListener(this)
    ui_Reserva.btnGuardarCambiosPedidoModificar.addActionListener(this)
  }


  private def agregarProductoAPedidoAction(accion: Int): () => Unit ={
    accion match {
      //Ingresar
      case 1 => () => {
        if(ui_Reserva.cmbAgregarProductosIngresar.getItemCount > 0){
          pedido += (ui_Reserva.cmbAgregarProductosIngresar.getSelectedItem.toString -> ui_Reserva.sbCantidadProductoIngresar.getValue.asInstanceOf[Int])
          ui_Reserva.cmbPedidoIngresar.removeAllItems()
          ui_Reserva.sbCantidadProductoIngresar.setValue(0)
          pedido.keys.foreach(producto => ui_Reserva.cmbPedidoIngresar.addItem(producto))
          ui_Reserva.sbCantidadPedidoIngresar.setValue(pedido(ui_Reserva.cmbPedidoIngresar.getSelectedItem.toString))
          dialogoInformacion("Éxito", "Se agregó producto a pedido")
        }
        else dialogoInformacion("Alerta", "No se agregó producto a pedido")
      }
      //Modificar
      case 2 => () => {
        if(ui_Reserva.cmbAgregarProductoModificar.getItemCount > 0){
          pedido += (ui_Reserva.cmbAgregarProductoModificar.getSelectedItem.toString -> ui_Reserva.sbCantidadProductoModificar.getValue.asInstanceOf[Int])
          ui_Reserva.cmbPedidoModificar.removeAllItems()
          ui_Reserva.sbCantidadProductoModificar.setValue(0)
          pedido.keys.foreach(producto => ui_Reserva.cmbPedidoModificar.addItem(producto))
          ui_Reserva.sbCantidadPedidoModificar.setValue(pedido(ui_Reserva.cmbPedidoModificar.getSelectedItem.toString))
          dialogoInformacion("Éxito", "Se agregó producto a pedido")
        }
        else dialogoInformacion("Alerta", "No se agregó producto a pedido")
      }
    }
  }

  private def pedidoAction(accion:Int): () => Unit = {
    accion match {
      //Guardar cambios pedido ingresar
      case 1 => () => {
        if(ui_Reserva.cmbPedidoIngresar.getItemCount > 0){
          pedido += (ui_Reserva.cmbPedidoIngresar.getSelectedItem.toString -> ui_Reserva.sbCantidadPedidoIngresar.getValue.asInstanceOf[Int])
          ui_Reserva.cmbPedidoIngresar.removeAllItems()
          pedido.keys.foreach(producto => ui_Reserva.cmbPedidoIngresar.addItem(producto))
          ui_Reserva.sbCantidadPedidoIngresar.setValue(pedido(ui_Reserva.cmbPedidoIngresar.getSelectedItem.toString))
          dialogoInformacion("Éxito", "Cambio guardado")
        }
        else dialogoInformacion("Alerta", "No se ha podido realizar el cambio")
      }
      //Eliminar pedido ingresar
      case 2 => () => {
        if(ui_Reserva.cmbPedidoIngresar.getItemCount > 0){
          pedido -= (ui_Reserva.cmbPedidoIngresar.getSelectedItem.toString)
          ui_Reserva.cmbPedidoIngresar.removeAllItems()
          pedido.keys.foreach(producto => ui_Reserva.cmbPedidoIngresar.addItem(producto))
          ui_Reserva.sbCantidadPedidoIngresar.setValue(pedido(ui_Reserva.cmbPedidoIngresar.getSelectedItem.toString))
          dialogoInformacion("Éxito", "Producto eliminado")
        }
        else dialogoInformacion("Alerta", "No se ha eliminado producto")
      }
      //Guardar cambios modificar
      case 3 => () => {
        if(ui_Reserva.cmbPedidoModificar.getItemCount > 0){
          pedido += (ui_Reserva.cmbPedidoModificar.getSelectedItem.toString -> ui_Reserva.sbCantidadPedidoModificar.getValue.asInstanceOf[Int])
          ui_Reserva.cmbPedidoModificar.removeAllItems()
          pedido.keys.foreach(producto => ui_Reserva.cmbPedidoModificar.addItem(producto))
          ui_Reserva.sbCantidadPedidoModificar.setValue(pedido(ui_Reserva.cmbPedidoModificar.getSelectedItem.toString))
          dialogoInformacion("Éxito", "Cambio guardado")
        }
        else dialogoInformacion("Alerta", "No se ha podido realizar el cambio")
      }
      //Eliminar pedido modificar
      case 4 => () => {
        if(ui_Reserva.cmbPedidoModificar.getItemCount > 0){
          pedido -= (ui_Reserva.cmbPedidoModificar.getSelectedItem.toString)
          ui_Reserva.cmbPedidoModificar.removeAllItems()
          pedido.keys.foreach(producto => ui_Reserva.cmbPedidoModificar.addItem(producto))

          if (pedido.nonEmpty) ui_Reserva.sbCantidadPedidoModificar.setValue(pedido(ui_Reserva.cmbPedidoModificar.getSelectedItem.toString))
          else ui_Reserva.sbCantidadPedidoModificar.setValue(0)

          dialogoInformacion("Éxito", "Producto eliminado")
        }
        else dialogoInformacion("Alerta", "No se ha eliminado producto")
      }
    }
  }

  private def cambioFechaAction(accion:Int): () => Unit = {
    accion match {
      //Cambio fecha ingresar
      case 1 => () => {
        pedido.clear()
        ui_Reserva.cmbTurnoIngresar.removeAllItems()
        ui_Reserva.cmbPedidoIngresar.removeAllItems()
        ui_Reserva.cmbAgregarProductosIngresar.removeAllItems()
        ui_Reserva.sbCantidadPedidoIngresar.setValue(0)
        ui_Reserva.sbNumeroComensalesIngresar.setValue(0)
        ui_Reserva.sbCantidadProductoIngresar.setValue(0)
        if(ui_Reserva.dtpFechaIngresar.getDate != null && restaurante.diasDisponibilidad.contains(new SimpleDateFormat(ui_Reserva.dtpFechaIngresar.getDateFormatString).format(ui_Reserva.dtpFechaIngresar.getDate)) && restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaIngresar.getDateFormatString).format(ui_Reserva.dtpFechaIngresar.getDate)).nonEmpty){
          restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaIngresar.getDateFormatString).format(ui_Reserva.dtpFechaIngresar.getDate))
            .keys
            .filter(disponibilidad => restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaIngresar.getDateFormatString).format(ui_Reserva.dtpFechaIngresar.getDate))(disponibilidad).numeroPlazas > 0)
            .map(disponibilidad => restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaIngresar.getDateFormatString).format(ui_Reserva.dtpFechaIngresar.getDate))(disponibilidad).turno)
            .foreach(turno => ui_Reserva.cmbTurnoIngresar.addItem(turno))
        }

        if(obtenerCartActual() != null) obtenerCartActual().productos.foreach(producto => ui_Reserva.cmbAgregarProductosIngresar.addItem(producto))

      }
      //Cambio fecha modificar
      case 2 => () => {
        pedido.clear()
        ui_Reserva.cmbTurnoModificar.removeAllItems()
        ui_Reserva.cmbPedidoModificar.removeAllItems()
        ui_Reserva.cmbAgregarProductoModificar.removeAllItems()
        ui_Reserva.sbCantidadPedidoModificar.setValue(0)
        ui_Reserva.sbNumeroComensalesModificar.setValue(0)
        ui_Reserva.sbCantidadProductoModificar.setValue(0)
        if(restaurante.diasDisponibilidad.contains(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate)) && restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate)).nonEmpty){
          restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate))
            .keys
            .filter(disponibilidad => restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate))(disponibilidad).numeroPlazas > 0)
            .map(disponibilidad => restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate))(disponibilidad).turno)
            .foreach(turno => ui_Reserva.cmbTurnoModificar.addItem(turno))
        }

        if(obtenerCartActual() != null) obtenerCartActual().productos.foreach(producto => ui_Reserva.cmbAgregarProductoModificar.addItem(producto))

      }
    }
  }

  private def eliminarReservaAction(): Unit = {
    if(restaurante.reservas.contains(ui_Reserva.cmbNumeroReservaEliminar.getSelectedItem.toString)){
      restaurante.reservas -= (ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString)
      initSeccion(2)()
      verificarPestana()
      dialogoInformacion("Éxito", "Se ha eliminado reserva")
    }
    else dialogoInformacion("Alerta", "No se ha podido eliminar reserva")
  }

  private def ingresarReservaAction(): Unit = {
    if(ui_Reserva.dtpFechaIngresar.getDate.compareTo(new Date()) >= 0 && ui_Reserva.cmbTurnoIngresar.getItemCount > 0 && pedido.nonEmpty){
      restaurante.reservas += (ui_Reserva.txtNumeroReservaIngresar.getText -> new Reserva(ui_Reserva.txtNumeroReservaIngresar.getText, ui_Reserva.cmbTurnoIngresar.getSelectedItem.toString, new SimpleDateFormat(ui_Reserva.dtpFechaIngresar.getDateFormatString).format(ui_Reserva.dtpFechaIngresar.getDate), ui_Reserva.sbNumeroComensalesIngresar.getValue.asInstanceOf[Int], mutable.TreeMap(pedido.toSeq: _*)))
      restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaIngresar.getDateFormatString).format(ui_Reserva.dtpFechaIngresar.getDate))(ui_Reserva.cmbTurnoIngresar.getSelectedItem.toString).numeroPlazas -= 1
      codigoReserva = obtenerCodigoReserva()
      initSeccion(0)()
      verificarPestana()
      dialogoInformacion("Éxito", "Ingreso correcto")
    }
    else dialogoInformacion("Alerta", "No se pudo realizar la reserva")
  }

  private def modificarReservaAction(): Unit = {
    if(ui_Reserva.dtpFechaModificar.getDate.compareTo(new Date()) >= 0 && ui_Reserva.cmbTurnoModificar.getItemCount > 0 && restaurante.reservas(ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString).pedidos.nonEmpty){
      restaurante.reservas(ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString).turno_(ui_Reserva.cmbTurnoModificar.getSelectedItem.toString)
      restaurante.reservas(ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString).fecha_(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate))
      restaurante.reservas(ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString).numeroComensales_(ui_Reserva.sbNumeroComensalesModificar.getValue.asInstanceOf[Int])
      restaurante.reservas(ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString).pedidos_(mutable.TreeMap(pedido.toSeq: _*))
      initSeccion(1)()
      verificarPestana()
      dialogoInformacion("Éxito", "Cambios guardados")
    }
    else dialogoInformacion("Alerta", "No se realizaron los cambios")

  }

  private def listarReservaAction(): Unit = {
    ui_Reserva.jgdReservas.setRowCount(0)
    restaurante.reservas.values.foreach(reserva => ui_Reserva.jgdReservas.addRow(Array[Object](reserva.numeroReserva, reserva.fecha, reserva.turno, reserva.numeroComensales.toString)))

  }

  private def generarCodigo(longitud: Int = 8): String = {
    val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    (1 to longitud).map(_ => caracteres(Random.nextInt(caracteres.length))).mkString
  }

  private def obtenerCodigoReserva(): String = {
    var codigo = generarCodigo()

    while (restaurante.reservas.contains(codigo)) codigo = generarCodigo()
    codigo
  }


  private def obtenerCartActual(): Carta = {
    val cartasValidas = restaurante.cartas.values
      .filter(carta => new SimpleDateFormat("yyyy-MM-dd").parse(carta.fechaValidez).compareTo(new Date()) >= 0)
      .toList
      .sortBy(carta => new SimpleDateFormat("yyyy-MM-dd").parse(carta.fechaValidez))(Ordering[Date].reverse)
    if (cartasValidas.nonEmpty) cartasValidas.head else null
  }

  private def initSeccion(seccion:Int): () => Unit ={
    seccion match {
      //Ingresar
      case 0 => () => {
        ui_Reserva.tbReserva.setSelectedIndex(0)
        ui_Reserva.txtNumeroReservaIngresar.setText(codigoReserva)
        ui_Reserva.dtpFechaIngresar.setMinSelectableDate(new Date())
        cambioFechaAction(1)()
      }
      //Modificar
      case 1 => () => {
        ui_Reserva.tbReserva.setSelectedIndex(1)
        ui_Reserva.cmbNumeroReservaModificar.removeAllItems()
        ui_Reserva.dtpFechaModificar.setMinSelectableDate(new Date())
        restaurante.reservas.keys
          .filter(reserva => new SimpleDateFormat("yyyy-MM-dd").parse(restaurante.reservas(reserva).fecha).compareTo(new Date()) >= 0)
          .foreach(reserva => ui_Reserva.cmbNumeroReservaModificar.addItem(reserva))
        numeroReservaAction(1)()
      }
      //Eliminar
      case 2 => () => {
        ui_Reserva.tbReserva.setSelectedIndex(2)
        numeroReservaAction(2)()

      }
      //Listar
      case 3 => () => {
        ui_Reserva.tbReserva.setSelectedIndex(3)
        listarReservaAction()

      }

    }
  }

  private def numeroReservaAction(accion:Int): () => Unit ={
    accion match {
      //Modificar
      case 1 => () => {
        try{
          ui_Reserva.cmbTurnoModificar.removeAllItems()
          ui_Reserva.cmbPedidoModificar.removeAllItems()
          ui_Reserva.cmbAgregarProductoModificar.removeAllItems()
          ui_Reserva.sbCantidadProductoModificar.setValue(0)
          pedido ++= mutable.TreeMap(restaurante.reservas(ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString).pedidos.toSeq: _*)
          pedido.keys.foreach(pedido => ui_Reserva.cmbPedidoModificar.addItem(pedido))
          ui_Reserva.sbNumeroComensalesModificar.setValue(restaurante.reservas(ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString).numeroComensales)
          ui_Reserva.dtpFechaModificar.setDate(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).parse(restaurante.reservas(ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString).fecha))

          ui_Reserva.sbCantidadPedidoModificar.setValue(pedido(ui_Reserva.cmbPedidoModificar.getSelectedItem.toString))

          if(restaurante.diasDisponibilidad.contains(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate)) && restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate)).nonEmpty){
            restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate))
              .keys
              .filter(disponibilidad => restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate))(disponibilidad).numeroPlazas > 0)
              .map(disponibilidad => restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Reserva.dtpFechaModificar.getDateFormatString).format(ui_Reserva.dtpFechaModificar.getDate))(disponibilidad).turno)
              .foreach(turno => ui_Reserva.cmbTurnoModificar.addItem(turno))
          }

          ui_Reserva.cmbTurnoModificar.setSelectedIndex(restaurante.turnos.indexOf(restaurante.reservas(ui_Reserva.cmbNumeroReservaModificar.getSelectedItem.toString).turno))
          if(obtenerCartActual() != null) obtenerCartActual().productos.foreach(producto => ui_Reserva.cmbAgregarProductoModificar.addItem(producto))
        }catch {
          case _:Exception =>
        }
      }

      case 2 => () => {
        ui_Reserva.cmbNumeroReservaEliminar.removeAllItems()
        restaurante.reservas.keys.foreach(reserva => ui_Reserva.cmbNumeroReservaEliminar.addItem(reserva))

      }
    }
  }

  private def verificarPestana():Unit ={
    if(restaurante.reservas.nonEmpty) (1 until 4).foreach(indice => ui_Reserva.tbReserva.setEnabledAt(indice, true))
    else{
      ui_Reserva.tbReserva.setSelectedIndex(0)
      (1 until 4).foreach(indice => ui_Reserva.tbReserva.setEnabledAt(indice, false))
    }
  }

  private def dialogoInformacion(titulo:String, cadena:String):Unit = {
    JOptionPane.showMessageDialog(null, cadena, titulo, JOptionPane.INFORMATION_MESSAGE)
  }

  //Cerrar ventana
  ui_Reserva.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      controladorAnterior.setVisible(true)
      ui_Reserva.dispose()
    }
  })


  override def actionPerformed(e: ActionEvent): Unit = {
    if(e.getSource == ui_Reserva.btnReservar) ingresarReservaAction()
    else if(e.getSource == ui_Reserva.btnGuardarCambios) modificarReservaAction()
    else if(e.getSource == ui_Reserva.btnEliminarReserva) eliminarReservaAction()
    else if(e.getSource == ui_Reserva.btnEliminarPedidoIngresar) pedidoAction(2)()
    else if(e.getSource == ui_Reserva.btnEliminarPedidoModificar) pedidoAction(4)()
    else if(e.getSource == ui_Reserva.btnGuardarCambiosPedidoIngresar) pedidoAction(1)()
    else if(e.getSource == ui_Reserva.btnGuardarCambiosPedidoModificar) pedidoAction(3)()
    else if(e.getSource == ui_Reserva.btnAgregarProductoIngresar) agregarProductoAPedidoAction(1)()
    else if(e.getSource == ui_Reserva.btnAgregarProductoModificar) agregarProductoAPedidoAction(2)()
  }

  override def stateChanged(e: ChangeEvent): Unit = {
    if(e.getSource == ui_Reserva.tbReserva) initSeccion(ui_Reserva.tbReserva.getSelectedIndex)()
  }

  override def itemStateChanged(e: ItemEvent): Unit = {
    if(e.getSource == ui_Reserva.cmbAgregarProductosIngresar) ui_Reserva.sbCantidadProductoIngresar.setValue(0)
    else if(e.getSource == ui_Reserva.cmbNumeroReservaModificar) numeroReservaAction(1)()
    else if(e.getSource == ui_Reserva.cmbAgregarProductoModificar) ui_Reserva.sbCantidadProductoModificar.setValue(0)
    else if(e.getSource == ui_Reserva.cmbPedidoIngresar){
      try {
        ui_Reserva.sbCantidadPedidoIngresar.setValue(pedido(ui_Reserva.cmbPedidoIngresar.getSelectedItem.toString))
      }catch {
        case _:Exception =>
      }
    }
    else if(e.getSource == ui_Reserva.cmbPedidoModificar) {
      try {
        ui_Reserva.sbCantidadPedidoModificar.setValue(pedido(ui_Reserva.cmbPedidoModificar.getSelectedItem.toString))
      }catch {
        case _:Exception =>
      }

    }
  }


  private def addDateListeners(): Unit = {
    ui_Reserva.dtpFechaIngresar.addPropertyChangeListener("date", new PropertyChangeListener() {
      override def propertyChange(evt: PropertyChangeEvent): Unit = {
        if ("date" == evt.getPropertyName) cambioFechaAction(1)()
      }
    })
    ui_Reserva.dtpFechaModificar.addPropertyChangeListener("date", new PropertyChangeListener() {
      override def propertyChange(evt: PropertyChangeEvent): Unit = {
        if ("date" == evt.getPropertyName) cambioFechaAction(2)()
      }
    })

  }

}
