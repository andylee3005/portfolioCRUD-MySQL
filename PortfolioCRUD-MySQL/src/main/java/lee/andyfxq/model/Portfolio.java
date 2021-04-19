package lee.andyfxq.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="portfolios")
public class Portfolio {
	public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String pName;
	private String currency;
	private Long cid;
	private String updated;
//	private long updatedTime;
	
	public Portfolio() {
		this.updated = dtf.format(LocalDateTime.now());
//		this.updatedTime = System.nanoTime();
	}
	
	public Portfolio(Long cid, String pName, String currency) {
		this.cid = cid;
		this.pName = pName;
		this.currency = currency;
		this.updated = dtf.format(LocalDateTime.now());
//		this.updatedTime = System.nanoTime();
	}
	
	public Portfolio(Long id, Long cid, String pName, String currency) {
		this.id = id;
		this.cid = cid;
		this.pName = pName;
		this.currency = currency;
		this.updated = dtf.format(LocalDateTime.now());
//		this.updatedTime = System.nanoTime();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getUpdated() {
		return updated;
	}
	
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "Portfolio [pName=" + pName + ", currency=" + currency + ", cid=" + cid
				+ ", updated=" + updated + "]";
	}

//	public long getUpdated_time() {
//		return updatedTime;
//	}

	
	
}
