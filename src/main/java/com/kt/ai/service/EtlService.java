package com.kt.ai.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonMetadataGenerator;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EtlService {
	private final VectorStore vectorStore;
	
	@Value("classpath:students.json")
	private Resource jsonResource;
	
	@Value("classpath:springai.txt")
	private Resource textResource;
	
	public List<Document> loadJsonAsDocuments() {
        JsonReader jsonReader = new JsonReader(jsonResource, new JsonMetadataGenerator() {
			@Override
			public Map<String, Object> generate(Map<String, Object> jsonMap) {
				return Map.of("Type", "Student grades");
			}
		}, "name","grade");
        return jsonReader.get();
	}
	
	public void importStudentsAsJson() {
		vectorStore.write(loadJsonAsDocuments());
	}
	
	public List<Document> searchJsonData(String queryStr){
		return vectorStore.similaritySearch(
				SearchRequest.query(queryStr)
                );
	}
	
	public List<Document> loadTextAsDocuments() {
		TextReader textReader = new TextReader(textResource);
		textReader.getCustomMetadata().put("ITHOME", "16th");
        return textReader.get();
	}
	
	public void importText() {
		TokenTextSplitter splitter = new TokenTextSplitter();
		vectorStore.write(splitter.split(loadTextAsDocuments()));
	}
	
	public List<Document> searchTextData(String queryStr){
		return vectorStore.similaritySearch(
				SearchRequest.query(queryStr)
                );
	}
	
}
