package kz.bcm.b2b.presentation.other.data

enum class SortItem(val message: String, val translate: String) {
    NEW(message = "new", translate = "Новые"),
    OLD(message = "old", translate = "Старые"),
    MIN(message = "min", translate = "Сначала дешевые"),
    MAX(message = "max", translate = "Сначала дорогие")
}