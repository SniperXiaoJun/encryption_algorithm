package util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class KeyUtils {
	//���ܿ��С
	 public static final int KEY_SIZE = 1024;
	/**
	 * ������Կ��,���洢���ļ���
	 */
	public static void createPairKey(String file) {	
		try {
			//����RSA��Կ������,BouncyCastleProvider������RSA�㷨�ṩ��
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA",new org.bouncycastle.jce.provider.BouncyCastleProvider());
			//ʹ�ø����������Ϻ����Դ��ʼ����Կ����������
			keyGen.initialize(KEY_SIZE, new SecureRandom());
			//ͨ��KeyPairGenerator������Կ��
			KeyPair keyPair = keyGen.generateKeyPair();  
			// �õ�����  
	        PublicKey pubKey = (PublicKey) keyPair.getPublic();  
	        // �õ�˽��  
	        PrivateKey priKey = (PrivateKey) keyPair.getPrivate();  
	        // ������˽��д�뵽�ļ�����  
            FileUtils.doObjToFile(file, new Object[] { pubKey, priKey });  
		} catch (Exception e) {
		}  
	}
	
 
}
