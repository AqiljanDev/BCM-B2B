package kz.bcm.b2b.domain.data.findOneCatalog

interface Info {
    val id: Int
    val id1c: String
    val title: String
    val slug: String
    val photo: String
    val parentId: String
    val parentCategory: ParentCategory?
    val childCategory: List<ChildCategory>
}
