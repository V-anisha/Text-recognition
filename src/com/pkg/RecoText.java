package com.pkg;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.ByteArrayInputStream;
import java.io.File;


public class RecoText {
	
	Tesseract ts;
	public RecoText() {
		ts = new Tesseract();
		ts.setDatapath("");
		ts.setLanguage("eng");
		try {
			String text = ts.doOCR(getImage("images/img1.png"));
			System.out.println(text);
		} catch (TesseractException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private BufferedImage getImage(String imgPath) throws IOException{
		
		//checking if the file exists
		File file = new File(imgPath);
	    if (!file.exists()) {
	        System.err.println("File not found: " + imgPath);
	        return null;
	    }
	    
		//read image
		Mat mat= Imgcodecs.imread(imgPath);
		
		//change img to gray scale
		Mat gray = new Mat();
		Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY);
		
		//resize image
		Mat resized = new Mat();
		Size size = new Size(mat.width() * 1.9f,mat.height() * 1.9f);
		Imgproc.resize(gray, resized, size);
		
		//convert to buffered image
		MatOfByte mof= new MatOfByte();
		byte imageByte[];
		Imgcodecs.imencode(".png", resized, mof);
		imageByte=mof.toArray();
		
		return ImageIO.read(new ByteArrayInputStream(imageByte));
	}


	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		System.out.print("loaded");
		new RecoText();
	}

}