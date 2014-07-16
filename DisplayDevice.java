import static java.lang.Math.*;

abstract class DisplayDevice {
    DisplayDevice(int lowerX, int lowerY, int upperX, int upperY) {
	this.lowerX = lowerX;
	this.lowerY = lowerY;
	this.upperX = upperX;
	this.upperY = upperY;
	this.signedWidth = upperX - lowerX;
	this.signedHeight = upperY - lowerY;
	width = abs(signedWidth);
	height = abs(signedHeight);
	pixels = new boolean[width][height];
	transMatrix = new Matrix(new double [][] {{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}});
    }
    DisplayDevice() {
	this(0, 10, 50, 0);
    } 

    void setPixel(int x, int y, boolean b) {pixels[x][y] = b;}

    void setPixel(int x, int y) {
	if (x < width && x >= 0 && y < height && y >= 0)
	    pixels[x][y] = true;
    }
    
    boolean getPixel(int x, int y) {return pixels[x][y];}

    int convertX(double normalizedX) {return (int)(lowerX + normalizedX * signedWidth);}
    int convertY(double normalizedY) {return (int)(lowerY + normalizedY * signedHeight);}
    
    void drawLine(double x1, double y1, double x2, double y2) {
	Matrix m1 = doTransformation(x1, y1);
	Matrix m2 = doTransformation(x2, y2);
	drawLine(convertX(m1.get(0, 0)), convertY(m1.get(0, 1)), convertX(m2.get(0, 0)), convertY(m2.get(0, 1)));
    }
 
    private void drawLine(int x1, int y1, int x2, int y2) {
	int deltaX = abs(x1 - x2); 
	int deltaY = abs(y1 - y2);
	int incrementX = x1 < x2 ? 1 : -1;
	int incrementY = y1 < y2 ? 1 : -1;
	int error = 2 * (deltaX - deltaY);
	boolean xDone = false, yDone = false;

	while (!xDone || !yDone) {
	    if(x1 == x2) xDone = true;
	    if(y1 == y2) yDone = true;

	    setPixel(x1, y1);

	    if (error > -deltaY) {
		error -= 2 * deltaY;
		x1 += incrementX;
	    }
	        
	    if (error < deltaX) {
		error += 2 * deltaX;
		y1 += incrementY;
	    }
	}
	
    }

    int getWidth() {return width;}
    int getHeight() {return height;}

    public void clear() {
	for (int row = 0; row < height; row ++) 
	    for (int col = 0; col < width; col ++) 
		if (pixels[col][row]) 
		    pixels[col][row] = false;
    } 

    abstract void display();
    
    public void clearTransformations() {transMatrix = new Matrix(new double [][] {{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}});}
    
    void scaleX(double scaleXFactor) {
	Matrix m = new Matrix(new double [][] {{scaleXFactor, 0, 0}, {0, 1, 0}, {0, 0, 1}});
	transMatrix = transMatrix.multiply(m);
    }

    void scaleY(double scaleYFactor) {
	Matrix m = new Matrix(new double [][] {{1, 0, 0}, {0, scaleYFactor, 0}, {0, 0, 1}});
	transMatrix = transMatrix.multiply(m);
    }

    void scale(double scaleXFactor, double scaleYFactor) {
	this.scaleX(scaleXFactor);
	this.scaleY(scaleYFactor);
    }
    
    void rotate(double angle) {
	rotate(angle, 0, 0);
    }
    void rotate(double angle, double x, double y) {
	Matrix m = new Matrix(new double [][] {{cos(angle), sin(angle), 0}, {-sin(angle), cos(angle), 0}, {0, 0, 1}});
	xlateX(-x);
	xlateY(-y);
        transMatrix = transMatrix.multiply(m);
	xlateX(x);
	xlateY(y);
    }
    
    void xlateX(double xlateXFactor) {
	Matrix m = new Matrix(new double [][] {{1, 0, 0}, {0, 1, 0}, {xlateXFactor, 0, 1}});
        transMatrix = transMatrix.multiply(m);
    }
    void xlateY(double xlateYFactor) {
	Matrix m = new Matrix(new double [][] {{1, 0, 0}, {0, 1, 0}, {0, xlateYFactor, 1}});
        transMatrix = transMatrix.multiply(m);
    }
    
    Matrix doTransformation(double x, double y) {
	Matrix m = new Matrix(new double [][] {{x, y, 1}});
	return m.multiply(transMatrix);
    }
    Matrix getTransMatrix() {return transMatrix;}
    
    private Matrix transMatrix;
    private boolean pixels[][];
    private int width, height, signedWidth, signedHeight, lowerX, lowerY, upperX, upperY;

}