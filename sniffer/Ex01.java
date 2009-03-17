import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class Ex01 {

	public static void main(String[] args) throws Exception {
		
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		for (NetworkInterface ni : devices) {
			System.out.println(ni.datalink_description);
			System.out.println(ni.datalink_name);
			System.out.println(ni.description);
			System.out.println(ni.name);
			System.out.println(ni.toString());
			System.out.println("*******************************");
		}
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[1], 65535, true, 20);
		captor.setFilter(
				"src host 192.168.3.14 and dst host 222.73.180.73"
//				"dst host 60.28.212.248"
//				"not host 192.168.3.253 and " +
//				"not host 192.168.3.249 and " +
//				"not host 192.168.3.246 and " +
//				"not host 222.202.96.176 and " +
//				"not host 65.54.228.18 and " +
//				"not host 65.54.228.33 and " + 
//				"not host 207.46.26.25 and " +
//				"not host 207.46.107.108 and " +
//				"not host 60.29.242.246 and " +
//				"not host 202.108.53.179 and " +
//				"not host 192.168.3.255 and " +
//				"not host 202.120.61.3"
//				"not host 207.46.26.50 and " +
//				"not host 64.4.36.48 and " +
//				"not host 207.46.26.30 and " +
//				"not host 60.28.218.122 and " +
//				"not host 64.4.34.63 and " +
//				"not host 222.73.180.73"
				, true);
//		captor.setFilter("dst host 192.168.3.253", false);
//		captor.setFilter("src host 192.168.1.100 and dst host www.126.com", true);
//		captor.setFilter("host www.21cn.com", true);
		captor.loopPacket(-1, new PacketPrinter());
		captor.close();
	}
}

class PacketPrinter implements PacketReceiver {  
	//this method is called every time Jpcap captures a packet  
	public void receivePacket(Packet packet) {    
		//just print out a captured packet
		if (packet instanceof IPPacket) {
			IPPacket ipp = (IPPacket)packet;
			System.out.println(ipp.src_ip + "(" + ipp.src_ip.getHostName() + ") -> " 
					+ ipp.dst_ip + "(" + ipp.dst_ip.getHostName() + ")");
			System.out.println(new String(ipp.data));
			if (ipp.data.length != 0 && !new String(ipp.data).contains("CKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")) {
				DataOutputStream dos = null;
				try {
					dos = new DataOutputStream(
							new FileOutputStream("d:\\files\\recreation\\farmlandSniffRecord", 
									true));
					dos.write(ipp.dst_ip.toString().getBytes());
					dos.write("\n".getBytes());
					dos.write(ipp.data);
					dos.write("\n\n".getBytes());
					dos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("*************************************************************");
		}
	}
}
