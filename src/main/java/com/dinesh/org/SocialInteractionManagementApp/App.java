package com.dinesh.org.SocialInteractionManagementApp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import com.dinesh.org.SocialInteractionManagementApp.exception.DuplicateWebsiteException;
import com.dinesh.org.SocialInteractionManagementApp.model.Website;
import com.dinesh.org.SocialInteractionManagementApp.service.WebsiteService;
import com.dinesh.org.SocialInteractionManagementApp.util.HibernateUtil;

@ComponentScan(basePackages = "com.dinesh.org.SocialInteractionManagementApp")
@Controller
public class App
{
	@Autowired
	private WebsiteService webSiteService;
	static Logger logger = Logger.getLogger(App.class);
	String fileName = "url_score.csv";
	 
	public static void main(String[] args)
	{
		logger.info("Application statrted");
		ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
		App app = context.getBean(App.class);
		app.initiatCommandLineDisplay();
		((ConfigurableApplicationContext)context).close();
	}

	public void initiatCommandLineDisplay()
	{
		Scanner scanner = new Scanner(System.in);

		int option = 1;

		while (option != 0)
		{
			showOptions();
			option = scanner.nextInt();

			switch (option)
			{
			case 1:
				logger.info("Add Website Option Selected");
				scanner.nextLine();
				System.out.println("Enter URL:");
				String inputUrl = scanner.nextLine();
				Website webSite = null;
				URL url = null;
				try
				{
					url = new URL(inputUrl);
					int score = Integer.parseInt(url.getPath().split(" ")[1]);
					webSite = new Website();
					webSite.setUrl(inputUrl.split(" ")[0]);
					webSite.setDomain(url.getHost().split("www.")[1]);
					webSite.setSocialScore(score);
					if (webSiteService.addWebSite(webSite))
						logger.info("Website added successfully");
				} 
				catch (DuplicateWebsiteException e)
				{
					logger.info("Problem while adding website: "+e.getMessage());
					e.printStackTrace();
				}
				catch (MalformedURLException e)
				{
					logger.info("Problem while creating Url: "+e.getMessage());
					e.printStackTrace();
				} 
				catch (Exception e)
				{
					logger.error("Problem while adding new Website: "+e.getMessage());
					e.printStackTrace();
				}
				break;
			case 2:
				logger.info("Export Website Social Score Option Selected");
				webSiteService.listAllRecords(fileName);
				break;
			case 3:
				logger.info("Delete Website Option Selected");
				scanner.nextLine();
				System.out.println("Enter URL:");
				String deleteInputUrl = scanner.nextLine();
				if(webSiteService.deleteWebSite(deleteInputUrl))
					logger.info("Delete successfull");
				else
					logger.error("Delete Failed");
				break;
			case 0:
				HibernateUtil.shutdown();
				logger.info("Exiting Application");
				break;
			default:
				logger.info("Export Website Social Score Option Selected");
				webSiteService.listAllRecords(fileName);
				break;
			}
		}

		if (scanner != null)
			scanner.close();
	}

	public static void showOptions()
	{
		System.out.println("Enter an option: ");
		System.out.println("1) ADD");
		System.out.println("2) EXPORT");
		System.out.println("3) REMOVE");
		System.out.println("0) To Exit.");
	}
}
