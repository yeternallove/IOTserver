package cn.edu.zucc.eternallove.iotserver.data;

import cn.edu.zucc.eternallove.iotserver.util.DataDeal;

/**
 * 6 * Created by falling on 2015/11/24. 7
 */
public class SendSensorData {
	private int V1;
	private int V2;
	private int v3;

	private byte[] bytes = new byte[12];

	public int getV1() {
		return V1;
	}

	public void setV1(int v1) {
		V1 = v1;
	}

	public int getV2() {
		return V2;
	}

	public void setV2(int v2) {
		V2 = v2;
	}

	public int getV3() {
		return v3;
	}

	public void setV3(int v3) {
		this.v3 = v3;
	}

	public void setBytes(byte[] bytes) {

		this.bytes = bytes;
	}

	public byte[] getBytes() {
		byte[] b = DataDeal.intTobyte(this.getV1());
		int i;
		for (i = 0; i < 4; i++) {
			bytes[i] = b[i];
		}

		b = DataDeal.intTobyte(this.getV2());
		for (i = 4; i < 8; i++) {
			bytes[i] = b[i - 4];
		}

		b = DataDeal.intTobyte(this.getV3());
		for (i = 8; i < 12; i++) {
			bytes[i] = b[i - 8];
		}

		return bytes;
	}
}
