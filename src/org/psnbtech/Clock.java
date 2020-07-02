package org.psnbtech;

/**
 * The {@code Clock} class is responsible for tracking the number of cycles
 * that have elapsed over time. 
 * @author Brendan Jones
 *
 */

package Hallo;

public class Clock {
	
	/**
	 * The number of milliseconds that make up one cycle.
	 */
	private float MillisproZyklus;
	
	/**
	 * The last time that the clock was updated (used for calculating the
	 * delta time).
	 */
	private long letztesUpdate;
	
	/**
	 * The number of cycles that have elapsed and have not yet been polled.
	 */
	private int verstricheneZyklen;
	
	/**
	 * The amount of excess time towards the next elapsed cycle.
	 */
	private float überschüssigeZyklen;
	
	/**
	 * Whether or not the clock is paused.
	 */
	private boolean istangehalten;
	
	/**
	 * Creates a new clock and sets it's cycles-per-second.
	 * @param cyclesPerSecond The number of cycles that elapse per second.
	 */
	public Clock(float ZyklenproSekunde) {
		setZyklenproSekunde(ZyklenproSekunde);
		zurücksetzen();
	}
	
	/**
	 * Sets the number of cycles that elapse per second.
	 * @param cyclesPerSecond The number of cycles per second.
	 */
	public void setZyklenproSekunde(float ZyklenproSekunde) {
		this.MillisproZyklus = (1.0f / ZyklenproSekunde) * 1000;
	}
	
	/**
	 * Resets the clock stats. Elapsed cycles and cycle excess will be reset
	 * to 0, the last update time will be reset to the current time, and the
	 * paused flag will be set to false.
	 */
	public void zurücksetzen() {
		this.verstricheneZyklen = 0;
		this.überschüssigeZyklen = 0.0f;
		this.letztesUpdate = getaktuelleZeit();
		this.istangehalten = false;
	}
	
	/**
	 * Updates the clock stats. The number of elapsed cycles, as well as the
	 * cycle excess will be calculated only if the clock is not paused. This
	 * method should be called every frame even when paused to prevent any
	 * nasty surprises with the delta time.
	 */
	public void update() {
		//Get the current time and calculate the delta time.
		long aktuellesUpdate = getaktuelleZeit();
		float delta = (float)(aktuellesUpdate - letztesUpdate) +  überschüssigeZyklen;
		
		//Update the number of elapsed and excess ticks if we're not paused.
		if(!istangehalten) {
			this.verstricheneZyklen += (int)Math.floor(delta / MillisproZyklus);
			this.überschüssigeZyklen = delta % MillisproZyklus;
		}
		
		//Set the last update time for the next update cycle.
		this.letztesUpdate = aktuellesUpdate;
	}
	
	/**
	 * Pauses or unpauses the clock. While paused, a clock will not update
	 * elapsed cycles or cycle excess, though the {@code update} method should
	 * still be called every frame to prevent issues.
	 * @param paused Whether or not to pause this clock.
	 */
	public void setPausiert(boolean istangehalten) {
		this.istangehalten = istangehalten;
	}
	
	/**
	 * Checks to see if the clock is currently paused.
	 * @return Whether or not this clock is paused.
	 */
	public boolean istangehalten() {
		return istangehalten;
	}
	
	/**
	 * Checks to see if a cycle has elapsed for this clock yet. If so,
	 * the number of elapsed cycles will be decremented by one.
	 * @return Whether or not a cycle has elapsed.
	 * @see peekElapsedCycle
	 */
	public boolean verstricheneZyklenElapsedCycle() {
		if(verstricheneZyklen > 0) {
			this.verstricheneZyklen--;
			return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if a cycle has elapsed for this clock yet. Unlike
	 * {@code hasElapsedCycle}, the number of cycles will not be decremented
	 * if the number of elapsed cycles is greater than 0.
	 * @return Whether or not a cycle has elapsed.
	 * @see hasElapsedCycle
	 */
	public boolean peekverstricheneZyklen() {
		return (verstricheneZyklen > 0);
	}
	
	/**
	 * Calculates the current time in milliseconds using the computer's high
	 * resolution clock. This is much more reliable than
	 * {@code System.getCurrentTimeMillis()}, and quicker than
	 * {@code System.nanoTime()}.
	 * @return The current time in milliseconds.
	 */
	private static final long getaktuelleZeit() {
		return (System.nanoTime() / 1000000L);
	}

}

