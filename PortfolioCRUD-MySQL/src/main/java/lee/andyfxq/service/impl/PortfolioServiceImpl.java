package lee.andyfxq.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lee.andyfxq.model.Portfolio;
import lee.andyfxq.model.Stock;
import lee.andyfxq.repository.PortfolioRepository;
import lee.andyfxq.service.PortfolioService;

@Service("portfService")
public class PortfolioServiceImpl implements PortfolioService {
	private static final Logger logger = LoggerFactory.getLogger(PortfolioServiceImpl.class);
	
	@Autowired
	PortfolioRepository portRepo;
	
	final String STOCKURL = "http://localhost:4985/STOCK";
	
	HttpEntity<String> setHeader(String jwt) {
		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", jwt);
		return new HttpEntity<String>(headers);
	}
	
	@Override
	public List<Portfolio> getAll() {
		return (List<Portfolio>) portRepo.findAll();
	}
	
	@Override
	public List<Portfolio> getByClientId(Long client_id) {
		return portRepo.findByCid(client_id);
	}

	@Override
	public Optional<Portfolio> getById(Long id) {
		return portRepo.findById(id);
	}

	@Override
	public Portfolio _save(Portfolio portfolio) {
		return portRepo.save(portfolio);
	}

	@Override
	public void removeById(Long id) {
		logger.info("Preparing to delete portfolio with ID: {}", id);
		portRepo.deleteRelationsPortfolioId(id);
		portRepo.deleteById(id);
	}

	@Override
	public List<Stock> getStocksByPortfolioId(Long id) {
		logger.info("Getting stocks with Portfolio ID: " + id);
		List<Long> stockIds = portRepo.findStockIdsByPortfolioId(id);
		List<Stock> stocks = new ArrayList<>();
		RestTemplate restTemp = new RestTemplate();
		for (Long sid:stockIds) {
			HttpEntity<String> request = new HttpEntity<>("");
			logger.info(STOCKURL + "/id/" + sid);
			ResponseEntity<Stock> response = restTemp.exchange(STOCKURL + "/id/" + sid, HttpMethod.GET, request, Stock.class);
//			if (response.getBody().isPresent()) {
				logger.info(response.getBody().getClass().toString());
				Stock sto = response.getBody();
				stocks.add(sto);
//			}
		}
		return stocks;
	}

	@Override
	public void addRel(Long cid, Long pid, Long sid) {
		portRepo.addRelation(cid, pid, sid);
	}

	@Override
	public void removeRel(Long pid, Long sid) {
		portRepo.removeRelaion(pid, sid);	
	}
	
//	@Override
//	public List<Stock> getStocksByPortfolioId(Long id) {
//		List<Long> stockIds = portRepo.findStockIdsByPortfolioId(id);
//		List<Stock> stocks = new ArrayList<>();
//		RestTemplate restTemp = new RestTemplate();
//		for (Long sid:stockIds) {
//			HttpEntity<String> request = new HttpEntity<>("");
//			ResponseEntity<Optional> response = restTemp.exchange(STOCKURL + "/id/" + sid, HttpMethod.GET, request, Optional.class);
//			if (response.getBody().isPresent()) {
//				stocks.add( (Stock) response.getBody().get());
//			}
//		}
//		return stocks;
//	}
	
}
