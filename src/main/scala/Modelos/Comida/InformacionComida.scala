package Modelos.Comida

object InformacionComida {

  private val _tiposComida: List[String] = List("Oriental", "Mexicana", "Carnes a la brasa", "Pescado", "Pasta-Pizza",
  "Vegetariana", "Vegana", "Macrobiótica", "Tapas", "Rápida", "Picante",
  "Detox", "Celiaca")

  private val _tiposIngredientes: List[String] = List("Fruta", "Verdura", "Tubérculo", "Legumbre", "Grano", "Hierba o Especie",
    "Fruto seco", "Cárnico", "Lacteo", "Otro")

  private val _medidaIngrediente: List[String] = List("Gramos", "Kilogramos", "Libras", "Mililitros",
  "Litros", "Tazas", "Cucharadas", "Cucharaditas",
  "Unidad", "Manojo")

  def tiposComida: List[String] = _tiposComida

  def tiposIngredientes: List[String] = _tiposIngredientes

  def medidaIngrediente: List[String] = _medidaIngrediente

}
