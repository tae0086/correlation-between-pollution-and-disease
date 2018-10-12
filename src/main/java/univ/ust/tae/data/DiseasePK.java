package univ.ust.tae.data;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("unused")
public class DiseasePK implements Serializable {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private Date date;
	private String sido;

	public DiseasePK() {
	}

	public DiseasePK(String name, Date date, String sido) {
		this.name = name;
		this.date = date;
		this.sido = sido;
	}

}
