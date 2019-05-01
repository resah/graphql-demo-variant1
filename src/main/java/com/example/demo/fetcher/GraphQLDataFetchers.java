package com.example.demo.fetcher;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.remote.GhibliApi;
import com.example.demo.remote.Person;
import com.example.demo.remote.Species;
import com.google.common.collect.ImmutableMap;

import graphql.schema.DataFetcher;
import retrofit2.Call;

@Component
public class GraphQLDataFetchers {
	
	GhibliApi ghibliApi;
	
	GraphQLDataFetchers(GhibliApi ghibliApi) {
		this.ghibliApi = ghibliApi;
	}
	
    private static List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1",
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2",
                    "name", "Moby Dick",
                    "pageCount", "635",
                    "authorId", "author-2"),
            ImmutableMap.of("id", "book-3",
                    "name", "Interview with the vampire",
                    "pageCount", "371",
                    "authorId", "author-3")
    );

    private static List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1",
                    "firstName", "Joanne",
                    "lastName", "Rowling"),
            ImmutableMap.of("id", "author-2",
                    "firstName", "Herman",
                    "lastName", "Melville"),
            ImmutableMap.of("id", "author-3",
                    "firstName", "Anne",
                    "lastName", "Rice")
    );

    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getBonkersDataFetcher() {
        return dataFetchingEnvironment -> {
            String personId = dataFetchingEnvironment.getArgument("id");
            Call<Person> person = ghibliApi.getPerson(personId);
            return person.execute().body();
        };
    }

    public DataFetcher getSpeciesDataFetcher() {
        return dataFetchingEnvironment -> {
//            Boolean toggle = dataFetchingEnvironment.getArgument("id");
            Person person = dataFetchingEnvironment.getSource();
            String speciesRef = person.getSpecies();
            
            URI uri = new URI(speciesRef);
            String[] segments = uri.getPath().split("/");
            String speciesId = segments[segments.length-1];
            
            Call<Species> species = ghibliApi.getSpecies(speciesId);
            return species.execute().body();
        };
    }
    
}
