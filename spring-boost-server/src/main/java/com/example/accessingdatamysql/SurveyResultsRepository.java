package com.example.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;
import com.example.accessingdatamysql.SurveyResult;

public interface SurveyResultsRepository extends CrudRepository<SurveyResult, Integer> {
    Iterable<SurveyResult> findBySurveyId(Integer surveyId);

        
}
