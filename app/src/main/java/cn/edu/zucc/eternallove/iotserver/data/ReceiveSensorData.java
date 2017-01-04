package cn.edu.zucc.eternallove.iotserver.data;


import cn.edu.zucc.eternallove.iotserver.util.DataDeal;

/**
 * Created by falling on 2015/11/18.
 */
public class ReceiveSensorData {
	private char ID;
	private int v1;
	private int v2;
	private int v3;
	private short vnot;
	private byte none;

	public ReceiveSensorData(byte[] bytes) {
		ID = (char) bytes[0];
		v1 = DataDeal.bytesToInt(bytes, 1);
		v2 = DataDeal.bytesToInt(bytes, 5);
		v3 = DataDeal.bytesToInt(bytes, 9);
		vnot = DataDeal.bytesToShort(bytes, 13);
		none = (byte) bytes[15];
	}

	public char getID() {
		return ID;
	}

	public void setID(char ID) {
		this.ID = ID;
	}

	public int getV1() {
		return v1;
	}

	public void setV1(int v1) {
		this.v1 = v1;
	}

	public int getV2() {
		return v2;
	}

	public void setV2(int v2) {
		this.v2 = v2;
	}

	public int getV3() {
		return v3;
	}

	public void setV3(int v3) {
		this.v3 = v3;
	}

	public short getVnot() {
		return vnot;
	}

	public void setVnot(short vnot) {
		this.vnot = vnot;
	}
}