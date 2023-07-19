/**
 * Sparta Software Co.
 * 2016
 */
package org.sparta.springwebutils.jdbc;

/**
 * @author Daniel Conde Diehl - Sparta Technology
 *
 * History:
 *  May 31, 2016 - Daniel Conde Diehl
 */

public class TestRangeObj<T> {
	private T min;
	private T max;
	/**
	 * @return the min
	 */
	public T getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(T min) {
		this.min = min;
	}
	/**
	 * @return the max
	 */
	public T getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(T max) {
		this.max = max;
	}
}
