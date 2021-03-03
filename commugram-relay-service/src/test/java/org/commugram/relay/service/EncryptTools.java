package org.commugram.relay.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptTools {

    public static int getInt(byte b) {
        int num = (int) b;
        if (num < 0) {
            num = num + 256;
        }
        return num;
    }
    public static String getHexString(byte b) {
        int tmpnum = getInt(b);
        String sTemp = Integer.toHexString(tmpnum).toUpperCase();
        if (sTemp.length() == 1) {
            sTemp = "0" + sTemp;
        }
        return sTemp;
    }
    public static String getHexString(byte[] b) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            buf.append(getHexString(b[i]));
        }
        return buf.toString();
    }

    public static byte[] hex2Bytes(String s) throws IOException {
        int i = s.length() / 2;
        byte abyte0[] = new byte[i];
        int j = 0;
        if (s.length() % 2 != 0)
            throw new IOException("hexadecimal string with odd number of characters");
        for (int k = 0; k < i; k++) {
            char c = s.charAt(j++);
            int l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
            if (l == -1)
                throw new IOException("hexadecimal string contains non hex character");
            int i1 = (l & 0xf) << 4;
            c = s.charAt(j++);
            l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
            i1 += l & 0xf;
            abyte0[k] = (byte) i1;
        }
        return abyte0;
    }

	public static final byte[] doAESDecrypt(int length, byte[] key, byte[] initialVector, byte[] text) throws Exception {
		return doAESEncrypt(false,length,key,initialVector,text);
	}
	public static final byte[] doAESEncrypt(int length, byte[] key, byte[] initialVector, byte[] text) throws Exception {
		return doAESEncrypt(true,length,key,initialVector,text);
	}
	private static final byte[] doAESEncrypt(boolean encrypt, int length, byte[] key, byte[] initialVector, byte[] text) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(Arrays.copyOf(key, key.length));
        kgen.init(length, secureRandom);
        
        SecretKey secretKey = kgen.generateKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        if (initialVector==null||initialVector.length==0){
        	initialVector = new byte[16];
        }else {
        	initialVector = Arrays.copyOf(initialVector, 16);
        }
        IvParameterSpec iv = new IvParameterSpec(initialVector);
        cipher.init(encrypt?Cipher.ENCRYPT_MODE:Cipher.DECRYPT_MODE, secretKeySpec, iv);
        return cipher.doFinal(text);
	}

	public static final byte[] doDESDecrypt(int length, byte[] key, byte[] initialVector, byte[] text) throws Exception {
		return doDESEncrypt(false,length,key,initialVector,text);
	}
	public static final byte[] doDESEncrypt(int length, byte[] key, byte[] initialVector, byte[] text) throws Exception {
		return doDESEncrypt(true,length,key,initialVector,text);
	}
	private static final byte[] doDESEncrypt(boolean encrypt, int length, byte[] key, byte[] initialVector, byte[] encryptText) throws Exception {
        int len = encryptText.length;
        if (encryptText.length % 8 != 0) {
            len = encryptText.length - encryptText.length % 8 + 8;
        }
        byte[] needData = null;
        if (len != 0)
            needData = new byte[len];

        for (int i = 0; i < len; i++) {
            needData[i] = 0x00;
        }

        System.arraycopy(encryptText, 0, needData, 0, encryptText.length);

        byte[] k = Arrays.copyOf(key, 24);
        if (key.length<k.length) {
            System.arraycopy(key, 0, k, key.length, k.length-key.length);
        }
        KeySpec ks = new DESedeKeySpec(k);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
        SecretKey ky = kf.generateSecret(ks);

        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");

        if (initialVector==null||initialVector.length==0){
        	initialVector = new byte[8];
        }else {
        	initialVector = Arrays.copyOf(initialVector, 8);
        }
        IvParameterSpec iv = new IvParameterSpec(initialVector);
        cipher.init(encrypt?Cipher.ENCRYPT_MODE:Cipher.DECRYPT_MODE, ky, iv);
        
        byte[] returned = cipher.doFinal(needData);
        int last0 = returned.length;
        for(;last0>-1&&returned[last0-1]==0;last0-=1) {}
        if (last0!=returned.length) {returned = Arrays.copyOf(returned, last0);}
        return returned;
    }
    

	public static byte[] compress(byte[] data) {
		return compress(data, 0, data.length);
	}
	
	public static byte[] decompress(byte[] data) throws DataFormatException {
		return decompress(data, 0, data.length);
	}
	
	public static byte[] compress(byte[] data, int offset, int len) {
		byte[] output = new byte[0];

		Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);
		compresser.setInput(data, offset, len);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(len);
        byte[] buf = new byte[1024];
        while (!compresser.finished()) {
            int count = compresser.deflate(buf);
            if (count<1) {break;}
            bos.write(buf, 0, count);
        }
		output = bos.toByteArray();
		try {
			bos.close();
		} catch (IOException e) {
//				e.printStackTrace();
		}
		compresser.end();
		return output;
	}

	public static byte[] decompress(byte[] data, int offset, int len) throws DataFormatException {
		byte[] output = new byte[0];

		Inflater decompresser = new Inflater();
		decompresser.setInput(data, offset, len);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(len);
        byte[] buff = new byte[1024];
        while (!decompresser.finished()) {
            int count = decompresser.inflate(buff);
            if (count<1) {break;}
            baos.write(buff, 0, count);
        }
		output = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
//				e.printStackTrace();
		}

		decompresser.end();
		return output;
	}

}
