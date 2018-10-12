package univ.ust.tae.data;

import java.io.Serializable;
import java.util.Date;

public class AirPollutionPK implements Serializable {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected Date dataTime;
	protected String sidoName;

	public AirPollutionPK() {
	}

	public AirPollutionPK(Date dataTime, String sidoName) {
		this.dataTime = dataTime;
		this.sidoName = sidoName;
	}

}
