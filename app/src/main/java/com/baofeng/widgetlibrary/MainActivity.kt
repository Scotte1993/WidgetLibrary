package com.baofeng.widgetlibrary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.abooc.util.Debug
import com.baofeng.widget.searchbar.SearchBar
import com.baofeng.widget.searchbar.SearchBar.SimpleOnSearchListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBar = findViewById<SearchBar>(R.id.searchBar)
        searchBar.setOnSearchListener(object : SimpleOnSearchListener() {
            override fun onSearchKeyChanged(key: String?) {
                Debug.out("onSearchKeyChanged: key ---> $key")
            }

            override fun onSearch(key: String?) {
                Debug.out("onSearch: key ---> $key")
            }

            override fun onCancel() {
                Debug.out("onCancel: ")
            }

        })
    }
}
