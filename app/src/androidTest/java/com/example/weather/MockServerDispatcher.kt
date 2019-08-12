package com.example.weather

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockServerDispatcher {

    inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(200).setBody(loadData())
        }
    }

    inner class ErrorDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {

            return MockResponse().setResponseCode(400)

        }
    }


    private fun loadData(): String? {
        return "{\n" +
            "  \"location\": {\n" +
            "    \"name\": \"Paris\",\n" +
            "    \"region\": \"Ile-de-France\",\n" +
            "    \"country\": \"France\",\n" +
            "    \"lat\": 48.87,\n" +
            "    \"lon\": 2.33,\n" +
            "    \"tz_id\": \"Europe/Paris\",\n" +
            "    \"localtime_epoch\": 1565513433,\n" +
            "    \"localtime\": \"2019-08-11 10:50\"\n" +
            "  },\n" +
            "  \"current\": {\n" +
            "    \"last_updated\": \"2019-08-11 10:40\",\n" +
            "    \"temp_c\": 17.0,\n" +
            "    \"condition\": {\n" +
            "    },\n" +
            "    \"uv\": 5.0\n" +
            "  },\n" +
            "  \"forecast\": {\n" +
            "    \"forecastday\": [\n" +
            "      {\n" +
            "        \"date\": \"2019-08-11\",\n" +
            "        \"day\": {\n" +
            "          \"avgtemp_c\": 18.8,\n" +
            "          \"condition\": {\n" +
            "          }\n" +
            "        },\n" +
            "        \"astro\": {\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"date\": \"2019-08-12\",\n" +
            "        \"day\": {\n" +
            "          \"avgtemp_c\": 18.7,\n" +
            "          \"condition\": {\n" +
            "          }\n" +
            "        },\n" +
            "        \"astro\": {\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"date\": \"2019-08-13\",\n" +
            "        \"day\": {\n" +
            "          \"avgtemp_c\": 19.0,\n" +
            "          \"condition\": {\n" +
            "          }\n" +
            "        },\n" +
            "        \"astro\": {\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"date\": \"2019-08-14\",\n" +
            "        \"day\": {\n" +
            "          \"avgtemp_c\": 20.3,\n" +
            "          \"condition\": {\n" +
            "          }\n" +
            "        },\n" +
            "        \"astro\": {\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"date\": \"2019-08-15\",\n" +
            "        \"day\": {\n" +
            "          \"avgtemp_c\": 21.2,\n" +
            "          \"condition\": {\n" +
            "          }\n" +
            "        },\n" +
            "        \"astro\": {\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"alert\": {\n" +
            "  }\n" +
            "}"
    }
}