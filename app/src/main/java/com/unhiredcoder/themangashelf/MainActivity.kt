package com.unhiredcoder.themangashelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.unhiredcoder.database.di.databaseModule
import com.unhiredcoder.listmanga.di.mangaModule
import com.unhiredcoder.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        modifier = Modifier.padding(innerPadding),
                        text = "Hello!"
                    )
                }
            }
        }

        setupKoinDI()
    }

    private fun setupKoinDI() {
        startKoin {
            androidContext(applicationContext)
            modules(networkModule, databaseModule, mangaModule)
        }
    }
}
