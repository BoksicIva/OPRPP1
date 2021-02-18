package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * Class used for crypto methods over files
 * @author Iva
 *
 */
public class Crypto {
	
	public static void main(String[] args) {
		if(args.length != 2 && args.length !=3)
				throw new IllegalArgumentException("Illegal number of arguments -> (2 or 3)");
		
		switch(args[0]) {
		case "checksha":    checksha(args[1]);
							break;
		case "encrypt":     crypt(args[1],args[2],true);
							break;	
		case "decrypt":     crypt(args[1],args[2],false);
							break;
		default:			throw new IllegalArgumentException("Operation not supported");
		}
	}
	
	/**
	 * Method checks sha for given file
	 * @param IFileName name of file that need to checked
	 */
	private static void checksha(String IFileName) {
		Scanner sc = new Scanner(System.in);
		String row = null;
		System.out.print("Please provide expected sha-256 digest for "+IFileName+":\n> ");
		if( sc.hasNextLine() ) {
			row = sc.nextLine().trim();
		}
		byte[] expectedDigest=Util.hextobyte(row);
		byte[] givenDigest;
		try {
			InputStream inputStream = Files.newInputStream(Paths.get(IFileName));
			MessageDigest messageDigest =  MessageDigest.getInstance("SHA-256");
			byte[] buff = new byte[4096];
			while(true) {
				int r = inputStream.read(buff);
				if(r<1) break;
				// obradi samo buff[0] do buff[r-1]
				messageDigest.update(buff, 0, r);
			}
			givenDigest=messageDigest.digest();
			
			if(Util.bytetohex(expectedDigest).equals(Util.bytetohex(givenDigest)))
				System.out.println("Digesting completed. Digest of "+IFileName +" matches expected digest");
			else
				System.out.println("Digesting completed. Digest of "+IFileName  +" does not match the expected digest. Digest was: "+ Util.bytetohex(givenDigest));
		}catch(IOException | NoSuchAlgorithmException ex){
			ex.printStackTrace();
		}
		
		sc.close();
	}
	
	
	
	/**
	 * Method encrypts of decrypts given file depending on value of bool encrypt
	 * @param IFileName name of input file
	 * @param OFileName name of output file
	 * @param encrypt bool value --> true method must encrypt, false decrypt
	 */
	private static void crypt(String IFileName,String OFileName,boolean encrypt)  {
	String keyText = null;// = … what user provided for password … 
	String ivText = null;// = … what user provided for initialization vector … 
	InputStream inputStream;
	OutputStream outputStream;
	Scanner sc = new Scanner(System.in);
	
	System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):ˇ\n> ");
	if( sc.hasNextLine() ) {
		keyText = sc.nextLine().trim();
	}
	
	System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n< ");
	if( sc.hasNextLine() ) {
		ivText = sc.nextLine().trim();
	}
	
	
	try{
		inputStream=Files.newInputStream(Paths.get(IFileName));
		outputStream = Files.newOutputStream(Paths.get(OFileName), StandardOpenOption.CREATE_NEW);
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText)); 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		
		byte[] buff = new byte[4096];
		while(true) {
			int r = inputStream.read(buff);
			if(r<1) break;
			// obradi samo buff[0] do buff[r-1]
			outputStream.write(cipher.update(buff, 0, r));
		}
		outputStream.write(cipher.doFinal());
		
		if(encrypt)
			System.out.println("Encryption");
		else
			System.out.println("Decryption");
		System.out.print(" completed. Generated file " + OFileName + " based on file " + IFileName+".");
	}catch(IOException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
		ex.printStackTrace();
	}
	
	sc.close();
	
	}

}
