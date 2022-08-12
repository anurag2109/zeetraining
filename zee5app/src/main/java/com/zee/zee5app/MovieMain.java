package com.zee.zee5app;


import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.dto.User;
import com.zee.zee5app.enums.Geners;
import com.zee.zee5app.exceptions.InvalidIdException;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;
import com.zee.zee5app.service.MovieService;
import com.zee.zee5app.service.MovieServiceImpl;

public class MovieMain {

	public static void main(String[] args) {
		MovieService movieService = MovieServiceImpl.getInstance();
		String[] actors = new String[2];
		actors[0] = "Ajay";
		actors[1] = "Kajol";
		String[] language = {"HINDI", "ENGLISH"};
		Geners geners = Geners.ACTION;
		try {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("Enter opration number: ");
				System.out.println("1: insert movie");
				System.out.println("2: getMovieByMovieId ");
				System.out.println("3: getAllMovies");
				System.out.println("4: getAllMoviesByGenre");
				System.out.println("5: getAllMoviesByName");
				System.out.println("6: findByOrderByMovieNameDsc");
				System.out.println("7: deleteMovieByMovieId");
				System.out.println("8: updateMovie");
				System.out.println("9: exit from movie operation");
				int operation = scanner.nextInt();
				switch (operation) {
				case 1:
					Movie res_for_insertion = movieService.insertMovie(new Movie(actors, "Jeet", "Kajol", geners, "Darma", language, 180.9f, "D:\\trailer.mp4"));
					if (res_for_insertion != null) {
						System.out.println("insertion successfull");
					} else {
						System.out.println("entry not inserted");
					}
					break;
				case 2:
					System.out.println("getMovieByMovieId: "+movieService.getMovieByMovieId("Je00000011").get());
					break;
				case 3:
					System.out.println("getAllMovies: "+movieService.getAllMovies().get());
					break;
				case 4:
					System.out.println("getAllMoviesByGenre: "+ movieService.getAllMoviesByGenre(Geners.ACTION.name()).get());
					break;
				case 5:
					System.out.println("getAllMoviesByName: "+ movieService.getAllMoviesByName("Jeet").get());
					break;
				case 6:
					System.out.println("findByOrderByMovieNameDsc: "+ movieService.findByOrderByMovieNameDsc());
					break;
				case 7:
					System.out.println("deleteMovieByMovieId: "+ movieService.deleteMovieByMovieId("Je00000011"));
					break;
				case 8:
					System.out.println("updateMovie: "+ movieService.updateMovie("Je00000011", new Movie(actors, "Jeet", "Kajol", geners, "Darma", language, 180.9f, "D:\\trailer.mp4")).get());
					break;
				case 9:
					return;

				default:
					break;
				}

			}
		
		
		
		
		
		
		
		
		} catch (InvalidIdException | FileNotFoundException | InvalidNameException | UnableToGenerateIdException | NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
