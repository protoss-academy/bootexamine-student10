package com.protosstechnology.train.bootexamine;

import com.protosstechnology.train.bootexamine.document.Document;
import com.protosstechnology.train.bootexamine.document.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BootexamineApplicationTests {

	@Autowired
	private MockMvc mock;

	@Autowired
	private DocumentRepository repository;

	@Test
	@Order(1)
	void save() throws Exception {
		String docNumber = "DOC001";
		String description = "Biology";
		mock.perform(
				MockMvcRequestBuilders.post("/document")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{" +
								"\"documentNumber\": \"" + docNumber + "\"," +
								"\"description\": \"" + description + "\"" +
								"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.documentNumber", CoreMatchers.is(docNumber)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.description", CoreMatchers.is(description)))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(2)
	void found() throws Exception {
		Document entity = repository.findById(new Long(1)).get();
		mock.perform(
				MockMvcRequestBuilders.get("/document/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.documentNumber", CoreMatchers.is(entity.getDocumentNumber())))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.description", CoreMatchers.is(entity.getDescription())))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(3)
	void update() throws Exception {
		Document entity = repository.findById(new Long(1)).get();
		String docNumber = "DOC002";
		String description = "Math";
		mock.perform(
				MockMvcRequestBuilders.put("/document/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{" +
								"\"documentNumber\": \"" + docNumber + "\"," +
								"\"description\": \"" + description + "\"" +
								"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.id", CoreMatchers.is(1)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.documentNumber", CoreMatchers.is(docNumber)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.documentNumber", CoreMatchers.not(entity.getDocumentNumber())))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.description", CoreMatchers.is(description)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.description", CoreMatchers.not(entity.getDescription())))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(4)
	void delete() throws Exception {
		mock.perform(
				MockMvcRequestBuilders.delete("/document/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.code", CoreMatchers.is(200)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.message", CoreMatchers.is("Deleted")))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(5)
	void not_found() throws Exception {
		mock.perform(
				MockMvcRequestBuilders.get("/document/2")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.code", CoreMatchers.is(400)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.message", CoreMatchers.is("Not Found")))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(6)
	void update_fail() throws Exception {
		String docNumber = "DOC002";
		String description = "Math";
		mock.perform(
				MockMvcRequestBuilders.put("/document/2")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{" +
								"\"documentNumber\": \"" + docNumber + "\"," +
								"\"description\": \"" + description + "\"" +
								"}"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.code", CoreMatchers.is(400)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.message", CoreMatchers.is("Bad Request")))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(7)
	void delete_fail() throws Exception {
		mock.perform(
				MockMvcRequestBuilders.delete("/document/2")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.code", CoreMatchers.is(400)))
				.andExpect(MockMvcResultMatchers
						.jsonPath("$.message", CoreMatchers.is("Bad Request")))
				.andDo(MockMvcResultHandlers.print());
	}


}
