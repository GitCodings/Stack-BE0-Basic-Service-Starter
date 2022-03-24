package com.github.klefstad_teaching.cs122b.basic;

import com.github.klefstad_teaching.cs122b.core.result.BasicResults;
import com.github.klefstad_teaching.cs122b.core.result.Result;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BasicServiceTest
{
    private static final String HELLO_PATH   = "/hello";
    private static final String REVERSE_PATH = "/reverse/{message}";
    private static final String MATH_PATH    = "/math";

    private final MockMvc mockMvc;

    @Autowired
    public BasicServiceTest(MockMvc mockMvc)
    {
        this.mockMvc = mockMvc;
    }

    private ResultMatcher[] isResult(Result result)
    {
        return new ResultMatcher[]{
            status().is(result.status().value()),
            jsonPath("result.code").value(result.code()),
            jsonPath("result.message").value(result.message())
        };
    }

    @Test
    public void applicationLoads() { }

    // Hello Tests

    @Test
    public void basicHelloSuccess()
        throws Exception
    {
        this.mockMvc.perform(get(HELLO_PATH))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.HELLO))
                    .andExpect(jsonPath("greeting").value(
                        "Hello there user! This is a test message! " +
                        "If the configuration properties are set up " +
                        "correctly you should be able to see this!"
                    ));
    }

    // Reverse Tests
    @Test
    public void reverseMessageEmpty()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, " "))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_IS_EMPTY))
                    .andExpect(jsonPath("reversed").doesNotHaveJsonPath());
    }

    @Test
    public void reverseMessageLowerCase()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, "test"))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_SUCCESSFULLY_REVERSED))
                    .andExpect(jsonPath("reversed").value("tset"));
    }

    @Test
    public void reverseMessageUpperCase()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, "TSET"))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_SUCCESSFULLY_REVERSED))
                    .andExpect(jsonPath("reversed").value("TEST"));
    }

    @Test
    public void reverseMessageMix()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, "TeSt"))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_SUCCESSFULLY_REVERSED))
                    .andExpect(jsonPath("reversed").value("tSeT"));
    }

    @Test
    public void reverseMessageSpace()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, "Te s t"))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_SUCCESSFULLY_REVERSED))
                    .andExpect(jsonPath("reversed").value("t s eT"));
    }

    @Test
    public void reverseMessageNumber()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, "1234"))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_SUCCESSFULLY_REVERSED))
                    .andExpect(jsonPath("reversed").value("4321"));
    }

    @Test
    public void reverseMessageUnderScore()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, "_A123b__"))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_SUCCESSFULLY_REVERSED))
                    .andExpect(jsonPath("reversed").value("__b321A_"));
    }

    @Test
    public void reverseMessageInvalidEdge()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, "!ABC!"))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_CONTAINS_INVALID_CHARACTERS))
                    .andExpect(jsonPath("reversed").doesNotHaveJsonPath());
    }

    @Test
    public void reverseMessageInvalidMid()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, "AB!CD"))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_CONTAINS_INVALID_CHARACTERS))
                    .andExpect(jsonPath("reversed").doesNotHaveJsonPath());
    }

    @Test
    public void reverseMessageInvalidAll()
        throws Exception
    {
        this.mockMvc.perform(get(REVERSE_PATH, "!!!!"))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.STRING_CONTAINS_INVALID_CHARACTERS))
                    .andExpect(jsonPath("reversed").doesNotHaveJsonPath());
    }

    // Math Tests

    @Test
    public void basicMathSuccess()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 2);
        request.put("numZ", 3);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.CALCULATION_SUCCESSFUL))
                    .andExpect(jsonPath("value").value(5));
    }

    @Test
    public void mathEdgeX1()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 2);
        request.put("numZ", 3);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.CALCULATION_SUCCESSFUL))
                    .andExpect(jsonPath("value").value(5));
    }

    @Test
    public void mathEdgeX2()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 99);
        request.put("numY", 1);
        request.put("numZ", 0);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.CALCULATION_SUCCESSFUL))
                    .andExpect(jsonPath("value").value(99));
    }

    @Test
    public void mathEdgeY1()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 1);
        request.put("numZ", 0);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.CALCULATION_SUCCESSFUL))
                    .andExpect(jsonPath("value").value(1));
    }

    @Test
    public void mathEdgeY2()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 99);
        request.put("numZ", 0);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.CALCULATION_SUCCESSFUL))
                    .andExpect(jsonPath("value").value(99));
    }

    @Test
    public void mathEdgeZ1()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 1);
        request.put("numZ", -10);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.CALCULATION_SUCCESSFUL))
                    .andExpect(jsonPath("value").value(-9));
    }

    @Test
    public void mathEdgeZ2()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 1);
        request.put("numZ", 10);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.CALCULATION_SUCCESSFUL))
                    .andExpect(jsonPath("value").value(11));
    }

    @Test
    public void mathPastLimitX1()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 0);
        request.put("numY", 1);
        request.put("numZ", 0);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.DATA_CONTAINS_INVALID_INTEGERS))
                    .andExpect(jsonPath("value").doesNotHaveJsonPath());
    }

    @Test
    public void mathPastLimitX2()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 100);
        request.put("numY", 1);
        request.put("numZ", 0);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.DATA_CONTAINS_INVALID_INTEGERS))
                    .andExpect(jsonPath("value").doesNotHaveJsonPath());
    }

    @Test
    public void mathPastLimitY1()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 0);
        request.put("numZ", 0);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.DATA_CONTAINS_INVALID_INTEGERS))
                    .andExpect(jsonPath("value").doesNotHaveJsonPath());
    }

    @Test
    public void mathPastLimitY2()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 100);
        request.put("numZ", 0);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.DATA_CONTAINS_INVALID_INTEGERS))
                    .andExpect(jsonPath("value").doesNotHaveJsonPath());
    }

    @Test
    public void mathPastLimitZ1()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 1);
        request.put("numZ", -11);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.DATA_CONTAINS_INVALID_INTEGERS))
                    .andExpect(jsonPath("value").doesNotHaveJsonPath());
    }

    @Test
    public void mathPastLimitZ2()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 1);
        request.put("numZ", 11);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.DATA_CONTAINS_INVALID_INTEGERS))
                    .andExpect(jsonPath("value").doesNotHaveJsonPath());
    }

    @Test
    public void basicMathMissingIntegers()
        throws Exception
    {
        JSONObject request = new JSONObject();
        request.put("numX", 1);
        request.put("numY", 1);

        this.mockMvc.perform(post(MATH_PATH).contentType(MediaType.APPLICATION_JSON)
                                            .content(request.toJSONString()))
                    .andDo(print())
                    .andExpectAll(isResult(BasicResults.DATA_CONTAINS_MISSING_INTEGERS))
                    .andExpect(jsonPath("value").doesNotHaveJsonPath());
    }
}
