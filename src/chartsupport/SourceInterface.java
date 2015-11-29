package chartsupport;

public interface SourceInterface extends Runnable{

	/**
	 * Interface, whichs supports Source that runs in a
	 * different Thread. The Stream can be stopped or continued.
	 * */
	
	public void setRunning(boolean running);

	void run();

}
