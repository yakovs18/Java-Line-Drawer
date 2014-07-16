class Matrix {
    Matrix(int rows, int cols) {arr = new double[rows][cols];}
    Matrix(double [][] inits) {
	this(inits.length, inits[0].length);
	for (int row = 0; row < this.arr.length; row ++)
	    for (int col = 0; col < this.arr[0].length; col ++)
		this.arr[row][col] = inits[row][col];
    } 

    int getNumRows() {return arr.length;}
    int getNumCols() {return arr[0].length;}
    
    double get(int row, int col) {return arr[row][col];}
    void set(int row, int col, double val) {arr[row][col] = val;}

    Matrix multiply(Matrix m) {
	Matrix result = new Matrix(this.arr.length, m.arr[0].length);
	for (int row = 0; row < this.arr.length; row ++)
            for(int col = 0; col < m.arr[0].length; col ++) {
		result.arr[row][col] = 0;
		for (int line = 0; line < this.arr[0].length; line ++)
		    result.arr[row][col] += this.arr[row][line] * m.arr[line][col];
	    }
	return result;	
    }

    public String toString() {
	String result = "{";
	for (int row = 0; row < arr.length; row ++) {
            for(int col = 0; col < arr[0].length; col ++)
		result = result + this.arr[row][col] + (col == arr[0].length - 1 ? "" : ", ");
	    result = result + "}" + (row == arr.length - 1 ? "" : ", {");
	}
	return result;
    }
    
    public static void main(String [] args) {
        Matrix 
	    m1 = new Matrix(new double [][] {{1, 0}, {0, 1}}),
	    m2 = new Matrix(new double [][] {{1, 2}, {3, 4}});
	System.err.println(m1);
	System.err.println(m2);
	System.err.println(m2.multiply(m1));

	m1 = new Matrix(new double [][] {{1, 2}, {3, 4}, {5, 6}});
	m2 = new Matrix(new double [][] {{1, 2, 3, 4}, {5, 6, 7, 8}});
	System.err.println(m1);
	System.err.println(m2);
	System.err.println(m1.multiply(m2));
    }

    double arr[][];
}