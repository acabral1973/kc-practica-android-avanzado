package com.keepcoding.madridshops.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.keepcoding.madridshops.repository.network.GetJsonManager
import com.keepcoding.madridshops.repository.network.GetJsonManagerVolleyImpl
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class VolleyInstrumentedTest {
    val appContext = InstrumentationRegistry.getTargetContext()

    @Test
    @Throws(Exception::class)
    fun given_valid_url_we_get_no_null_json_as_string() {
        val url = "http://madrid-shops.com/json_new/getShops.php"
        val jsonManager: GetJsonManager = GetJsonManagerVolleyImpl(appContext)

        jsonManager.execute(url, success =  object : SuccessCompletion<String> {
            override fun successCompletion(e: String) {
                assertTrue(e.isNotEmpty())
            }
        }, error = object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                assertTrue(false)
            }
        })
    }
}
