/**
 * Description: Description goes here.
 * 
 * @author <a href="mailto:xxx@xxx">xxx</a>
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class PercolationStats {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		System.out.println("mean = " + p.mean());
		System.out.println("stddev = " + p.stddev());
		System.out.println("conf = " + p.confidenceLo() + ", " + p.confidenceHi());
	}

	private final double _mean;

	private final double _stddev;

	private final double _lo;

	private final double _hi;

	private final double[] _thresholds;

	/**
	 * @param N
	 * @param T
	 */
	// perform T independent
	// computational experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		validateInput(N, T);
		_thresholds = new double[T];

		for (int i = 0; i < T; i++) {
			final Percolation p = new Percolation(N);
			int counter = 0;
			while (!p.percolates()) {
				int x = 1 + StdRandom.uniform(N);
				int y = 1 + StdRandom.uniform(N);
				while (p.isOpen(x, y)) {
					x = 1 + StdRandom.uniform(N);
					y = 1 + StdRandom.uniform(N);
				}
				p.open(x, y);
				counter++;
			}
			_thresholds[i] = (double) counter / (N * N);
		}

		_mean = StdStats.mean(_thresholds);
		_stddev = StdStats.stddev(_thresholds);
		_lo = _mean - 1.96 * _stddev / Math.sqrt(T);
		_hi = _mean + 1.96 * _stddev / Math.sqrt(T);

	}

	// returns upper bound of the 95% confidence interval
	/**
	 * @return conf hi
	 */
	public double confidenceHi() {
		return _hi;
	}

	// returns lower bound of the 95% confidence interval
	/**
	 * @return conf lo
	 */
	public double confidenceLo() {
		return _lo;
	}

	/**
	 * @return mean
	 */
	// sample mean of percolation threshold
	public double mean() {
		return _mean;
	}

	// sample standard deviation of percolation threshold
	/**
	 * @return stddev
	 */
	public double stddev() {
		return _stddev;
	}

	/**
	 * @param i
	 * @param j
	 */
	private void validateInput(int i, int j) {
		if (i <= 0 || j <= 0) {
			throw new IllegalArgumentException();
		}
	}

}
