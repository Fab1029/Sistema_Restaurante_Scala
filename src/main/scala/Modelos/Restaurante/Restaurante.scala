package Modelos.Restaurante

import Modelos.Comida._
import Datos.RutaArchivos

import java.io._
import scala.collection.mutable

object Restaurante {
  private var _ruc: String = ""
  private var _email: String = ""
  private var _nombre: String = ""
  private var _telefono: String = ""
  private var _ubicacion: String = ""
  private var _razonSocial: String = ""
  private var _turnos = List("Mañana", "Tarde", "Noche")

  private var _cartas: mutable.TreeMap[String, Carta] = mutable.TreeMap[String, Carta]()
  private var _reservas: mutable.TreeMap[String, Reserva] = mutable.TreeMap[String, Reserva]()
  private var _productos: mutable.TreeMap[String, Producto] = mutable.TreeMap[String, Producto]()
  private var _ingredientes: mutable.TreeMap[String, Ingrediente] = mutable.TreeMap[String, Ingrediente]()
  private var _diasDisponibilidad: mutable.TreeMap[String, mutable.TreeMap[String, DiaDisponibilidad]] = mutable.TreeMap[String, mutable.TreeMap[String, DiaDisponibilidad]]()

  // Getters
  def ruc: String = _ruc
  def email: String = _email
  def nombre: String = _nombre
  def telefono: String = _telefono
  def turnos: List[String] = _turnos
  def ubicacion: String = _ubicacion
  def razonSocial: String = _razonSocial

  def cartas: mutable.TreeMap[String, Carta] = _cartas
  def reservas: mutable.TreeMap[String, Reserva] = _reservas
  def productos: mutable.TreeMap[String, Producto] = _productos
  def ingredientes: mutable.TreeMap[String, Ingrediente] = _ingredientes
  def diasDisponibilidad: mutable.TreeMap[String, mutable.TreeMap[String, DiaDisponibilidad]] = _diasDisponibilidad

  // Setters
  def ruc_(ruc: String): Unit = _ruc = ruc
  def email_(email: String): Unit = _email = email
  def nombre_(nombre: String): Unit = _nombre = nombre
  def telefono_(telefono: String): Unit = _telefono = telefono
  def ubicacion_(ubicacion: String): Unit = _ubicacion = ubicacion
  def razonSocial_(razonSocial: String): Unit = _razonSocial = razonSocial

  // Cargar Datos Restaurante
  def cargarDatosRestaurante(carga: Int): () => Unit = {
    carga match {
      // Carga datos generales
      case 1 => () => {
        if(new File(RutaArchivos.rutaArchivos.head).exists() && new File(RutaArchivos.rutaArchivos(1)).exists() && new File(RutaArchivos.rutaArchivos(2)).exists() && new File(RutaArchivos.rutaArchivos(3)).exists() && new File(RutaArchivos.rutaArchivos(4)).exists()){

          try {
            val cartasFile = new ObjectInputStream(new FileInputStream(RutaArchivos.rutaArchivos.head))
            val reservasFile = new ObjectInputStream(new FileInputStream(RutaArchivos.rutaArchivos(1)))
            val productosFile = new ObjectInputStream(new FileInputStream(RutaArchivos.rutaArchivos(2)))
            val ingredientesFile = new ObjectInputStream(new FileInputStream(RutaArchivos.rutaArchivos(3)))
            val disponibilidadFile = new ObjectInputStream(new FileInputStream(RutaArchivos.rutaArchivos(4)))

            _cartas ++= cartasFile.readObject().asInstanceOf[mutable.TreeMap[String, Carta]]
            _reservas ++= reservasFile.readObject().asInstanceOf[mutable.TreeMap[String, Reserva]]
            _productos ++= productosFile.readObject().asInstanceOf[mutable.TreeMap[String, Producto]]
            _ingredientes ++= ingredientesFile.readObject().asInstanceOf[mutable.TreeMap[String, Ingrediente]]
            _diasDisponibilidad ++= disponibilidadFile.readObject().asInstanceOf[mutable.TreeMap[String, mutable.TreeMap[String, DiaDisponibilidad]]]

            cartasFile.close()
            reservasFile.close()
            productosFile.close()
            ingredientesFile.close()
            disponibilidadFile.close()
          } catch {
            case e: Exception =>
              e.printStackTrace()
            // Primera vez se carga los archivos
          }

        }

      }

      // Carga información del restaurante
      case 2 => () => {
        if(new File("src/main/scala/Datos/InformacionRestaurante.dat").exists()){

          try {
            val file = new ObjectInputStream(new FileInputStream("src/main/scala/Datos/InformacionRestaurante.dat"))
            _ruc = file.readObject().asInstanceOf[String]
            _email = file.readObject().asInstanceOf[String]
            _nombre = file.readObject().asInstanceOf[String]
            _telefono = file.readObject().asInstanceOf[String]
            _ubicacion = file.readObject().asInstanceOf[String]
            _razonSocial = file.readObject().asInstanceOf[String]

            file.close()
          } catch {
            case e: Exception =>
              e.printStackTrace()
            // Primera vez que se carga los archivos
          }

        }
      }
      case _ => () => {
        // No realiza ninguna elección
      }
    }
  }

  // Guardar Datos Restaurante
  def guardarDatosRestaurante(carga: Int): () => Unit = {
    carga match {
      // Guardar datos generales
      case 1 => () => {
        try {
          val cartasFile = new ObjectOutputStream(new FileOutputStream(RutaArchivos.rutaArchivos.head))
          val reservasFile = new ObjectOutputStream(new FileOutputStream(RutaArchivos.rutaArchivos(1)))
          val productosFile = new ObjectOutputStream(new FileOutputStream(RutaArchivos.rutaArchivos(2)))
          val ingredientesFile = new ObjectOutputStream(new FileOutputStream(RutaArchivos.rutaArchivos(3)))
          val disponibilidadFile = new ObjectOutputStream(new FileOutputStream(RutaArchivos.rutaArchivos(4)))

          cartasFile.writeObject(_cartas)
          reservasFile.writeObject(_reservas)
          productosFile.writeObject(_productos)
          ingredientesFile.writeObject(_ingredientes)
          disponibilidadFile.writeObject(_diasDisponibilidad)

          cartasFile.flush()
          cartasFile.close()
          reservasFile.flush()
          reservasFile.close()
          productosFile.flush()
          productosFile.close()
          ingredientesFile.flush()
          ingredientesFile.close()
          disponibilidadFile.flush()
          disponibilidadFile.close()
        } catch {
          case e: Exception =>
            e.printStackTrace()
            println("Problemas guardar archivos datos generales")
        }
      }

      case 2 => () => {
        try {
          val file = new ObjectOutputStream(new FileOutputStream("src/main/scala/Datos/InformacionRestaurante.dat"))
          file.writeObject(_ruc)
          file.writeObject(_email)
          file.writeObject(_nombre)
          file.writeObject(_telefono)
          file.writeObject(_ubicacion)
          file.writeObject(_razonSocial)

          file.flush()
          file.close()
        } catch {
          case e: Exception =>
            e.printStackTrace()
            println("Problemas guardar información del restaurante")
        }
      }

      case _ => () => {
        // No realiza ninguna elección
      }
    }
  }
}

