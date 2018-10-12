package univ.ust.tae.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(AirPollutionPK.class)
@Table(name = "air_pollution")
public class AirPollution implements Serializable {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name = "data_time", nullable = false, unique = true)
	private Date dataTime;

	@Id
	@Column(name = "sido_name", nullable = false, unique = true)
	private String sidoName;

	@Column(name = "so2")
	private Double so2;

	@Column(name = "co")
	private Double co;

	@Column(name = "o3")
	private Double o3;

	@Column(name = "no2")
	private Double no2;

	@Column(name = "pm10")
	private Double pm10;

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public String getSidoName() {
		return sidoName;
	}

	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}

	public Double getSo2() {
		return so2;
	}

	public void setSo2(Double so2) {
		this.so2 = so2;
	}

	public Double getCo() {
		return co;
	}

	public void setCo(Double co) {
		this.co = co;
	}

	public Double getO3() {
		return o3;
	}

	public void setO3(Double o3) {
		this.o3 = o3;
	}

	public Double getNo2() {
		return no2;
	}

	public void setNo2(Double no2) {
		this.no2 = no2;
	}

	public Double getPm10() {
		return pm10;
	}

	public void setPm10(Double pm10) {
		this.pm10 = pm10;
	}

}
