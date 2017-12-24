package com.baofeng.widgetlibrary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.abooc.util.Debug
import com.baofeng.widget.searchbar.SearchBar
import com.baofeng.widget.searchbar.SearchBar.SimpleOnSearchListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Debug.out("MainActivity.onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBar = findViewById<SearchBar>(R.id.searchBar)
        searchBar.setOnSearchListener(object : SimpleOnSearchListener() {
            override fun onSearchKeyChanged(key: String?) {
                Debug.out("onSearchKeyChanged: key ---> $key")
            }

            override fun onSearch(key: String?) {
                Debug.out("onSearch: key ---> $key")
                TestActivity.launch(this@MainActivity)
            }

            override fun onCancel() {
                Debug.out("onCancel: ")
            }

        })
    }

    override fun onStart() {
        Debug.out("MainActivity.onStart: ")
        super.onStart()
    }

    override fun onRestart() {
        Debug.out("MainActivity.onRestart: ")
        super.onRestart()
    }

    override fun onResume() {
        Debug.out("MainActivity.onResume: ")
        super.onResume()
    }

    override fun onPause() {
        Debug.out("MainActivity.onPause: ")
        super.onPause()
    }

    override fun onStop() {
        Debug.out("MainActivity.onStop: ")
        super.onStop()
    }

    override fun onDestroy() {
        Debug.out("MainActivity.onDestroy: ")
        super.onDestroy()
    }
}
