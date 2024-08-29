package com.kt.ai.controller;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.ai.service.EtlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ai/etl")
@RequiredArgsConstructor
public class EtlController {
	
	private final EtlService etlService;
	
	@GetMapping("readjson")
	public List<Document> readJsonFile(){
		return etlService.loadJsonAsDocuments();
	}
	
	@GetMapping("importjson")
	public void importJson(){
		etlService.importStudentsAsJson();
	}
	
	@GetMapping("searchjson")
	public List<Document> searchJson(String query){
		return etlService.searchJsonData(query);
	}

	@GetMapping("readtext")
	public List<Document> readTextFile(){
		return etlService.loadTextAsDocuments();
	}
	
	@GetMapping("importtext")
	public void importText(){
		etlService.importText();
	}
	@GetMapping("searchtext")
	public List<Document> searchText(String query){
		return etlService.searchTextData(query);
	}
}
