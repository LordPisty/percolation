/**
 * Description: Description goes here.
 * 
 * @author <a href="mailto:xxx@xxx">xxx</a>
 * @version $Revision$ $Date$
 * @since 0.1
 */
public class Percolation {

	private final int size;

	private final boolean[][] _sites;

	private final WeightedQuickUnionUF _quickFind;

	private final WeightedQuickUnionUF _quickFindF;

	/**
	 * @param N grid size
	 */
	// create N-by-N grid, with all sites blocked
	public Percolation(final int N) {
		size = N;
		_sites = new boolean[N][N];
		_quickFind = new WeightedQuickUnionUF(size * size + 2);
		_quickFindF = new WeightedQuickUnionUF(size * size + 1);

	}

	/**
	 * @param i x
	 * @param j y
	 * @return whether the site is full
	 */
	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		validateInput(i, j);
		return _quickFindF.connected(0, translate(i, j));
	}

	/**
	 * @param i x
	 * @param j y
	 * @return whether the site is open
	 */
	// is site (row i, column j) open?
	public boolean isOpen(final int i, final int j) {
		validateInput(i, j);
		return _sites[i - 1][j - 1];
	}

	/**
	 * @param i x
	 * @param j y
	 */
	// open site (row i, column j) if it is not already
	public void open(final int i, final int j) {
		validateInput(i, j);
		if (!isOpen(i, j)) {
			_sites[i - 1][j - 1] = true;
			if (i == 1) {
				_quickFind.union(translate(i, j), 0);
				_quickFindF.union(translate(i, j), 0);
			}
			if (i - 1 > 0 && isOpen(i - 1, j)) {
				_quickFind.union(translate(i, j), translate(i - 1, j));
				_quickFindF.union(translate(i, j), translate(i - 1, j));
			}
			if (i + 1 <= size && isOpen(i + 1, j)) {
				_quickFind.union(translate(i, j), translate(i + 1, j));
				_quickFindF.union(translate(i, j), translate(i + 1, j));
			}
			if (j - 1 > 0 && isOpen(i, j - 1)) {
				_quickFind.union(translate(i, j), translate(i, j - 1));
				_quickFindF.union(translate(i, j), translate(i, j - 1));
			}
			if (j + 1 <= size && isOpen(i, j + 1)) {
				_quickFind.union(translate(i, j), translate(i, j + 1));
				_quickFindF.union(translate(i, j), translate(i, j + 1));
			}
			if (i == size) {
				_quickFind.union(translate(i, j), size * size + 1);
			}
		}
	}

	/**
	 * @return whether the grid percolates
	 */
	// does the system percolate?
	public boolean percolates() {
		return _quickFind.connected(0, size * size + 1);
	}

	private int translate(final int i, final int j) {
		return (i - 1) * size + j;
	}

	/**
	 * @param i
	 * @param j
	 */
	private void validateInput(int i, int j) {
		if (i < 1 || i > size || j < 1 || j > size) {
			throw new IndexOutOfBoundsException();
		}
	}
}
