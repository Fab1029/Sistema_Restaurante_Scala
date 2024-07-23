package Modelos.Comida

import scala.collection.mutable

class Producto (private var _nombre:String, private var _descripcion:String, private var _precio:Float, private var _ingredientes:mutable.Set[String]) extends Serializable{
  //Getters
  def nombre:String = _nombre
  def precio: Float = _precio
  def descripcion:String = _descripcion
  def ingredientes:mutable.Set[String] = _ingredientes

  //Setters
  def nombre_= (nombre: String):Unit = _nombre = nombre
  def precio_= (precio: Float):Unit = _precio = precio
  def descripcion_= (descripcion: String):Unit = _descripcion = descripcion
  def ingredientes_= (ingredientes: mutable.Set[String]):Unit = _ingredientes = ingredientes

}
