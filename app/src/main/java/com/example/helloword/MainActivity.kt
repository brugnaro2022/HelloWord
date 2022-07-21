package com.example.helloword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloword.api.SearchResult
import com.example.helloword.api.createGitHubApiService
import com.example.helloword.repodetails.RepoDetailsActivity
import com.example.helloword.reposlist.ReposAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ReposAdapter { repo ->
            // Toast.makeText(this, repo.name, Toast.LENGTH_SHORT).show()
            RepoDetailsActivity.startActivity(this, repo)
        }

        val list: RecyclerView = findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        val service = createGitHubApiService()
        service.searchRepositories("android").enqueue(object: Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val repos = response.body()?.items.orEmpty()
                adapter.submitList(repos)
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                // handle failure
            }

        })
    }
}