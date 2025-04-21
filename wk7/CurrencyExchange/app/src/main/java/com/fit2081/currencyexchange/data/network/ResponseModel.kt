package com.fit2081.currencyexchange.data.network

/**
 * * DTO for the response from the currency exchange API.
 *
 * It contains the base currency and a map of currency codes to their exchange rates.
 *
 * @param base The base currency code.
 * @param rates A map of currency codes to their exchange rates.
 */
data class ResponseModel(
    var base: String,
    var rates: Map<String, Double>
)