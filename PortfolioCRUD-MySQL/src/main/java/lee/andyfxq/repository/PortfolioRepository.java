package lee.andyfxq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lee.andyfxq.model.Portfolio;

@Repository("portRepo")
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
	
	List<Portfolio> findByCid(Long cid);
	void deleteByCid(Long cid);
	
	@Query(value="SELECT stock_id from portfolio_stock WHERE portfolio_id = :pid", nativeQuery=true)
	List<Long> findStockIdsByPortfolioId(Long pid); //might need to specify database name
	
	@Modifying
	@Transactional
	@Query(value="DELETE from portfolio_stock WHERE portfolio_id = :pid", nativeQuery=true)
	int deleteRelationsPortfolioId(@Param("pid") Long pid);
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO portfolio_stock (`client_id`, `portfolio_id`, `stock_id`) VALUES (:cid, :pid, :sid)", nativeQuery=true)
	void addRelation(Long cid, Long pid, Long sid);
	
	@Modifying
	@Transactional
	@Query(value="DELETE from portfolio_stock WHERE portfolio_id = :pid AND stock_id = :sid", nativeQuery=true)
	void removeRelaion(Long pid, Long sid);
}
