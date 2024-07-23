package Modelos.Usuario
import Datos.RutaArchivos

import scala.collection.mutable
import java.io.{File, FileInputStream, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}

object GestionUsuarios {
  private var _encargados: mutable.TreeMap[String, Encargado] = cargar()


  def encargados: mutable.TreeMap[String, Encargado] = _encargados

  private def cargar(): mutable.TreeMap[String, Encargado] = {
    if(new File(RutaArchivos.rutaArchivos.last).exists()){

      try {
        val file = new ObjectInputStream(new FileInputStream(RutaArchivos.rutaArchivos.last))
        val encargados = file.readObject().asInstanceOf[mutable.TreeMap[String, Encargado]]
        file.close()
        //Cargar admin
        encargados += ("admin" -> new Encargado("admin", "admin", "admin", "admin"))
        encargados

      } catch {
        case _: Exception =>
          mutable.TreeMap[String, Encargado]("admin" -> new Encargado("admin", "admin", "admin", "admin"))
      }

    }
    else mutable.TreeMap[String, Encargado]("admin" -> new Encargado("admin", "admin", "admin", "admin"))
  }

  def guardar(): Unit = {
    try {
      val file = new ObjectOutputStream(new FileOutputStream(RutaArchivos.rutaArchivos.last))
      file.writeObject(_encargados)
      file.flush()
      file.close()
    } catch {
      case e: IOException =>
        println(e)
      case _: ClassNotFoundException =>
        //Primera vez se crea el archivo
    }
  }

}

