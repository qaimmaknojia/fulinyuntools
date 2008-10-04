import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class Ex01 {

	public static void main(String[] args) throws Exception {
		
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
//		for (NetworkInterface ni : devices) {
//			System.out.println(ni.datalink_description);
//			System.out.println(ni.datalink_name);
//			System.out.println(ni.description);
//			System.out.println(ni.name);
//			System.out.println(ni.toString());
//			System.out.println("*******************************");
//		}
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[1], 65535, true, 20);
		captor.setFilter("src host 192.168.1.100", true);
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
			System.out.println("*************************************************************");
		}
	}
}
