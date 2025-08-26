package com.example.makepizza_android.ui.view.screens.checkout

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.gson.Gson
import java.util.Base64

class ScreenPaypal(
    private val amount: Double, // 🔹 valor dinámico
    private val onResult: (Boolean) -> Unit
) : Screen {

    @Composable
    override fun Content() {
        val client = OkHttpClient()
        val gson = Gson()
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        var status by remember { mutableStateOf("Esperando...") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(status)

            Button(onClick = {
                scope.launch(Dispatchers.IO) {
                    try {
                        // 🔹 Paso 1: Obtener token
                        val clientId = "ATdZkF-8be4avH0N34LvnuONknf2XxukivxyAc9e4Jrc_biAre-76tMxQmoQ2pHsI8KDwXvFOn-XRlUI"
                        val secret = "EFq9vcVwtk5O5mK2kG2D6I5uGrAiius85Aw_bQd_6aQz8g_1iPerwSKtwSBTAeQnvc-NTnJ9PtWplb_F"
                        val basicAuth = "Basic " + Base64.getEncoder()
                            .encodeToString("$clientId:$secret".toByteArray())

                        val tokenReq = Request.Builder()
                            .url("https://api-m.sandbox.paypal.com/v1/oauth2/token")
                            .post(FormBody.Builder().add("grant_type", "client_credentials").build())
                            .header("Authorization", basicAuth)
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .build()

                        val tokenResp = client.newCall(tokenReq).execute()
                        val tokenBody = tokenResp.body?.string()
                        val tokenJson = gson.fromJson(tokenBody, Map::class.java)
                        val token = tokenJson["access_token"] as? String

                        if (token == null) {
                            withContext(Dispatchers.Main) {
                                status = "❌ No se pudo obtener el token. Respuesta: $tokenBody"
                            }
                            return@launch
                        }

                        // 🔹 Paso 2: Crear orden con valor dinámico
                        val orderBody = """
                            {
                              "intent": "CAPTURE",
                              "purchase_units": [
                                {
                                  "amount": {
                                    "currency_code": "USD",
                                    "value": "${String.format("%.2f", amount)}"
                                  }
                                }
                              ]
                            }
                        """.trimIndent()

                        val orderReq = Request.Builder()
                            .url("https://api-m.sandbox.paypal.com/v2/checkout/orders")
                            .post(orderBody.toRequestBody("application/json".toMediaType()))
                            .header("Authorization", "Bearer $token")
                            .header("Content-Type", "application/json")
                            .build()

                        val orderResp = client.newCall(orderReq).execute()
                        val orderBodyResp = orderResp.body?.string()
                        val orderJson = gson.fromJson(orderBodyResp, Map::class.java)
                        val orderId = orderJson["id"] as? String

                        if (orderId == null) {
                            withContext(Dispatchers.Main) {
                                status = "❌ No se pudo obtener el ID de la orden. Respuesta: $orderBodyResp"
                            }
                            return@launch
                        }

                        // 🔹 Redirigir a PayPal con el orderId
                        val approveUrl = "https://www.sandbox.paypal.com/checkoutnow?token=$orderId"

                        withContext(Dispatchers.Main) {
                            status = "🔗 Redirigiendo a PayPal..."
                            openExternalBrowser(context, approveUrl)
                        }

                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            status = "Error: ${e.message}"
                            onResult(false)
                        }
                    }
                }
            }) {
                Text("Pagar con PayPal")
            }
        }
    }

    private fun openExternalBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}