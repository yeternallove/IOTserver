package cn.edu.zucc.eternallove.iotserver.data;

import cn.edu.zucc.eternallove.iotserver.util.DataDeal;

/**
 * Created by falling on 2015/10/24.
 */
public class ReceiveIOTData {
	private char ID;
	private char sensor_num;
	private short hearbeat;
	private ReceiveSensorData sensor_data_frame1;
	private ReceiveSensorData sensor_data_frame2;
	private ReceiveSensorData sensor_data_frame3;

	private byte[] bytes;

	public ReceiveIOTData(byte[] b) throws Exception {
		this.bytes = b;
		if (bytes[0] == (byte) 0xAA && bytes[1] == (byte) 0xAA
				&& bytes[2] == (byte) 0xAA && bytes[3] == (byte) 0xAB) {
			if (DataDeal.check(bytes)) {
				System.out.println(" 数据有效 !");
			} else {
				throw new Exception(" 数据错误 !");
			}
		} else {
			throw new Exception(" 起始标志错误 !");
		}

//		ID = (char) new byte[]{0,};
		ID = '0';
//		sensor_num = (char)bytes[5];
		sensor_num = '3';
		hearbeat = DataDeal.bytesToShort(bytes,6);
		
		byte[] date1 = new byte[16];
		byte[] date2 = new byte[16];
		byte[] date3 = new byte[16];

		for (int i = 0; i < 16; i++) {
			date1[i] = bytes[i + 8];
			date2[i] = bytes[i + 24];
			date3[i] = bytes[i + 40];
		}

		sensor_data_frame1 = new ReceiveSensorData(date1);
		sensor_data_frame2 = new ReceiveSensorData(date2);
		sensor_data_frame3 = new ReceiveSensorData(date3);
	}

	public char getID() {
		return ID;
	}

	public void setID(char ID) {
		this.ID = ID;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public ReceiveSensorData getSensor_data_frame3() {
		return sensor_data_frame3;
	}

	public void setSensor_data_frame3(ReceiveSensorData sensor_data_frame3) {
		this.sensor_data_frame3 = sensor_data_frame3;
	}

	public ReceiveSensorData getSensor_data_frame2() {
		return sensor_data_frame2;
	}

	public void setSensor_data_frame2(ReceiveSensorData sensor_data_frame2) {
		this.sensor_data_frame2 = sensor_data_frame2;
	}

	public ReceiveSensorData getSensor_data_frame1() {
		return sensor_data_frame1;
	}

	public void setSensor_data_frame1(ReceiveSensorData sensor_data_frame1) {
		this.sensor_data_frame1 = sensor_data_frame1;
	}

	public short getHearbeat() {
		return hearbeat;
	}

	public void setHearbeat(short hearbeat) {
		this.hearbeat = hearbeat;
	}

	public char getSensor_num() {
		return sensor_num;
	}

	public void setSensor_num(char sensor_num) {
		this.sensor_num = sensor_num;
	}
}