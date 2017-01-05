package cn.edu.zucc.eternallove.iotserver.data;

import android.util.Log;

import cn.edu.zucc.eternallove.iotserver.util.DataDeal;

/**
 * 6 * Created by falling on 2015/11/6. 7
 */
public class SendIOTData {
	private byte START_F0;
	private byte START_F1;
	private byte START_F2;
	private byte START_F3;
	private byte sensor_num; // 传感器数量
	private short heartbeat;
	// 7
	private SendSensorData sensor_dev1; // RGB 13
	private SendSensorData sensor_dev2; // MOTO
	private SendSensorData sensor_dev3; // LED

	byte[] bytes = new byte[45];

	// 39+7 46
	// 8+36=44
	public SendIOTData() {
		START_F0 = (byte) 0xAA;
		START_F1 = (byte) 0xAA;
		START_F2 = (byte) 0xAA;
		START_F3 = (byte) 0xAB;
		sensor_dev1 = new SendSensorData();
		sensor_dev2 = new SendSensorData();
		sensor_dev3 = new SendSensorData();
		sensor_num = 3;
	}

	public byte getSTART_F0() {
		return START_F0;
	}

	public void setSTART_F0(byte START_F0) {
		this.START_F0 = START_F0;
	}

	public byte getSTART_F1() {
		return START_F1;
	}

	public void setSTART_F1(byte START_F1) {
		this.START_F1 = START_F1;
	}

	public byte getSTART_F2() {
		return START_F2;
	}

	public void setSTART_F2(byte START_F2) {
		this.START_F2 = START_F2;
	}

	public byte getSTART_F3() {
		return START_F3;
	}

	public void setSTART_F3(byte START_F3) {
		this.START_F3 = START_F3;
	}

	public byte getSensor_num() {
		return sensor_num;
	}

	public void setSensor_num(byte sensor_num) {
		this.sensor_num = sensor_num;
	}

	public short getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(short heartbeat) {
		this.heartbeat = heartbeat;
	}

	public SendSensorData getSensor_dev1() {
		return sensor_dev1;
	}

	public void setSensor_dev1(SendSensorData sensor_dev1) {
		this.sensor_dev1 = sensor_dev1;
	}

	public SendSensorData getSensor_dev2() {
		return sensor_dev2;
	}

	public void setSensor_dev2(SendSensorData sensor_dev2) {
		this.sensor_dev2 = sensor_dev2;
	}

	public SendSensorData getSensor_dev3() {
		return sensor_dev3;
	}

	public void setSensor_dev3(SendSensorData sensor_dev3) {
		this.sensor_dev3 = sensor_dev3;
	}

	public byte[] getBytes() {
		bytes[0] = this.getSTART_F0();
		bytes[1] = this.getSTART_F1();
		bytes[2] = this.getSTART_F2();
		bytes[3] = this.getSTART_F3();
		bytes[4] = this.getSensor_num(); // 传感器数量
		byte[] t = DataDeal.shortToBytes(this.getHeartbeat());

		// 心跳数
		bytes[5] = t[0];
		bytes[6] = t[1];

		// 传感器数据
		byte[] t1 = this.getSensor_dev1().getBytes();
		byte[] t2 = this.getSensor_dev2().getBytes();
		byte[] t3 = this.getSensor_dev3().getBytes();

		for (int i = 0; i < 12; i++) {
			bytes[7 + i] = t1[i];
			bytes[19 + i] = t2[i];
			bytes[31 + i] = t3[i];
		}
		DataDeal.setCheckCode(bytes);

		return bytes;
	}
}