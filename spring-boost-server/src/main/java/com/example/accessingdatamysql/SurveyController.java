package com.example.accessingdatamysql;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.ElementCollection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {
    @Autowired
    private SurveyResultsRepository surveyResultsRepository;

    @GetMapping("/{surveyId}")
    public String getSurveyData(@PathVariable String surveyId) {
	//	String surveyId = "123";
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = "";
        try {
            // Load survey.json from the resources folder
            ClassPathResource resource = new ClassPathResource("surveys/survey.json");

            // Read the JSON file into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(resource.getInputStream());

        
            for (JsonNode survey : jsonNode) {
                if (survey.has("surveyId") && survey.get("surveyId").asText().equals(surveyId)) {
                    // Return the JSON data for the matching survey ID
                    return survey.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
	}
        
        return jsonData;
    }
    
    @PostMapping(path="/submit")
    public @ResponseBody String submitSurveyResults (@RequestBody Map<String, Object> requestData) {
        // Extract surveyId and ratings from the requestData map
        Integer surveyId = (Integer) requestData.get("surveyId");
        Map<String, Integer> ratings = (Map<String, Integer>) requestData.get("ratings");

        // Save survey results to the database
        SurveyResult surveyResult = new SurveyResult();
        surveyResult.setSurveyId(surveyId);
        surveyResult.setData(ratings);
        surveyResultsRepository.save(surveyResult);

        return "Submitted";
    }
    
    @GetMapping(path="/all_results")
    public @ResponseBody Iterable<SurveyResult> getAllResults() {
	return surveyResultsRepository.findAll();
    }
    
    @GetMapping(path="/results/{surveyId}")
    public @ResponseBody Iterable<SurveyResult> getSurveyData(@PathVariable Long surveyId) {
	Integer intSurveyId = surveyId.intValue();
	Iterable<SurveyResult> filteredSurveyResults = surveyResultsRepository.findBySurveyId(intSurveyId);
	return filteredSurveyResults;
	
        
    }
    
		
}

