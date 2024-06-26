package com.example.remote.retrofit

import com.example.remote.model.request.DeliverRequestModel
import com.example.remote.model.response.DeliverResponseModel
import com.example.remote.model.response.OrderGameList
import com.example.remote.model.response.OrderResponseModel
import com.example.remote.model.response.TurtleBotResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("turtle/{storeId}/list")
    suspend fun getTurtleBot(
        @Path("storeId") storeId: String
    ): Response<List<TurtleBotResponseModel>>

    @GET("menu/order/store/{storeId}")
    suspend fun getOrderList(
        @Path("storeId") storeId: String
    )
    : Response<List<OrderResponseModel>>

    @GET("game/order/stores/{storeId}")
    suspend fun getGameList(
        @Path("storeId") storeId: String
    )
    : Response<OrderGameList>

    @POST("turtle/order/{turtleId}")
    suspend fun startDelivery(
        @Path("turtleId") turtleId : Int,
        @Body requestBody: DeliverRequestModel
    ) : Response<DeliverResponseModel>

    @DELETE("game/order/{orderGameId}")
    suspend fun cancelGameOrder(
        @Path("orderGameId") orderGameId : Int,
    ) : Response<Void>

    @PATCH("menu/order/{menuOrderId}/ready")
    suspend fun startMenuOrder(
        @Path("menuOrderId") menuOrderId : Int,
    ) : Response<Void>

    @DELETE("menu/order/{menuOrderId}/delete")
    suspend fun cancelMenuOrder(
        @Path("menuOrderId") menuOrderId : Int,
    ) : Response<Void>

//    @GET("stores/{storeId}")
//    suspend fun getStoreInfo(
//        @Path("storeId") storeId: Int
//    ) : Response<Void>
}