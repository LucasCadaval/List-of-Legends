package br.uri.listoflegends.models

data class ItemModel(
    val name: String,
    val description: String,
    val price: Price,
    val purchasable: Boolean,
    val icon: String
)

data class Price(
    val base: Int,
    val total: Int,
    val sell: Int
)
