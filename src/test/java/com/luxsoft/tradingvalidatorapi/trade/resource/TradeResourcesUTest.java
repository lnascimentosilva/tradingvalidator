package com.luxsoft.tradingvalidatorapi.trade.resource;

import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.valueDateSpotNotTwoDaysAhead;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidSpot;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.luxsoft.tradingvalidatorapi.common.utils.file.Files;
import com.luxsoft.tradingvalidatorapi.common.utils.property.Properties;
import com.luxsoft.tradingvalidatorapi.common.utils.resource.Resources;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;
import com.luxsoft.tradingvalidatorapi.trade.service.TradeServices;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TradeResources.class, secure = false)
@Import(value={Files.class, Resources.class, Properties.class})
public class TradeResourcesUTest {

	private static final String BASE_RESOURCE_PATH = "/trades";
	private static final String BULK_RESOURCE_PATH = BASE_RESOURCE_PATH.concat("/bulk");

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private Files files; 
	
	@MockBean
	private TradeServices tradeServices;

	@Test
	public void validateSingleValidSpotTrade() throws Exception {

		byte[] inputBody = files.getFileContent("input.trade.singlevalidspot");

		mockMvc.perform(post(BASE_RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON).content(inputBody))
				.andDo(print())
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void validateSingleInvalidSpotTrade() throws Exception {
		
		when(tradeServices.validate(any(Trade.class))).thenReturn(Optional.of(asList(valueDateSpotNotTwoDaysAhead())));

		byte[] inputBody = files.getFileContent("input.trade.singleinvalidspot");
		byte[] responseBody = files.getFileContent("output.trade.singleinvalidspot");

		mockMvc.perform(post(BASE_RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON).content(inputBody))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().json(new String(responseBody)));
	}
	
	@Test
	public void validateListOfValidTrades() throws Exception {

		byte[] inputBody = files.getFileContent("input.trade.validlist");

		mockMvc.perform(post(BULK_RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON).content(inputBody))
				.andDo(print())
				.andExpect(status().isNoContent());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void validateListOfInvalidSpotTrades() throws Exception {
		Trade invalidSpot1 = invalidSpot();
		invalidSpot1.setErrors(asList(valueDateSpotNotTwoDaysAhead()));
		Trade invalidSpot2 = invalidSpot();
		invalidSpot2.setCustomer("PLUTO2");
		invalidSpot2.setErrors(asList(valueDateSpotNotTwoDaysAhead()));
		
		when(tradeServices.validate(any(List.class))).thenReturn(asList(invalidSpot1, invalidSpot2));

		byte[] inputBody = files.getFileContent("input.trade.listinvalidspot");
		byte[] responseBody = files.getFileContent("output.trade.listinvalidspot");

		mockMvc.perform(post(BULK_RESOURCE_PATH).contentType(MediaType.APPLICATION_JSON).content(inputBody))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().json(new String(responseBody)));
	}

}
