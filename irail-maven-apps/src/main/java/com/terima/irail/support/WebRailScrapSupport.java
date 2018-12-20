/**
 * 
 */
package com.terima.irail.support;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.IntStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.terima.irail.bean.IRailInfo;
import com.terima.irail.bean.RailAvailablity;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vignesh
 *
 */
@Component
@Slf4j
public class WebRailScrapSupport {
	

	public List<IRailInfo> getScrappedIRailScheule(String url) throws Exception {
		
		
		log.debug("Connecting Server -> "+url);
		
		final Document document = Jsoup.connect(url).get();

		final Map<Integer, List<String>> ls = new LinkedHashMap<Integer, List<String>>();
		final Elements elementByTr = document.getElementsByClass("myTable data nocps nolrborder").get(0)
				.getElementsByTag("tbody").get(0).getElementsByTag("tr");
		IntStream.range(0, elementByTr.size()).forEach(i -> {
			final List<String> bodyList = new LinkedList<>();
			final Element element = elementByTr.get(i);
			final Elements cols = element.select("td");
			final ListIterator<Element> it = cols.listIterator();
			while (it.hasNext()) {
				final Element next = it.next();
				final String text = next.text();
				if (!text.isEmpty()) {
					bodyList.add(text);
				}
			}
			ls.put(i, bodyList);

		});

		final List<IRailInfo> iRailInfoList = new LinkedList<>();
		final Set<Entry<Integer, List<String>>> entrySet = ls.entrySet();
		entrySet.stream().forEach(i -> {
			final IRailInfo travel = new IRailInfo();
			final List<String> value = i.getValue();
			final Object[] array = value.toArray();
			travel.setTrainNo(array[0].toString());
			travel.setTrainName(array[1].toString());
			travel.setOrgiStation(array[2].toString());
			travel.setDepatureDt(array[3].toString());
			travel.setDestStation(array[4].toString());
			travel.setArrDt(array[5].toString());
			travel.setTravelTime(array[6].toString());

			final RailAvailablity avail = new RailAvailablity();
			avail.setSunday(array[7].toString());
			avail.setMonday(array[8].toString());
			avail.setTuesady(array[9].toString());
			avail.setWednesday(array[10].toString());
			avail.setThrusday(array[11].toString());
			avail.setFriday(array[12].toString());
			avail.setSaturday(array[13].toString());

			travel.setAvailDays(avail);

			iRailInfoList.add(travel);
		});

		return iRailInfoList;
	}

}
