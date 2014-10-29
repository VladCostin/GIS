package GIS;
import java.io.OutputStream;

import java.awt.Image;

import java.awt.image.BufferedImage;

import java.util.Iterator;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.ImageWriteParam;
import javax.imageio.IIOImage;

import javax.imageio.stream.ImageOutputStream;

/**
 * Klasse die einige grundlegende Funktionen bereitstellt, um
 * Bitmaps zu erzeugen
 */
public class ImageToBitmap {
	
	public static final int JPEG = 0;
	public static final int PNG  = 1;
	public static final int BMP  = 2;
	
	/**
	 * Liefert eine Image-Vorlage in einer definierten Groesse
	 * @param _width Die Breite des Bildes
	 * @param _height Die Hoehe des Bildes
	 * @return Das Bild in Form eines BufferedImages
	 * @see java.awt.image.BufferedImage
	 */
	public static BufferedImage getImage(int _width, int _height) {
		return new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);
	} // getImage
	
	/**
	 * Liefert die durch das System unterstuetzten Bitmap-Konverter
	 * @return String-Array mit allen zur Verfuegung stehenden Bitmap-Konvertern
	 * @see java.lang.String
	 */
	public static String[] getImageTypes() {
		return ImageIO.getWriterMIMETypes();
	}
	
	/**
	 * Liefert, ausgehend von einer textuellen Angabe, den Bitmaptyp
	 * JPG|jpg|Jpg    --> JPEG
	 * BMP|bmp|Bmp    --> BMP
	 * JPEG|jpeg|Jpeg --> JPEG
	 * @param _type Der Bitmap-Typ als String kodiert
	 * @return Der Bitmap-Typ als ImageToBitmap-Konstante(JPEG|BMP|PNG)
	 * @see java.lang.String
	 */
	public static int getImageType(String _type) {
		if (_type.equalsIgnoreCase("JPG")) {
			return JPEG;
		} if (_type.equalsIgnoreCase("BMP")) {
			return BMP;
		} if (_type.equalsIgnoreCase("PNG")) {
			return PNG;
		} if (_type.equalsIgnoreCase("JPEG")) {
			return JPEG;
		} else {
			return JPEG;
		}
	}
	
	/**
	 * Speichert das uebergebene Bild in Form des angegebenen Bitmap-Formats
	 * in einen Ausgabestrom
	 * @param _image Das zu speichernde Bild
	 * @param _out   Der Ausgabestrom, in den das Bild im entsprechenden Format
	 *               abzuspeichern ist
	 * @param _type  Der Bitmap-Typ [JPEG|BMP|PNG]
	 * @see java.awt.image.BufferedImage
	 * @see java.io.OutputStream
	 */
	public static void storeImage(BufferedImage _image, OutputStream _out, int _type) {
	  switch(_type) {
			case JPEG : {
				System.out.println("JPEG");
	      /*
	      JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(_out);
	      JPEGEncodeParam  prm = enc.getDefaultJPEGEncodeParam(_image);
	      prm.setQuality(1.0f,false );
	      enc.setJPEGEncodeParam(prm);
	      enc.encode(_imgage);			
	      */
	      try {
	        Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
					if(iter.hasNext()) {
					  ImageWriter     imgWriter   = (ImageWriter)iter.next();
					  ImageWriteParam imgWrtParam = imgWriter.getDefaultWriteParam();
				  	if (imgWrtParam.canWriteCompressed()) {					  
						  ImageOutputStream imgIOS    = ImageIO.createImageOutputStream(_out);
						  imgWriter.setOutput(imgIOS);
						  
						  // String[] temp = imgWrtParam.getCompressionTypes();
						  // for (int i = 0 ; i < temp.length ; i++) {
						  // 	System.out.println(i + ". --> " + temp[i]);
						  // } // for i
							  
						  imgWrtParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
						  imgWrtParam.setCompressionQuality(1.0f);
						  imgWriter.write(null, new IIOImage(_image, null, null), imgWrtParam );
						  
						  imgIOS.flush();
						  imgWriter.dispose();
						  imgIOS.close();
						} // if iter
					} else {
						System.out.println("no compression allowed");
        		ImageIO.write(_image, "jpg", _out);
					} // if else canWriteCompressed
	      } catch (Exception _e) { _e.printStackTrace(); }
			} break;
			case PNG : {
				System.out.println("PNG");
	      try {
	        Iterator iter = ImageIO.getImageWritersByFormatName("png");
					if(iter.hasNext()) {
					  ImageWriter     imgWriter   = (ImageWriter)iter.next();
					  ImageWriteParam imgWrtParam = imgWriter.getDefaultWriteParam();
						ImageOutputStream imgIOS    = ImageIO.createImageOutputStream(_out);
						imgWriter.setOutput(imgIOS);

				  	if (imgWrtParam.canWriteCompressed()) {		
				  		imgWrtParam.setCompressionType("BI_RLE8");
						}
						imgWriter.write(null, new IIOImage(_image, null, null), imgWrtParam );
						  
						imgIOS.flush();
						imgWriter.dispose();
						imgIOS.close();
					} else {
						System.out.println("no compression allowed");
        		ImageIO.write(_image, "png", _out);
					} // if else canWriteCompressed
	      } catch (Exception _e) { _e.printStackTrace(); }
			} break;
			case BMP : {
				System.out.println("BMP");
	      try {
	        Iterator iter = ImageIO.getImageWritersByFormatName("bmp");
					if(iter.hasNext()) {
					  ImageWriter     imgWriter   = (ImageWriter)iter.next();
					  ImageWriteParam imgWrtParam = imgWriter.getDefaultWriteParam();
					  ImageOutputStream imgIOS    = ImageIO.createImageOutputStream(_out);
					  imgWriter.setOutput(imgIOS);

				  	if (imgWrtParam.canWriteCompressed()) {					  
						  // Hier Komprimierungsparameter setzen ... 
						} // if compressed
					  imgWriter.write(null, new IIOImage(_image, null, null), imgWrtParam );
					  
					  imgIOS.flush();
					  imgWriter.dispose();
					  imgIOS.close();
					} else {
						System.out.println("no compression allowed");
        		ImageIO.write(_image, "bmp", _out);
					} // if else canWriteCompressed
	      } catch (Exception _e) { _e.printStackTrace(); }
			} break;
	  } // switch
  }// storeImage
} // class

