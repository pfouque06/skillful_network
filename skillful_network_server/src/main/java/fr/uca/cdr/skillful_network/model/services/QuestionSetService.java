package fr.uca.cdr.skillful_network.model.services;

import java.util.List;
import java.util.Optional;

import fr.uca.cdr.skillful_network.model.entities.simulation.exercise.QuestionSet;
import fr.uca.cdr.skillful_network.request.ExerciseForm;

public interface QuestionSetService {
	
	float calculateGrade(ExerciseForm exerciseForm, float weightByExercice);
	List<QuestionSet> getAllQuestionSets();
    Optional<QuestionSet> getQuestionSetById(Long id);
    QuestionSet saveOrUpdateQuestionSet(QuestionSet questionSet);
			
}
