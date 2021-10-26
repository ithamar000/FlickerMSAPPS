package com.example.flickermsapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//mapps  -   aabca25d8cd75f676d3a74a72dcebf21
//myKey  -   720eff91b08581b424593aab6a477815
//secret -   65b2a7a4c085d309

public class MainActivity extends AppCompatActivity {

    private String ENDPOINT = "https://api.flickr.com/services/rest/";
    private String QUERY_RECENT_PHOTOS_MAPPS = "https://www.flickr.com/services/rest/?method=flickr.photos.getRecent" +
            "&api_key=aabca25d8cd75f676d3a74a72dcebf21&per_age=500&format=json&nojsoncallback=1";
    private List<FlickerPhoto> photoList;
    private RecyclerView recyclerView;
    private PhotosAdapter photosAdapter;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeContainer;
    private ProgressBar progressBar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressbar);
        requestQueue = Volley.newRequestQueue(this);
        photoList = new ArrayList<FlickerPhoto>();
        recyclerView = findViewById(R.id.photos_recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        photosAdapter = new PhotosAdapter(this, (ArrayList<FlickerPhoto>) photoList);
        recyclerView.setAdapter(photosAdapter);
        photosAdapter.setListenter(new PhotosAdapter.MyPhotoListener() {
            @Override
            public void onPhotoClicked(int position, View view) {
                Intent intent = new Intent(MainActivity.this,FullsizePhotoActivity.class);
                intent.putExtra("url",photoList.get(position).getPhotoUrl());
                startActivity(intent);
            }
        });

/*        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                JsonObjectRequest jsonObjectRequest = getJsonObjectRequest();
                requestQueue.add(jsonObjectRequest);
            }
        });*/

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                photosAdapter.clear();
                JsonObjectRequest jsonObjectRequest = getJsonObjectRequest();
                requestQueue.add(jsonObjectRequest);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        JsonObjectRequest jsonObjectRequest = getJsonObjectRequest();
        requestQueue.add(jsonObjectRequest);
    }



    @NonNull
    private JsonObjectRequest getJsonObjectRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, QUERY_RECENT_PHOTOS_MAPPS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e("Rest Response", response.toString());

                        try {

                            JSONObject photos_json = response.getJSONObject("photos");
                            JSONArray photos_array = photos_json.getJSONArray("photo");
                            JSONObject photo;

                            if (photos_array != null) {

                                for (int i=0;i<photos_array.length();i++){

                                    photo = (JSONObject) photos_array.get(i);
                                    FlickerPhoto flickerPhoto = new FlickerPhoto();
                                    String server_id = photo.getString("server");
                                    String id = photo.getString("id");
                                    String secret = photo.getString("secret");

                                    flickerPhoto.setId(id);
                                    flickerPhoto.setOwner(photo.getString("owner"));
                                    flickerPhoto.setSecret(secret);
                                    flickerPhoto.setServer(server_id);
                                    flickerPhoto.setFarm(photo.getString("farm"));
                                    flickerPhoto.setTitle(photo.getString("title"));
                                    flickerPhoto.setIsfamily(photo.getString("isfamily"));
                                    flickerPhoto.setIsfriend(photo.getString("isfriend"));
                                    flickerPhoto.setIspublic(photo.getString("ispublic"));

                                    String pic_url = "https://live.staticflickr.com/"+server_id+"/"+id+"_"+secret+"_q.jpg";

                                    flickerPhoto.setPhotoUrl(pic_url);
                                    if(!photoList.contains(flickerPhoto))
                                        photoList.add(flickerPhoto);
                                }
                                swipeContainer.setRefreshing(false);
                                progressBar.setVisibility(View.GONE);
                                photosAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Rest Error", error.toString());
            }
        });
        return jsonObjectRequest;
    }

    @Override
    protected void onResume() {
        JsonObjectRequest jsonObjectRequest = getJsonObjectRequest();
        requestQueue.add(jsonObjectRequest);
        super.onResume();
    }
}








































