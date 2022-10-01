package com.codepath.flixsterplus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MovieFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_fragment_list, container, false)
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        updateAdapter(recyclerView)
        return view
    }

    private fun updateAdapter(recyclerView: RecyclerView){

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY

        client[
                "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1", params, object : JsonHttpResponseHandler()

        {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {


                Log.d("MovieFragment", json.toString())
                val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
                val moviesRawJSON : String = resultsJSON.toString()

                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<Movie>>(){}.type

                val models : List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                recyclerView.adapter = MoviesRecyclerViewAdapter(models, this@MovieFragment)

                Log.d("MovieFragment", "response successful")
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, throwable: Throwable?) {

                throwable?.message?.let{
                    Log.d("MovieFragment", errorResponse)
                }
            }
        }]
    }

    override fun onItemClick(item: Movie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }


}