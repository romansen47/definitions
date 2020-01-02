public class AspectsController {

	private static AspectsController instance;

	public static AspectsController getInstance() {
		if (instance == null) {
			instance = new AspectsController();
		}
		return instance;
	}

	private Boolean running;

	private AspectsController() {

	}

	/**
	 * @return the running
	 */
	public Boolean getRunning() {
		return this.running;
	}

	/**
	 * @param running the running to set
	 */
	public void setRunning(final Boolean running) {
		this.running = running;
	}

}
