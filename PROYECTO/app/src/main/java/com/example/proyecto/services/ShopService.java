package com.example.proyecto.services;
import com.example.proyecto.models.Inventario;
import com.example.proyecto.models.Item;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.List;
public interface ShopService {
    @GET("game/store")
    Call<List<Item>> getItems();
    @PUT("game/user/{username}/inventory/")
    Call<Void> purchaseItem(@Path("username") String username, @Query("item") int itemId);
    @GET("game/user/{username}/inventory/")
    Call<List<Inventario>> inventario(@Path("username")String username);
}