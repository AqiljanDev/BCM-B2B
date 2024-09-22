package kz.bcm.b2b.domain.data.auth.register

interface PostRegistration {
    val email: String
    val phone: String
    val company: String
    val type: String
    val area: String
    val site: String
    val bin: String
    val address: String
    val bik: String
    val bank: String
    val iik: String
}