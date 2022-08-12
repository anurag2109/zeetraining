package com.zee.zee5app;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.Movie;
import com.zee.zee5app.dto.WebSeries;
import com.zee.zee5app.enums.Geners;
import com.zee.zee5app.enums.Languages;
import com.zee.zee5app.exceptions.InvalidIdException;
import com.zee.zee5app.exceptions.NoDataFoundException;
import com.zee.zee5app.exceptions.UnableToGenerateIdException;
import com.zee.zee5app.service.WebSeriesService;
import com.zee.zee5app.service.WebSeriesServiceImpl;

public class WebSeriesMain {

	public static void main(String[] args) {
		WebSeriesService webSeriesService = WebSeriesServiceImpl.getInstance();
		String[] actors = new String[2];
		actors[0] = "Ajay";
		actors[1] = "Kajol";
		String[] language = { "HINDI", "ENGLISH" };
		Geners geners = Geners.ACTION;
		try {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("Enter opration number: ");
				System.out.println("1: insert webSeries");
				System.out.println("2: getwebSeriesBywebSeriesId ");
				System.out.println("3: getAllwebSeries");
				System.out.println("4: getAllwebSeriesByGenre");
				System.out.println("5: getAllwebSeriesByName");
				System.out.println("6: findByOrderBywebSeriesNameDsc");
				System.out.println("7: deletewebSeriesBywebSeriesId");
				System.out.println("8: updatewebSeries");
				System.out.println("9: exit from webSeries operation");
				int operation = scanner.nextInt();
				switch (operation) {
				case 1:
					WebSeries res_for_insertion = webSeriesService.insertWebSeries(
							new WebSeries(actors, "Jeet", "Kajol", geners, "Darma", language, 3, "D:\\trailer.mp4"));
					if (res_for_insertion != null) {
						System.out.println("insertion successfull");
					} else {
						System.out.println("entry not inserted");
					}
					break;
				case 2:
					System.out.println("getWebSeriesByWebSeriesId: "
							+ webSeriesService.getWebSeriesByWebSeriesId("gh00000015").get());
					break;
				case 3:
					System.out.println("getAllWebSeriess: " + webSeriesService.getAllWebSeriess().get());
					break;
				case 4:
					System.out.println("getAllWebSeriessByGenre: "
							+ webSeriesService.getAllWebSeriessByGenre(Geners.ACTION.name()).get());
					break;
				case 5:
					System.out.println(
							"getAllWebSeriessByName: " + webSeriesService.getAllWebSeriessByName("Jeet").get());
					break;
				case 6:
					System.out.println(
							"findByOrderByWebSeriesNameDsc: " + webSeriesService.findByOrderByWebSeriesNameDsc());
					break;
				case 7:
					System.out.println("deleteWebSeriesByWebSeriesId: "
							+ webSeriesService.deleteWebSeriesByWebSeriesId("gh00000015"));
					break;
				case 8:
					System.out.println("updateWebSeries: " + webSeriesService.updateWebSeries("gh00000015",
							new WebSeries(actors, "Jeet", "Kajol", geners, "Darma", language, 3, "D:\\trailer.mp4"))
							.get());
					break;
				case 9:
					return;

				default:
					break;
				}

			}

		} catch (InvalidIdException | FileNotFoundException | InvalidNameException | UnableToGenerateIdException
				| NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
