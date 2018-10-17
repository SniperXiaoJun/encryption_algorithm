package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtils {
	 /** 
     * ��ָ���Ķ���д��ָ�����ļ��� 
     *  
     * @param file 
     *            ָ��д����ļ� 
     * @param objs 
     *            Ҫд��Ķ��� 
     */  
    public static void doObjToFile(String file, Object[] objs) {  
        ObjectOutputStream oos = null;  
        try {  
            FileOutputStream fos = new FileOutputStream(file);  
            oos = new ObjectOutputStream(fos);  
            for (int i = 0; i < objs.length; i++) {  
                oos.writeObject(objs[i]);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                oos.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    } 
    
    /** 
     * �������ļ���ָ��λ�õĶ��� 
     *  
     * @param file 
     *            ָ�����ļ� 
     * @param i 
     *            ��ȡλ��
     * @return 
     */  
    public static Object getObjFromFile(String file, int i) {  
        ObjectInputStream ois = null;  
        Object obj = null;  
        try {  
            FileInputStream fis = new FileInputStream(file);  
            ois = new ObjectInputStream(fis);  
            for (int j = 0; j < i; j++) {  
                obj = ois.readObject();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                ois.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return obj;  
    }  
}
