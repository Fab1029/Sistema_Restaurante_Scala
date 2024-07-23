package Modelos.Restaurante

import scala.collection.mutable

class Reserva (private val _numeroReserva:String, private var _turno:String, private var _fecha:String, private var _numeroComensales: Int, private var _pedidos:mutable.TreeMap[String, Int]) extends Serializable{
  //Getters
  def turno:String = _turno
  def fecha:String = _fecha
  def numeroReserva:String = _numeroReserva
  def numeroComensales: Int = _numeroComensales
  def pedidos:mutable.TreeMap[String, Int] = _pedidos

  //Setters
  def turno_(turno:String):Unit = _turno = turno
  def fecha_(fecha:String):Unit = _fecha = fecha
  def pedidos_(pedidos:mutable.TreeMap[String, Int]):Unit = _pedidos = pedidos
  def numeroComensales_(numeroComensales:Int):Unit = _numeroComensales = numeroComensales

}
