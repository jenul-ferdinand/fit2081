package com.fit2081.currencyexchange.data.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * * Fetches the latest exchange rates for a given base and target currency
     *
     * @param base The base currency code (e.g., "USD").
     * @param symbols A comma-separated list of target currency codes (e.g., "EUR,JPY").
     * @return A [ResponseModel] containing the base currency and a map of target currencies with their exchange rates.
     */
    @GET("v1/latest")
    suspend fun getRate(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Response<ResponseModel>

    /**
     * * Companion object to provide factory method for creating an instance of [ApiService].
     */
    companion object {
        // The base url for the API
        var BASE_URL = "https://api.frankfurter.dev/"

        /**
         * Creates an instance of [ApiService] using RetroFit.
         * @return An implementation of [ApiService] for making API calls.
         */
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}