package com.fit2081.currencyexchange.data.repository

import com.fit2081.currencyexchange.data.network.ApiService
import com.fit2081.currencyexchange.data.network.ResponseModel

/**
 * * Repo class for handling currency exchange rate data operations.
 *
 * This class acts as an intermediary between the ViewModel and the API Service.
 */
class RatesRepository {
    // Create the instance of the API service for making network requests
    private val apiService = ApiService.create()

    /**
     * * Fetches the currency exchange rate for a given base currency and target currency symbols.
     *
     * @param base The base currency code (e.g., "USD").
     * @param symbols A comma-separated list of target currency codes (e.g., "EUR,JPY").
     * @return A [ResponseModel] containing the base currency and a map of target currencies with their exchange rates.
     */
    suspend fun getRate(base: String, symbols: String): ResponseModel? {
        return apiService.getRate(base, symbols).body()
    }
}