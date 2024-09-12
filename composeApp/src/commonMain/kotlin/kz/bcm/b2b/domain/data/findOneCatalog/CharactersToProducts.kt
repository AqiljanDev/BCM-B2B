package kz.bcm.b2b.domain.data.findOneCatalog

interface CharactersToProducts {
    val id: Int
    val order: Int
    val characterId: String
    val characterValueId: String
    val productId: String
    val character: Character
    val characterValue: CharacterValue
}
