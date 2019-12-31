public class AspectsController {

	private static AspectsController instance;

	private Boolean running;

	private AspectsController() {

	};

	public static AspectsController getInstance() {
		if (instance == null) {
			instance = new AspectsController();
		}
		return instance;
	}

	/**
	 * @return the running
	 */
	public Boolean getRunning() {
		return running;
	}

	/**
	 * @param running the running to set
	 */
	public void setRunning(Boolean running) {
		this.running = running;
	}

}
