package com.keepcoding.madridshops

import com.keepcoding.madridshops.repository.model.DataEntity
import com.keepcoding.madridshops.repository.model.DataResponseEntity
import com.keepcoding.madridshops.repository.network.json.JsonEntitiesParser
import com.keepcoding.madridshops.util.ReadJsonFile
import junit.framework.Assert.*
import org.junit.Test

class JSONParsingTest {
    @Test
    @Throws(Exception::class)
    fun given_valid_string_containing_json_it_parses_correctly() {


        // leo datos del fichero JSON que tiene una sola shop
        val shopsJson = ReadJsonFile().loadJSONFromAsset("shop.json")

        // veo que la string en la que leí el JSON no está vacía
        assertTrue(false == shopsJson.isEmpty())

        // hago el parseo usando la librería Jakson Json
        val parser = JsonEntitiesParser()
        val shop = parser.parse<DataEntity>(shopsJson)

        assertNotNull(shop)
        assertEquals("Cortefiel - Preciados",shop.name)

    }

    @Test
    @Throws(Exception::class)
    fun given_invalid_string_containing_json_it_petaquetecagas() {


        // leo datos del fichero JSON que tiene una sola shop
        val shopsJson = ReadJsonFile().loadJSONFromAsset("shopWrongLatitude.json")

        // veo que la string en la que leí el JSON no está vacía
        assertTrue(false == shopsJson.isEmpty())

        // hago el parseo usando la librería Jakson Json
        val parser = JsonEntitiesParser()
        val shop = parser.parse<DataEntity>(shopsJson)

        assertNotNull(shop)
        assertEquals("Cortefiel - Preciados",shop.name)
        assertEquals(40.4180563f, shop.gps_lat,1f)
    }

    @Test
    @Throws(Exception::class)
    fun given_valid_string_containing_json_shops_it_parses_correctly() {

        // leo datos del fichero JSON que tiene una sola shop
        val shopsJson = ReadJsonFile().loadJSONFromAsset("shops.json")

        // veo que la string en la que leí el JSON no está vacía
        assertTrue(false == shopsJson.isEmpty())

        // hago el parseo usando la librería Jakson Json
        val parser = JsonEntitiesParser()
        val responseEntity = parser.parse<DataResponseEntity>(shopsJson)

        assertNotNull(responseEntity)
        assertEquals(6, responseEntity.result.count())
        assertEquals("Cortefiel - Preciados",responseEntity.result[0].name)
        assertEquals(40.4180563f, responseEntity.result[0].gps_lat,1f)
    }
}