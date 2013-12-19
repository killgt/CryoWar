package es.killgt.engine;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Esta clase esta basada en el libro Developming Games in Java
 * 
 * @author Kill
 * 
 */
public class Sonido extends Thread {

	private boolean ejecutandose = true;
	private boolean loopeable;
	private boolean reproducirse = false;
	
	private byte[] samples;
	private AudioInputStream stream;
	private AudioFormat format;
	private InputStream sonido;
	private byte[] buffer; 
	private SourceDataLine line;
	private float volumen;
	
	public Sonido(ResourceManager r, String ruta, boolean loopeable){
		this(r,ruta,loopeable,1);
	}
	public Sonido(ResourceManager r, String ruta, boolean loopeable, float volumen) {
		this.loopeable = loopeable;
		try {
			stream = r.getSonido(ruta);
		} catch (UnsupportedAudioFileException e) {

		} catch (IOException e) {
		}
		
		this.volumen = volumen;
		
		if (ejecutandose)
			this.start();
	}

	public void run() {
		inicializar();
		while (ejecutandose) {

			if (!loopeable)
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {}					
				}
			if (reproducirse)				
				play(sonido);
		}
        line.close();
 
	}

	private void inicializar() {
		format = stream.getFormat();
		samples = generateSamples();
		sonido = new ByteArrayInputStream(samples);
		
        // use a short, 100ms (1/10th sec) buffer for real-time
        // change to the sound stream
        int bufferSize = format.getFrameSize() *
            Math.round(format.getSampleRate() / 10);
        buffer = new byte[bufferSize];

        // create a line to play to
        try {
            DataLine.Info info =
                new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine)AudioSystem.getLine(info);
            line.open(format, bufferSize);
        }
        catch (LineUnavailableException ex) {
            ex.printStackTrace();
            return;
        }
        if (line != null){
        	FloatControl volctrl=(FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN); 
        	volctrl.setValue(volumen);
        }
	}

	private byte[] generateSamples() {
		int length = (int)(stream.getFrameLength() *
	            format.getFrameSize());
		
		byte[] samples = new byte[length];
		
		DataInputStream is = new DataInputStream(stream);
		
        try {
            is.readFully(samples);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return samples;

	}

	public void reproducir() {
		synchronized (this) {
			this.notify();		
			reproducirse = true;
		}
		
	}
	public void parar(){
		reproducirse = false;
		if (line != null)
			line.stop();
	}
	public void terminar() {
		ejecutandose = false;

	}

	public void finalize() {
		terminar();
	}
	
	public void play(InputStream source) {

		line.start();
		line.flush();
        // copy data to the line
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {
                numBytesRead =
                    source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1) {
                   line.write(buffer, 0, numBytesRead);
                   if (!ejecutandose)
                	   break;
                }
            }
           line.drain();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        line.drain();
        try {
			source.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
