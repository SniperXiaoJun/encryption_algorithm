package util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;


public class SignUtils {
	/**
	 * ���ļ��л�ù�Կ��˽Կ��������ϢժҪ����ǩ�������浽�ļ���
	 * @param file
	 * 				���˽Կ���ļ�
	 * @param info
	 * 				��ϢժҪ
	 * @param signFile
	 * 				�����ļ�
	 */
    public static void signInfo(String file,String info, String signFile) {  
    	// ���ļ��ж�ȡ����  
        PublicKey myPubKey = (PublicKey) FileUtils.getObjFromFile(file, 1);  
        // ���ļ��ж�ȡ˽��  
    	 PrivateKey myPriKey = (PrivateKey) FileUtils.getObjFromFile(file, 2);  
        try {  
            // ��ȡSignature ,Signature������������ɺ���֤����ǩ��  
            Signature signet = Signature.getInstance("MD5WithRSA");  
            // ��˽Կ��ʼ��Signature
            signet.initSign(myPriKey);  
            // ����ǩ������ϢժҪ 
            signet.update(info.getBytes());  
            // ִ��ǩ��
            byte[] signed = signet.sign();  
            // ������ǩ��,����,��ϢժҪ�����ļ���  
            FileUtils.doObjToFile(signFile, new Object[] { signed, myPubKey, info });  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * ��ȡ����ǩ���ļ� ���ݹ��ף�ǩ������Ϣ��֤��Ϣ�ĺϷ��� 
     *  
     * @return true ��֤�ɹ� false ��֤ʧ�� 
     */  
    public static boolean validateSign(String signFile) {  
        // ��ȡǩ��  
        byte[] signed = (byte[]) FileUtils.getObjFromFile(signFile, 1);  
        // ��ȡ����  
        PublicKey myPubKey = (PublicKey) FileUtils.getObjFromFile(signFile, 2);  
        // ��ȡ��Ϣ  
        String info = (String) FileUtils.getObjFromFile(signFile, 3);  
        try {  
            // ��ʼһ��Signature����,���ù�Կ��ǩ��������֤  
            Signature signet = Signature.getInstance("MD5WithRSA");  
            // ��ʼ����֤ǩ���Ĺ�Կ  
            signet.initVerify(myPubKey);  
            // ������֤����ϢժҪ 
            signet.update(info.getBytes());  
           // System.out.println(info);  
            // ��֤�����ǩ��  
            return signet.verify(signed);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
}
