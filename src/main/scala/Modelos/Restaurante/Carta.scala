package Modelos.Restaurante

import scala.collection.mutable

class Carta (private val _nombre:String, private var _fechaValidez:String, private var _productos:mutable.Set[String]) extends Serializable{
  //Getters
  def nombre:String = _nombre
  def fechaValidez:String = _fechaValidez
  def productos:mutable.Set[String] = _productos

  //Setters
  def fechaValidez_=(fechaValidez: String):Unit = _fechaValidez = fechaValidez
  def productos_=(productos: mutable.Set[String]): Unit = _productos = productos

}
