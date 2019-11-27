package com.dinesh.org.SocialInteractionManagementApp.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dinesh.org.SocialInteractionManagementApp.dao.WebsiteDao;
import com.dinesh.org.SocialInteractionManagementApp.exception.DuplicateWebsiteException;
import com.dinesh.org.SocialInteractionManagementApp.model.ExportWebsite;
import com.dinesh.org.SocialInteractionManagementApp.model.Website;

@Service
public class WebsiteServiceImpl implements WebsiteService
{

	@Autowired
	private WebsiteDao webSiteDao;
	static Logger logger = Logger.getLogger(WebsiteServiceImpl.class);

	@Override
	public Boolean addWebSite(Website website) throws DuplicateWebsiteException
	{
		
		if (webSiteDao.findByUrl(website.getUrl()) != null)
		{
			System.out.println(webSiteDao.findByUrl(website.getUrl()).toString());
			throw new DuplicateWebsiteException("Website with same url already added");
		}
		
		return webSiteDao.addWebsite(website);
	}

	@Override
	public void listAllRecords(String fileName)
	{
		List<Website> websites = webSiteDao.listAllRecords();
		Map<String, ExportWebsite> map = new HashMap<String, ExportWebsite>();

		logger.info("List of Website:");

		FileWriter writer;
		CSVPrinter printer;
		try
		{
			for (Website website : websites)
			{
				if (map.containsKey(website.getDomain()))
				{
					ExportWebsite temp = map.get(website.getDomain());
					temp.setSocialScore(temp.getSocialScore() + website.getSocialScore());
					temp.setCount(temp.getCount() + 1);
					map.put(website.getDomain(), temp);
				} else
				{
					ExportWebsite exportWebSite = new ExportWebsite();
					exportWebSite.setDomain(website.getDomain());
					exportWebSite.setCount(1);
					exportWebSite.setSocialScore(website.getSocialScore());
					map.put(website.getDomain(), exportWebSite);
				}
			}

			writer = new FileWriter(fileName);
			printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Domain", "Urls", "Social Score"));

			map.forEach((key, value) ->
			{
				logger.info(value.getDomain() + " " + value.getCount() + " " + value.getSocialScore());
				try
				{
					printer.printRecord(value.getDomain(), value.getCount(), value.getSocialScore());
					printer.flush();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			});

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public boolean deleteWebSite(String url)
	{
		if (webSiteDao.deleteWebsite(url))
			return true;
		return false;
	}
}
