package com.siemens.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Test
	void contextLoads() {
	}

	@Test
	void testGetTutorialById() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/1")
						.accept(MediaType.APPLICATION_JSON))
				.andDo((print()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	void testGetAllTutorials() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials")
				.accept(MediaType.APPLICATION_JSON))
				.andDo((print()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testCreateTutorial() throws Exception{
		String title = "Test";
		String desc = "Test desc1";
		Boolean published = false;

		mockMvc.perform(MockMvcRequestBuilders.post("/api/tutorials")
				.content("{\"title\":\"" + title + "\",\"description\":\"" + desc + "\",\"published\":" + published + "}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

}
