package lee.andyfxq.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "stocks")
public class Stock {
	static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	static final DecimalFormat f = new DecimalFormat("##.00000000");
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long cid;

	private String symbol;
	private String currency;
	private int volume;
	private BigDecimal price;
	private String px_str;
	private String time_purchased;
	private long time_date;
	
	public Stock() {
		this.time_purchased = dtf.format(LocalDateTime.now());
		this.time_date = System.nanoTime();		
	}
	
	public Stock(Long cid, String symbol, String currency, int volume, BigDecimal price) {
		this.cid = cid;
		this.symbol = symbol;
		this.currency = currency;
		this.volume = volume;
		this.price = price;
		this.px_str = f.format(price);
		this.time_purchased = dtf.format(LocalDateTime.now());
		this.time_date = System.nanoTime();
	}
	
	public Stock(@JsonProperty("id") Long id, @JsonProperty("cid") Long cid, 
			@JsonProperty("symbol") String symbol, @JsonProperty("currency") String currency,
			@JsonProperty("volume") int volume, 
			@JsonProperty("price") BigDecimal price, @JsonProperty("px_str") String px_str,
			@JsonProperty("time_purchased") String time_purchased, @JsonProperty("time_date") long time_date) {
		super();
		this.id = id;
		this.cid = cid;
		this.symbol = symbol;
		this.currency = currency;
		this.volume = volume;
		this.price = price;
		this.px_str = px_str;
		this.time_purchased = time_purchased;
		this.time_date = time_date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getPx_str() {
		return px_str;
	}
	public void setPx_str(String px_str) {
		this.px_str = px_str;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public long getTime_date() {
		return time_date;
	}
	public String getTime_purchased() {
		return time_purchased;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", cid=" + cid + ", symbol=" + symbol + ", currency=" + currency + ", volume="
				+ volume + ", price=" + price + ", px_str=" + px_str + ", time_purchased=" + time_purchased
				+ ", time_date=" + time_date + "]";
	}
	
	

}
