package com.mycompany.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.pojo.Survey;
import com.mycompany.services.SurveyService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiSurveyController {

    @Autowired
    private SurveyService surveyService;

    // --- ADMIN ---
    @GetMapping("/admin/surveys")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Survey>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }    @PostMapping("/admin/surveys")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Survey> addSurvey(@RequestBody Map<String, Object> params) {
        Survey s = surveyService.addSurvey(params);
        return ResponseEntity.ok(s);
    }

    @PutMapping("/admin/surveys/{surveyId}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Survey> updateSurveyStatus(@PathVariable Long surveyId, @RequestBody Map<String, String> params) {
        Survey s = surveyService.updateSurveyStatus(surveyId, params);
        return ResponseEntity.ok(s);
    }

    @GetMapping("/admin/surveys/{surveyId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> getSurveyDetail(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyService.getSurveyDetail(surveyId));
    }

    @GetMapping("/admin/surveys/{surveyId}/results")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> getSurveyResults(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyService.getSurveyResults(surveyId));
    }

    // --- USER (Cư dân) ---
    @GetMapping("/users/surveys")
    @PreAuthorize("hasAuthority('RESIDENT')")
    public ResponseEntity<List<Survey>> getAvailableSurveysForUser() {
        return ResponseEntity.ok(surveyService.getAvailableSurveysForUser());
    }

    @GetMapping("/users/surveys/{surveyId}")
    @PreAuthorize("hasAuthority('RESIDENT')")

    public ResponseEntity<Map<String, Object>> getSurveyDetailForUser(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyService.getSurveyDetailForUser(surveyId));
    }    @PostMapping("/users/surveys/{surveyId}/respond")
    @PreAuthorize("hasAuthority('RESIDENT')")
    public ResponseEntity<?> respondSurvey(@PathVariable Long surveyId, @RequestBody Map<String, Object> params) {
        boolean ok = surveyService.respondSurvey(surveyId, params);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("Invalid or duplicate response");
    }
}
