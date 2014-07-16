import java.awt.event.*;
import java.awt.*;

class AWTDevice extends DisplayDevice {
    AWTDevice(int width, int height) {
	super(0, height, width, 0);
	frame = new DeviceFrame(width, height, this);
    }

    public void display() {frame.repaint();}

    DeviceFrame frame;
}

class DeviceFrame extends Frame {
    DeviceFrame(int width, int height, AWTDevice device) {
        addWindowListener(
	    new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
		    System.exit(0);
		}
	    });
	dc = new DeviceCanvas(width, height, device);
        add(dc);
        pack();
        setVisible(true);
    }

    public void repaint() {
	dc.paint(dc.getGraphics());
    }
    
    DeviceCanvas dc;
}

class DeviceCanvas extends Canvas {
    DeviceCanvas(int width, int height, AWTDevice device) {
        setSize(width, height);
        this.device = device;
    }

    public void paint(Graphics g) {
        for (int x = 0; x < device.getWidth(); x ++) {
	    for (int y = 0; y < device.getHeight(); y ++) {
		if (device.getPixel(x, y)) {
		    g.setColor(Color.BLACK);
		    g.drawLine(x,y,x,y);
		} else {
		    g.setColor(Color.WHITE);
		    g.drawLine(x,y,x,y);
		}
	    }
	}
    }

    AWTDevice device;
}