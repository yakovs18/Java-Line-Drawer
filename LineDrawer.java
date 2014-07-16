class LineDrawer {
    LineDrawer(DisplayDevice device) {
	this.device = device;
	new LineDrawerThread(this).start();
    }

    void paint(DisplayDevice device) {
	device.clearTransformations();
	device.rotate(theta, .5, .5);
	device.drawLine(.5, .5, .5, .75);
	theta += 2 * Math.PI / 60;
    }

    void repaint() {
	device.clear();
	paint(device);
	device.display();
    }

    public static void main(String [] args) {
	//      	LineDrawer lineDrawer = new LineDrawer(new ConsoleDevice(80, 25));
	LineDrawer lineDrawer2 = new LineDrawer(new AWTDevice(400, 300));
    }

    DisplayDevice device;
    double theta;
}

class LineDrawerThread extends Thread {
    LineDrawerThread(LineDrawer ld) {this.ld = ld;}

    public void run() {
	while (true) {
	    try {
		ld.repaint();
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
	    }
	}
    }

    LineDrawer ld;
}