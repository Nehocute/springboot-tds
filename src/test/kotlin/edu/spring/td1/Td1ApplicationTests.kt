package edu.spring.td1

import org.hamcrest.core.StringContains.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@SpringBootTest
@AutoConfigureMockMvc
class Td1ApplicationTests {

	@Autowired
	private val mvc: MockMvc? = null

	@Test
	fun contextLoads() {
	}

	@Test
	@Throws(Exception::class)
	fun loadHello() {
		mvc!!.perform(get("/"))
				.andDo{println(it.response.contentAsString)}
				.andExpect(content().string(containsString("Gestion d'items")))
	}

}
