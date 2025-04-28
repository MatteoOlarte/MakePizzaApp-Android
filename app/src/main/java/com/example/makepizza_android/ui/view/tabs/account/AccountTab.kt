package com.example.makepizza_android.ui.view.tabs.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.makepizza_android.ui.theme.ApplicationTheme

object AccountTab : Tab {
    override val options: TabOptions
        @Composable
        get() = _GetTabOptions()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier.Companion.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { TabToolbar(scrollBehavior = scrollBehavior) }
        ) {
            TabContent(modifier = Modifier.Companion.padding(top = it.calculateTopPadding()))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TabToolbar(scrollBehavior: TopAppBarScrollBehavior) {
        TopAppBar(
            title = { Text(text = "Profile") },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.AutoMirrored.Filled.Logout, "")
                }
            },
            scrollBehavior = scrollBehavior
        )
    }


    @Composable
    fun TabContent(modifier: Modifier) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {

        }
    }

    @Composable
    private fun _GetTabOptions(): TabOptions {
        val title = "Account"
        val icon = rememberVectorPainter(Icons.Filled.Person)

        return remember {
            TabOptions(index = 0u, title = title, icon = icon)
        }
    }

    private fun readResolve(): Any = AccountTab
}

@Preview(device = Devices.PIXEL_6, showSystemUi = true)
@Composable
private fun AccountTabPreview() {
    ApplicationTheme { AccountTab.Content() }
}