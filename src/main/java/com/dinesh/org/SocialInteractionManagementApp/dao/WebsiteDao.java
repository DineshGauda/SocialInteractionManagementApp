package com.dinesh.org.SocialInteractionManagementApp.dao;

import java.util.List;

import com.dinesh.org.SocialInteractionManagementApp.model.Website;

public interface WebsiteDao
{
	public Boolean addWebsite(Website website);
	
	public List<Website> listAllRecords();

	public Website findByUrl(String url);
	
	public boolean deleteWebsite(String url);
}
