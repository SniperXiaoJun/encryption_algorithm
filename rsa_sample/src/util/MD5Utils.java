package util;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class MD5Utils {
	//��MD5����ļ�����ϢժҪ
	public static String md5(String file) {
		try {
			//����MessageDigest���󣬵õ�md5����ϢժҪ
			MessageDigest md = MessageDigest.getInstance("MD5");
			//�õ���Ҫ�����������
			FileInputStream fis = new FileInputStream(file);
			//����DigestInputStream����
			DigestInputStream dis = new DigestInputStream(fis,md);
			//��DigestInputStream���ж�ȡ���ݣ�����Ҫѭ����
			while(dis.read()!=-1);	
			//�õ�ժҪ
			byte[] bytes= md.digest(); 
			//��ժҪת�����ַ�������
			String result = "";
			for (int i=0; i<bytes.length; i++){
	            result+=Integer.toHexString((0x000000ff & bytes[i]) | 
					0xffffff00).substring(6);
	        }
			return result;
		
		} catch (Exception e) {
			return null;
		}	
	}
}
