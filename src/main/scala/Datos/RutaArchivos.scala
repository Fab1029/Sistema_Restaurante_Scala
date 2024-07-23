package Datos

import scala.collection.immutable.ListMap

object RutaArchivos {
  private val _rutaArchivos:List[String] = List("src/main/scala/Datos/Cartas.dat", "src/main/scala/Datos/Reservas.dat", "src/main/scala/Datos/Productos.dat", "src/main/scala/Datos/Ingredientes.dat",
    "src/main/scala/Datos/Disponibilidad.dat", "src/main/scala/Datos/Encargados.dat")

  def rutaArchivos:List[String]  = _rutaArchivos


}
