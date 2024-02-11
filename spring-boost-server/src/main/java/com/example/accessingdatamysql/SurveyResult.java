package com.example.accessingdatamysql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import jakarta.persistence.ElementCollection;

@Entity
public class SurveyResult {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer surveyId;
    @ElementCollection
    private Map<String, Integer> data;
    public Integer getId(){
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }
    public Integer getSurveyId(){
	return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
	this.surveyId = surveyId;
    }
    public Map<String, Integer> getData(){
	return data;
    }

    public void setData(Map<String, Integer> data) {
	this.data = new HashMap<String, Integer>(data);
    }
    
}


