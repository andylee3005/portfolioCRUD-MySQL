package lee.andyfxq.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lee.andyfxq.model.Portfolio;
import lee.andyfxq.model.Stock;
import lee.andyfxq.service.PortfolioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/PORT")
public class PortfolioController {
	
	private static final Logger logger = LoggerFactory.getLogger(PortfolioController.class);
	
	@Autowired
	@Qualifier("portfService")
	PortfolioService portfService;
	
	@PostMapping("/create")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Portfolio> createPortfolio(@RequestBody Portfolio portf) {
		try {
			Portfolio _portf = portfService._save(new Portfolio(
					portf.getCid(), portf.getpName(), portf.getCurrency()
					));
			return new ResponseEntity<>(_portf, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/list")
//	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Portfolio> requestPortfolios() {
		return portfService.getAll();
	}
	
	@GetMapping("/id/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Optional<Portfolio> requestById(@PathVariable Long id) {
		return portfService.getById(id);
	}
	
	@GetMapping("/cid/{cid}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Portfolio> requestByClientId(@PathVariable Long cid) {
		List<Portfolio> res = portfService.getByClientId(cid);
//		logger.info("Request by client ID:{} response :{}", cid, Arrays.toString(res.toArray()));
		return res;
	}

	@GetMapping("/pid/{pid}")
//	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Stock> getStocksByPortfolioId(@PathVariable Long pid) {
		return portfService.getStocksByPortfolioId(pid);
	}
	
	@PutMapping("/edit/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Portfolio> editPortfolio(@PathVariable Long id, @RequestBody Portfolio portf) {
		Optional<Portfolio> portfData = portfService.getById(id);
		
		if (portfData.isPresent()) {
			Portfolio _portf = portfData.get();
			_portf.setpName(portf.getpName());
			_portf.setCurrency(portf.getCurrency());
			_portf.setUpdated(Portfolio.dtf.format(LocalDateTime.now()));
			
			return new ResponseEntity<>(portfService._save(_portf), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/edit/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deletePortfolio(@PathVariable Long id) {
		try {
			portfService.removeById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/rel/add/{sid}")
	public ResponseEntity<HttpStatus> addRelationship(@PathVariable Long sid, @RequestBody Portfolio portf) {
		try {
			portfService.addRel(portf.getCid(), portf.getId(), sid);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/rel/del/{sid}")
	public ResponseEntity<HttpStatus> removeRelationship(@PathVariable Long sid, @RequestBody Portfolio portf) {
		try {
			portfService.removeRel(portf.getId(), sid);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}