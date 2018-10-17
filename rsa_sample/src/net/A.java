package net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.interfaces.RSAPublicKey;

import util.KeyUtils;
import util.MD5Utils;
import util.RSAUtils;
import util.SignUtils;

public class A {

	private String info;
	private String FILE = "d:/test/test.txt";
	private String KEY_FILE = "d:/test/A/AKey.dat";
	private String SIGN_FILE = "d:/test/A/sign.dat";
	private String RSA_SIGN_FILE = "d:/test/A/rsaSign.dat";
	private String RSA_FILE = "d:/test/A/rsaTest.dat";
	private RSAPublicKey pubKey;

	public RSAPublicKey getPubKey() {
		return pubKey;
	}

	public void setPubKey(RSAPublicKey pubKey) {
		this.pubKey = pubKey;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void init() {
		// ����Ŀ¼
		File f1 = new File("d:/test/A/");
		f1.mkdirs();
		info = MD5Utils.md5(FILE);
		KeyUtils.createPairKey(KEY_FILE);
		SignUtils.signInfo(KEY_FILE, info, SIGN_FILE);
	}

	public void send() throws Exception {
		RSAUtils.encryptToFile(pubKey, SIGN_FILE, RSA_SIGN_FILE);
		RSAUtils.encryptToFile(pubKey, FILE, RSA_FILE);
	}

	public void start() throws Exception {

		ServerSocket ss = new ServerSocket(520);

		Socket socket = ss.accept();

		InputStream is = socket.getInputStream();
		DataInputStream dis = new DataInputStream(is);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		OutputStream os = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		ObjectInputStream ois = new ObjectInputStream(is);
		String str1 = br.readLine();
		dos.writeUTF(str1);
		dos.flush();
		String str2 = br.readLine();
		dos.writeUTF(str2);
		dos.flush();
		String str3 = dis.readUTF();
		System.out.println("��A��A���ܵ�B������:" + str3);
		if ("y".equalsIgnoreCase(str3)) {
			System.out.println("��A��ABͨ����ʼ......");
			System.out.println("��A��A����B�Ĺ�Կ��ʼ......");
			setPubKey((RSAPublicKey) ois.readObject());
			System.out.println("��A��A����B�Ĺ�Կ���......");
			send();
			System.out.println("��A��A��ʼ������B��Կ���ܹ�������......");
			File file = new File(RSA_SIGN_FILE);
			long length= file.length();
			dos.writeLong(length);
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(RSA_SIGN_FILE));
			int a;
			while ((a = bis.read()) != -1) {
				dos.write(a);
			}
			bis.close();
			BufferedInputStream bis2 = new BufferedInputStream(
					new FileInputStream(RSA_FILE));
			int b;
			while ((b = bis2.read()) != -1) {
				dos.write(b);
			}
			bis2.close();
			
			System.out.println("��A��A���������ļ����......");
			System.out.println("��A��ABͨ������......");
		}
		
		
		socket.close();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("A:");
		A a = new A();
		a.init();
		a.start();
	}
}
