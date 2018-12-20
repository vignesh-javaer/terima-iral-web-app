/**
 * 
 */
package com.terima.irail.resources;

import java.util.List;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.terima.irail.bean.IRailInfo;
import com.terima.irail.constants.IRailConstants;
import com.terima.irail.support.WebRailScrapSupport;

/**
 * @author vignesh
 *
 */
@RestController
@RequestMapping("/trains")
public class IRailScheduleBetweenStations {
	
	//private static final String NVM_QUERY_AND = "&";
	private static final String NVM_QUERY_SEPT = "?";
	private static final String NVM_QUERY_EQUAL = "=";
	private static final String NVM_HYPEN = "-";
	
	@Autowired
	private WebRailScrapSupport irailScrapSupport;
	
	@GetMapping("/test")
	public String getTested() {
		return "Running Successfully...";
	}

	@GetMapping("/schedule")
	public RedirectView getScheduleBetweenStations(@QueryParam("origin") String origin , @QueryParam("destination") String destination, RedirectAttributes redir) {
	
		System.out.println("Getting Info...");
		final String url = IRailConstants.IRAIL_WEB_SITE + NVM_QUERY_SEPT + IRailConstants.IRAIL_BETWEEN_STATION+ NVM_QUERY_EQUAL+origin + NVM_HYPEN + destination;
		
		List<IRailInfo> scrappedIRailScheule = null;
		
		try {
			scrappedIRailScheule = irailScrapSupport.getScrappedIRailScheule(url);
			
			redir.addFlashAttribute("irailInfo", scrappedIRailScheule);
			
			RedirectView redirectView = new RedirectView();
			redirectView.setContextRelative(true);
			redirectView.setUrl("/irail/schedule/inforeceive");
			return redirectView;
		} catch (Exception e) {
			throw new InternalServerErrorException("Internal Server exception occured,");
		}
	}
	
}
