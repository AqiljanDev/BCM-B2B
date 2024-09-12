package kz.bcm.b2b.domain.data.collectCharacters

interface CharacterItem {
    val id: Int
    val id1c: String
    val title: String
    val characters: List<CharacterValue>
}
