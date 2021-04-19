package lee.andyfxq.service;

import java.util.List;
//import java.util.List;
import java.util.Optional;

import lee.andyfxq.model.Portfolio;
import lee.andyfxq.model.Stock;

public interface PortfolioService {
	List<Portfolio> getAll();
	List<Portfolio> getByClientId(Long id);
	Optional<Portfolio> getById(Long id);
	Portfolio _save(Portfolio portfolio);
	void removeById(Long id);
	
	List<Stock> getStocksByPortfolioId(Long id);
	
	void addRel(Long cid, Long pid, Long sid);
	void removeRel(Long pid, Long sid);
}
