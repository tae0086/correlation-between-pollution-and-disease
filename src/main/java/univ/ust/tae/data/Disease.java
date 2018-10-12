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
@IdClass(DiseasePK.class)
@Table(name = "disease")
public class Disease implements Serializable {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false, unique = true)
	private Date date;

	@Id
	@Column(name = "sido", nullable = false, unique = true)
	private String sido;

	@Column(name = "count")
	private Integer count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return name + "[" + date + ", " + sido + ", " + count + "]";
	}

}
