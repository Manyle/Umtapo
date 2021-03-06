package org.lenndi.umtapo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lenndi.umtapo.entity.configuration.Z3950;
import org.lenndi.umtapo.entity.configuration.Z3950Configuration;
import org.lenndi.umtapo.marc.transformer.impl.UnimarcToSimpleRecord;
import org.lenndi.umtapo.service.configuration.Z3950Service;
import org.lenndi.umtapo.service.specific.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Library web service test.
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringRunner.class)
@WebMvcTest(Z3950WebService.class)
@WithMockUser(username = "test", roles = {"USER", "ADMIN"})
public class Z3950WebServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Z3950Service z3950Service;

    @MockBean
    private RecordService recordService;

    @MockBean
    private UnimarcToSimpleRecord unimarcToSimpleRecord;

    @Autowired
    private ObjectMapper objectMapper;

    private Z3950Configuration z3950Configuration = new Z3950Configuration();

    /**
     * Sets .
     */
    @Before
    public void setup() {
        Map<Integer, Z3950> providers = new HashMap<>();
        Map<String, String> database = new HashMap<>();
        database.put("name", "TOUT_UTF8");
        database.put("password", "Z3950_BNF");
        database.put("username", "Z3950");
        Map<String, String> options = new HashMap<>();
        options.put("elementSetName", "f");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("title", "4");
        attributes.put("isbn", "0");
        Z3950 z3950 = new Z3950();
        z3950.setName("Bibliothèque Nationale de France");
        z3950.setUrl("z3950.bnf.fr");
        z3950.setPort(2211);
        z3950.setSyntax("1.2.840.10003.5.1");
        z3950.setDatabase(database);
        z3950.setOptions(options);
        providers.put(1, z3950);
        this.z3950Configuration.setProviders(providers);
    }

    /**
     * Test get z 3950.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetZ3950() throws Exception {

        given(this.z3950Service.find(1)).willReturn(this.z3950Configuration.getProviders().get(1));
        this.mockMvc.perform(get("/z3950/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Bibliothèque Nationale de France")))
                .andExpect(jsonPath("$.url", is("z3950.bnf.fr")))
                .andExpect(jsonPath("$.port", is(2211)))
                .andExpect(jsonPath("$.syntax", is("1.2.840.10003.5.1")))
                .andExpect(jsonPath("$.database", is(this.z3950Configuration.getProviders().get(1).getDatabase())))
                .andExpect(jsonPath("$.options", is(this.z3950Configuration.getProviders().get(1).getOptions())));
        verify(this.z3950Service, times(1)).find(1);
        verifyNoMoreInteractions(this.z3950Service);
    }

    /**
     * Test get all z 3950.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetAllZ3950() throws Exception {
        Map<Integer, Z3950> providers = this.z3950Configuration.getProviders();
        given(this.z3950Service.findAll()).willReturn(providers);

        this.mockMvc.perform(get("/z3950")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Bibliothèque Nationale de France")));
        verify(this.z3950Service, times(1)).findAll();
        verifyNoMoreInteractions(this.z3950Service);
    }
}