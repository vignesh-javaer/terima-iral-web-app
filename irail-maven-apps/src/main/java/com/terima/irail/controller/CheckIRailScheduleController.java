package com.terima.irail.controller;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.terima.irail.bean.IRailInfo;
import com.terima.irail.constants.IRailConstants;
import com.terima.irail.support.WebRailScrapSupport;

@Controller
@RequestMapping("/schedule")
public class CheckIRailScheduleController {

	private static final String NVM_QUERY_SEPT = "?";
	private static final String NVM_QUERY_EQUAL = "=";
	private static final String NVM_HYPEN = "-";

	@Autowired
	private WebRailScrapSupport irailScrapSupport;

	@RequestMapping("/")
	public ModelAndView getSearchPage() {
		return new ModelAndView("searchtrain");
	}

	@RequestMapping("/info")
	public RedirectView getConnectIRail(@RequestParam("origin") String origin,
			@RequestParam("destination") String destination) {
		RedirectView rview = new RedirectView();
		rview.setUrl("/irail/schedule/inforeceive?origin=" + origin + "&destination=" + destination);
		return rview;
	}

	@RequestMapping("/inforeceive")
	public @ResponseBody String getIRailInfo(@QueryParam("origin") String origin,
			@QueryParam("destination") String destination) {

		System.out.println("Checking Info....");

		final String url = IRailConstants.IRAIL_WEB_SITE + NVM_QUERY_SEPT + IRailConstants.IRAIL_BETWEEN_STATION
				+ NVM_QUERY_EQUAL + origin + NVM_HYPEN + destination;

		List<IRailInfo> scrappedIRailScheule = null;
		try {
			scrappedIRailScheule = irailScrapSupport.getScrappedIRailScheule(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		sb.append(
				"<!DOCTYPE html><html><head><style>table {  width:50%;}table, th, td {  border: 1px solid black;  border-collapse: collapse;}th, td {  padding: 5px;  text-align: left;}table#t01 tr:nth-child(even) {  background-color: #eee;}table#t01 tr:nth-child(odd) { background-color: #fff;}table#t01 th {  background-color: black;  color: white;} table.#t01 {  table-layout: auto;  width: 100%;  }</style></head><body><table id=01>");
		sb.append("<tr><th>TRAIN_NO</th>").append("<th>TRAIN_NAME</th>").append("<th>ORIGIN</th>")
				.append("<th>DEPATURE_AT</th>").append("<th>DESTINATION</th>").append("<th>ARRIVAL_DATE</th>")
				.append("<th>TRAVEL_TIME</th>").append("<th>Mon</th>").append("<th>Tue</th>").append("<th>Wed</th>")
				.append("<th>Thru</th>").append("<th>Fri</th>").append("<th>Sat</th>").append("<th>Sun</th>")
				.append("</tr>");
		for (IRailInfo iRailInfo : scrappedIRailScheule) {
			sb.append("<tr>");
			sb.append("<td>").append(iRailInfo.getTrainNo()).append("</td>");
			sb.append("<td>").append(iRailInfo.getTrainName()).append("</td>");
			sb.append("<td>").append(iRailInfo.getOrgiStation()).append("</td>");
			sb.append("<td>").append(iRailInfo.getDepatureDt()).append("</td>");
			sb.append("<td>").append(iRailInfo.getDestStation()).append("</td>");
			sb.append("<td>").append(iRailInfo.getArrDt()).append("</td>");
			sb.append("<td>").append(iRailInfo.getTravelTime()).append("</td>");
			sb.append("<td>").append(iRailInfo.getAvailDays().getMonday()).append("</td>");
			sb.append("<td>").append(iRailInfo.getAvailDays().getTuesady()).append("</td>");
			sb.append("<td>").append(iRailInfo.getAvailDays().getWednesday()).append("</td>");
			sb.append("<td>").append(iRailInfo.getAvailDays().getThrusday()).append("</td>");
			sb.append("<td>").append(iRailInfo.getAvailDays().getFriday()).append("</td>");
			sb.append("<td>").append(iRailInfo.getAvailDays().getSaturday()).append("</td>");
			sb.append("<td>").append(iRailInfo.getAvailDays().getSunday()).append("</td>");
			sb.append("</tr>");
		}
		sb.append("</table></body></html>");
		return sb.toString();
	}
}
