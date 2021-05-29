package com.cowin.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;

public class Utilities {

	public static void createCaptchaImage(String captcha) {

		try {
			// Create a JPEG transcoder
			JPEGTranscoder t = new JPEGTranscoder();

			// Set the transcoding hints.
			t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));

			InputStream stream = new ByteArrayInputStream(captcha.getBytes(StandardCharsets.UTF_8));

			// Create the transcoder input.
			// String svgURI = new File(inputFilePath).toURL().toString();
			TranscoderInput input = new TranscoderInput(stream);

			// Create the transcoder output.
			OutputStream ostream = new FileOutputStream("F:\\Abhishek\\Project\\Cowin_Book\\captach.jpeg");
			TranscoderOutput output = new TranscoderOutput(ostream);

			// Save the image.
			t.transcode(input, output);

			// Flush and close the stream.
			ostream.flush();
			ostream.close();
			openImage();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void openImage() throws Exception
	{
		String fileName = "F:\\Abhishek\\Project\\Cowin_Book\\captach.jpeg";
	    String [] commands = {
	        "cmd.exe" , "/c", "start" , "\"DummyTitle\"", "\"" + fileName + "\""
	    };
	    Process p = Runtime.getRuntime().exec(commands);
	    p.waitFor();
	    System.out.println("Done.");
	}
}
