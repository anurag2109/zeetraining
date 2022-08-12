package com.zee.zee5app.dto;

import javax.naming.InvalidNameException;

import com.zee.zee5app.enums.Geners;
import com.zee.zee5app.enums.Languages;
import com.zee.zee5app.exceptions.InvalidIdException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class WebSeries {
	
	
	public WebSeries(String[] actors, String webSeriesName, String director, Geners genre, String production,
			String[] languages, int episodes, String trailer1) throws InvalidNameException {
		super();
		this.setActors(actors);
		this.setWebSeriesName(webSeriesName);
		this.setDirector(director);
		this.setGenre(genre);
		this.setProduction(production);
		this.setLanguages(languages);
		this.setEpisodes(episodes);
		this.setTrailer1(trailer1);
	}

	public void setWebSeriesId(String webSeriesId) throws InvalidIdException {
		int len = webSeriesId.length();
		if(len == 10)
			this.webSeriesId = webSeriesId;
		else
			throw new InvalidIdException("Id must be of size 10");
	}

	public void setActors(String[] actors) {
		this.actors = actors;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public void setGenre(Geners genre) {
		this.genre = genre;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}

	public void setWebSeriesName(String webSeriesName) throws InvalidNameException {
		if(webSeriesName =="" || webSeriesName == null || webSeriesName.length() < 3) {			
			throw new InvalidNameException("Invalid movie name !!");
		}else {
			this.webSeriesName = webSeriesName;
		}
	}
	
	public void setLanguages(String[] languages) throws InvalidNameException {
		int count = 0;
		for (String string : languages) {
//			System.out.println(Languages.valueOf(string));
			for(Languages l : Languages.values()) {
				if(Languages.valueOf(string).compareTo(l)== 0) {
					count++;
				}
			}
		}
		if(count == languages.length)
			this.languages = languages;
		else
			throw new InvalidNameException(director);
	}
	
	public void setTrailer1(String trailer1) {
		this.trailer1 = trailer1;
	}
	public WebSeries() {
		
	}
	private String webSeriesId;
    private String actors[];
    private String webSeriesName;
    private String director;
    private Geners genre;
    private String production;
	private String languages[];
    private int episodes;
    private String trailer1;
}
