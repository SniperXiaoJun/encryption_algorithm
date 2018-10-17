package util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

public class RSAUtils {
	/**
	 * ��RSA�㷨���ֽ�������ܷ����ֽ�����
	 * @param pk
	 * 			������Ҫ�Ĺ�Կ
	 * @param data
	 * 			��Ҫ���ܵ��ֽ�����
	 * @return
	 * 			���ؼ��ܺ���ֽ�����
	 * @throws Exception
	 */
	private static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {

		Cipher cipher = Cipher.getInstance("RSA",
				new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		int blockSize = cipher.getBlockSize();// ��ü��ܿ��С
		int outputSize = cipher.getOutputSize(data.length);// ��ü��ܿ���ܺ���С
		int leavedSize = data.length % blockSize;//ȡĤ
		int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
				: data.length / blockSize;
		byte[] raw = new byte[outputSize * blocksSize];
		int i = 0;
		while (data.length - i * blockSize > 0) {
			if (data.length - i * blockSize > blockSize)
				cipher.doFinal(data, i * blockSize, blockSize, raw, i
						* outputSize);
			else
				cipher.doFinal(data, i * blockSize,
						data.length - i * blockSize, raw, i * outputSize);
			i++;
		}
		return raw;

	}

	/**
	 * 
	 * @param pk
	 * 			���ܹ�Կ
	 * @param file
	 * 			�����ļ�
	 * @param destFile
	 * 			Ŀ���ļ�
	 * @throws Exception
	 */
	public static void encryptToFile(RSAPublicKey pk, String file,
			String destFile) throws Exception {
		//�������ļ���ȡ��ŵ��ڴ���
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = new FileInputStream(file);
		byte[] b = new byte[1024];
		int r;
		while ((r = is.read(b)) > 0) {
			baos.write(b, 0, r);
		}
		byte[] data = baos.toByteArray();
		byte[] bytes = encrypt(pk, data);
		OutputStream os = new FileOutputStream(destFile);
		os.write(bytes);
		baos.close();
		os.close();
	}

	/**
	 * 
	 * @param pk
	 * 			����˽Կ
	 * @param data
	 * 			�����ֽ�����
	 * @return
	 * 			�����ֽ�����
	 * @throws Exception
	 */
	private static byte[] decrypt(RSAPrivateKey pk, byte[] data)
			throws Exception {

		Cipher cipher = Cipher.getInstance("RSA",
				new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, pk);
		int blockSize = cipher.getBlockSize();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
		int j = 0;

		while (data.length - j * blockSize > 0) {
			bout.write(cipher.doFinal(data, j * blockSize, blockSize));
			j++;
		}
		return bout.toByteArray();

	}
/**
 * 
 * @param pk
 * 			����˽Կ
 * @param file
 * 			�����ļ�
 * @param destFile
 * 			Ŀ���ļ�
 * @throws Exception
 */
	public static void decryptToFile(RSAPrivateKey pk, String file,
			String destFile) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = new FileInputStream(file);
		byte[] b = new byte[1024];
		int r;
		while ((r = is.read(b)) > 0) {
			baos.write(b, 0, r);
		}
		byte[] data = baos.toByteArray();
		byte[] bytes = decrypt(pk, data);
		OutputStream os = new FileOutputStream(destFile);
		os.write(bytes);
		baos.close();
		os.close();
	}

}
