package pl.cywek.chair;

import pl.cywek.chair.model.DataModel;

/**
 * Movements based on fotel.pdf - > link
 * 
 * @author kcywinski
 *
 */
public class Chair {

	public static final String HOST = "http://pawelszczerbicki.pl:8080/armchair/rest/message/device";//"http://pawelszczerbicki.pl:8080/rest/message/device";
	
	private  DataModel mModel = new DataModel();
	private static Chair instance = new Chair();
    
	public void calibrate() {
		new AtmosphereTask().execute("", "CALIBRATE", "");
	}

    public static Chair getInstance() {
        return instance;
    }
    
    public void setSeekbars() {
    	
    }

    public void setElement(String elementCode, String value) {
    	if (elementCode.equals("PF")) {
    		mModel.setmPF(Integer.parseInt(value));
    	} else if (elementCode.equals("PO")) {
    		mModel.setmPO(Integer.parseInt(value));
    	} else if (elementCode.equals("SP")) {
    		mModel.setmSP(Integer.parseInt(value));
    	} else if (elementCode.equals("UG")) {
    		mModel.setmUG(Integer.parseInt(value));
    	} else if (elementCode.equals("ZG")) {
    		mModel.setmZG(Integer.parseInt(value));
    	} else if (elementCode.equals("WF")) {
    		mModel.setmWF(Integer.parseInt(value));
    	} else if (elementCode.equals("GG")) {
    		mModel.setmGG(Integer.parseInt(value));
    	} else if (elementCode.equals("GW")) {
    		mModel.setmGW(Integer.parseInt(value));
    	} else if (elementCode.equals("PZ")) {
    		mModel.setmPZ(Integer.parseInt(value));
    	}
    }
    
	public DataModel getmModel() {
		return mModel;
	}

	public void setmModel(DataModel mModel) {
		this.mModel = mModel;
	}

	/**
	 * Pochylenie przedniej części fotela góra/dół.
	 */
	public void pf(String radius) {
		mModel.setmPF(Integer.parseInt(radius));
		new AtmosphereTask().execute(radius, "ACTION", "PF");
	}

	/**
	 * Pochylenie oparcia fotela.
	 */
	public  void po(String radius) {
		mModel.setmPO(Integer.parseInt(radius));
		new AtmosphereTask().execute(radius, "ACTION", "PO");
	}

	/**
	 * Przesunięcie całego fotela przód/tył.
	 */
	public  void sp(String pos) {
		mModel.setmSP(Integer.parseInt(pos));
		new AtmosphereTask().execute(pos, "ACTION", "SP");
		
	}

	/**
	 * Pochylenie tylnej części siedziska.
	 */
	public  void ug(String radius) {
		mModel.setmUG(Integer.parseInt(radius));
		new AtmosphereTask().execute(radius, "ACTION", "UG");
	}

	/**
	 * Wysunięcie zagłówka góra/dół.
	 */
	public  void zg(String pos) {
		mModel.setmZG(Integer.parseInt(pos));
		new AtmosphereTask().execute(pos, "ACTION", "ZG");
		
	}

	/**
	 * Wydłużenie siedziska fotela przód/tył.
	 */
	public  void wf(String width) {
		mModel.setmWF(Integer.parseInt(width));
		new AtmosphereTask().execute(width, "ACTION", "WF");
	}

	/**
	 * Przesunięcie wybrzuszenia oparcia pod kręgosłupem w położeniu góra/dół.
	 */
	public  void gg(String radius) {
		mModel.setmGG(Integer.parseInt(radius));
		new AtmosphereTask().execute(radius, "ACTION", "GG");
	}

	/**
	 * Wybrzuszenie oparcia pod kręgosłupem.
	 */
	public  void gw(String radius) {
		mModel.setmGW(Integer.parseInt(radius));
		new AtmosphereTask().execute(radius, "ACTION", "GW");
	}

	/**
	 * Pochylenie zagłówka.
	 */
	public  void pz(String radius) {
		mModel.setmPZ(Integer.parseInt(radius));
		new AtmosphereTask().execute(radius, "ACTION", "PZ");
	}
}
