package Controladores.Restaurante

import Modelos.Restaurante.Restaurante
import Vistas.Restaurante.Ui_Reportes

import java.awt.event.{ActionEvent, ActionListener, WindowAdapter, WindowEvent}
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.swing.{JFrame, JOptionPane}


class ControladorReportes (private val tipoReporte:Int, private val controladorAnterior:JFrame) extends ActionListener{
  private var restaurante = Restaurante
  private val ui_Reportes:Ui_Reportes = new Ui_Reportes()
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  initSeccion(tipoReporte)()
  initAction()

  private def initAction(): Unit = {
    ui_Reportes.btnObtenerReporte.addActionListener(this)
  }

  private def initSeccion(tipoReporte: Int): () => Unit = {
    (1 until 13).foreach(indice => ui_Reportes.cmbMesFin.addItem(indice))
    (1 until 13).foreach(indice => ui_Reportes.cmbMesInicio.addItem(indice))
    (1990 until LocalDate.now().getYear + 2).foreach(indice => ui_Reportes.cmbAnoFin.addItem(indice))
    (1990 until LocalDate.now().getYear + 2).foreach(indice => ui_Reportes.cmbAnoInicio.addItem(indice))

    tipoReporte match {
      //Reporte_dinero
      case 0 => () => {
        ui_Reportes.initTableReporteDinero()
      }
      //Reporte_productos
      case 1 => () => {
        ui_Reportes.initTableReporteProductos()
      }
      //Reporte_concurrencia
      case 2 => () => {
        ui_Reportes.initTableReporteConcurrencia()
      }
    }

  }

  private def reporteDinero(): Map[String, Float] = {
    try {
      Restaurante.reservas.values.toList.foldLeft(Map[String, Float]()) { (mesDinero, reserva) =>
        val fechaReserva = LocalDate.parse(reserva.fecha)
        val mesAnio = s"${fechaReserva.getMonthValue}-${fechaReserva.getYear}"
        val dentroDelRango = {
          !fechaReserva.isBefore(LocalDate.of(ui_Reportes.cmbAnoInicio.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesInicio.getSelectedIndex + 1, 1)) &&
            !fechaReserva.isAfter(LocalDate.of(ui_Reportes.cmbAnoFin.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesFin.getSelectedIndex + 1, 1).plusMonths(1).minusDays(1))
        }

        if (dentroDelRango) {
          val totalDinero = reserva.pedidos.toList.collect {
            case (producto, cantidad) if Restaurante.productos.contains(producto) =>
              Restaurante.productos(producto).precio * cantidad
          }.sum
          mesDinero + (mesAnio -> (mesDinero.getOrElse(mesAnio, 0f) + totalDinero))
        } else {
          mesDinero
        }
      }
    } catch {
      case _: Exception => Map.empty[String, Float]
    }
  }

  private def reporteProductos(): Map[String, (String, Int)] = {
    try {
      val productoPorMes = Restaurante.reservas.values.foldLeft(Map[String, Map[String, Int]]()) { (mesProducto, reserva) =>
        val fechaReserva = LocalDate.parse(reserva.fecha)
        val mesAnio = s"${fechaReserva.getMonthValue}-${fechaReserva.getYear}"
        val dentroDelRango = {
          !fechaReserva.isBefore(LocalDate.of(ui_Reportes.cmbAnoInicio.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesInicio.getSelectedIndex + 1, 1)) &&
            !fechaReserva.isAfter(LocalDate.of(ui_Reportes.cmbAnoFin.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesFin.getSelectedIndex + 1, 1).plusMonths(1).minusDays(1))
        }

        if (dentroDelRango) {
          val productosActualizados = reserva.pedidos.foldLeft(mesProducto.getOrElse(mesAnio, Map[String, Int]())) { (productos, pedido) =>
            val (producto, cantidad) = pedido
            productos + (producto -> (productos.getOrElse(producto, 0) + cantidad))
          }
          mesProducto + (mesAnio -> productosActualizados)
        } else {
          mesProducto
        }
      }

      productoPorMes.map { case (mesAnio, productos) =>
        val (productoMasVendido, cantidad) = productos.maxBy(_._2)
        mesAnio -> (productoMasVendido, cantidad)
      }
    } catch {
      case _: Exception => Map.empty[String, (String, Int)]
    }
  }

  private def reporteConcurrencia(): Map[String, (String, Int)] = {
    try {
      val concurrenciaPorMes = Restaurante.reservas.values.foldLeft(Map[String, Map[String, Int]]()) { (mesReserva, reserva) =>
        val fechaReserva = LocalDate.parse(reserva.fecha)
        val mesAnio = s"${fechaReserva.getMonthValue}-${fechaReserva.getYear}"
        val diaSemana = fechaReserva.getDayOfWeek.toString
        val dentroDelRango = {
          !fechaReserva.isBefore(LocalDate.of(ui_Reportes.cmbAnoInicio.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesInicio.getSelectedIndex + 1, 1)) &&
            !fechaReserva.isAfter(LocalDate.of(ui_Reportes.cmbAnoFin.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesFin.getSelectedIndex + 1, 1).plusMonths(1).minusDays(1))
        }

        if (dentroDelRango) {
          val diasActualizados = mesReserva.getOrElse(mesAnio, Map[String, Int]()) + (diaSemana -> (mesReserva.getOrElse(mesAnio, Map[String, Int]()).getOrElse(diaSemana, 0) + 1))
          mesReserva + (mesAnio -> diasActualizados)
        } else {
          mesReserva
        }
      }

      concurrenciaPorMes.map { case (mesAnio, dias) =>
        val (diaMasConcurrido, cantidad) = dias.maxBy(_._2)
        mesAnio -> (diaMasConcurrido, cantidad)
      }
    } catch {
      case _: Exception => Map.empty[String, (String, Int)]
    }
  }


  private def listarReportes(tipoReporte: Int): () => Unit = {
    tipoReporte match {
      //reporte_dinero
      case 0 => () => {
        val reporte = reporteDinero()
        if(reporte.nonEmpty){
          ui_Reportes.jgdReportes.setRowCount(0)
          reporte.foreach{case (mesAno, monto) => ui_Reportes.jgdReportes.addRow(Array[Object](mesAno, monto.toString))}
        }

      }
      //reporte_productos
      case 1 => () => {
        val reporte = reporteProductos()
        if(reporte.nonEmpty){
          ui_Reportes.jgdReportes.setRowCount(0)
          reporte.foreach{case (mesAno, productoCantidad) => ui_Reportes.jgdReportes.addRow(Array[Object](mesAno, productoCantidad._1, productoCantidad._2.toString))}
        }

      }
      //reporte_concurrencia
      case 2 => () => {
        val reporte = reporteConcurrencia()
        if(reporte.nonEmpty){
          ui_Reportes.jgdReportes.setRowCount(0)
          reporte.foreach{case (mesAno, reservaCantidad) => ui_Reportes.jgdReportes.addRow(Array[Object](mesAno, reservaCantidad._1, reservaCantidad._2.toString))}
        }

      }
    }
  }

  private def pesosDias(peso:Int): String = {
    peso match {
      case 1 => "Lunes"
      case 2 => "Martes"
      case 3 => "Miércoles"
      case 4 => "Jueves"
      case 5 => "Viernes"
      case 6 => "Sábado"
      case 7 => "Domingo"
    }
  }

  private def dialogoInformacion(titulo:String, cadena:String):Unit = {
    JOptionPane.showMessageDialog(null, cadena, titulo, JOptionPane.INFORMATION_MESSAGE)
  }

  //Cerrar ventana
  ui_Reportes.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      controladorAnterior.setVisible(true)
      ui_Reportes.dispose()
    }
  })


  override def actionPerformed(e: ActionEvent): Unit = {
    if(e.getSource == ui_Reportes.btnObtenerReporte){
      if(LocalDate.of(ui_Reportes.cmbAnoInicio.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesFin.getSelectedItem.asInstanceOf[Int], 1).isBefore(LocalDate.of(ui_Reportes.cmbAnoFin.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesFin.getSelectedItem.asInstanceOf[Int], 1).plusMonths(1).minusDays(1)) || LocalDate.of(ui_Reportes.cmbAnoInicio.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesFin.getSelectedItem.asInstanceOf[Int], 1).isEqual(LocalDate.of(ui_Reportes.cmbAnoFin.getSelectedItem.asInstanceOf[Int], ui_Reportes.cmbMesFin.getSelectedItem.asInstanceOf[Int], 1).plusMonths(1).minusDays(1)))
        listarReportes(tipoReporte)()
      else dialogoInformacion("Alerta", "Fechas inválidas")


    }
  }
}
