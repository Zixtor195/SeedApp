package app.android.seedapp.utils.ui

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.seedapp.ui.theme.White
import app.android.seedapp.ui.theme.AppPrimary
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState

@Composable
fun ContentView(
    urlToOpen: String = "https://wsp.registraduria.gov.co/censo/consultar",
    title: String = "Registro",
    onClickBackButton: () -> Unit
) {
    val state = rememberWebViewState(url = urlToOpen)

    Column(modifier = Modifier.fillMaxSize()) {
        WebViewTopBar(title = title) { onClickBackButton() }
        WebViewPart(webViewState = state)
    }
}

@Composable
fun WebViewTopBar(title: String, onClickBackButton: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppPrimary)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = { onClickBackButton() }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, tint = White, contentDescription = "")
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontSize = 24.sp,
                color = White,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun WebViewPart(webViewState: WebViewState) {
    if (webViewState.isLoading) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            color = AppPrimary
        )
    }

    WebView(
        modifier = Modifier.fillMaxSize(),
        state = webViewState,
        onCreated = { webView ->
            setupWebView(webView = webView)
            setupWebViewChromeClient(webView = webView)
            setupWebViewClient(webView = webView)
        }
    )
}

private fun setupWebView(webView: WebView) {
    val webViewSettings = webView.settings
    webViewSettings.javaScriptEnabled = true
    webViewSettings.useWideViewPort = true
}

private fun setupWebViewChromeClient(webView: WebView) {
    webView.webChromeClient = object : WebChromeClient() {}
}

private fun setupWebViewClient(webView: WebView) {
    webView.webViewClient = object : WebViewClient() {}
}