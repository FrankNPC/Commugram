package universal.message.plugin;

/**
 * messages between client, server and third peer possibly.
 * the negative value is for the error. the positive is for the notice, where the next step should be allowed to go through.
 * the values fall into a regular: 
 * 		when UID=100000000, the error belongs it will be -UID-n: -100000001.
 * 		when UID=100000000, the notice belongs it will be UID+n:  100000001.
 * the values which increase from the head is for distinguish the information category.
 * the values which increase from the tail is for sub error/notice.
 * 
 * @author FrankNPC
 *
 */
public interface Constant {
	public static int SYSTEM = 						0x00000000;//preserved for internal system.
	public static int LOCKED_ACCESS = 			   -0x00000001;//the access has locked by user temporarily.
	public static int ERROR_PARSE = 			   -0x00000002;//fail to resolve data structure..
	
	public static int UID = 						0x01000000;//UID Object.
	
	public static int PLUGIN = 						0x02000000;//Plugin Object.
	
	public static int USER = 						0x04000000;//User Object.
	
	public static int STREAM = 					 	0x10000000;//Stream Object.
	public static int STREAM_FILE = 			 	0x11000000;
	public static int STREAM_VIDEO = 			 	0x12000000;
	public static int STREAM_AUDIO = 			 	0x13000000;
	public static int STREAM_IMAGE = 			 	0x14000000;

	public static int MESSAGE = 					0x40000000;//Message Object.
	public static int MESSAGE_KEY = 				0x41000000;//for encrypt message content till the end or the message tagged by MESSAGE_KEY.
	public static int MESSAGE_TEXT = 				0x42000000;
	public static int MESSAGE_COMBINED = 			0x43000000;
	public static int MESSAGE_HTML = 				0x44000000;
	public static int MESSAGE_FILE = 				0x45000000;
	public static int MESSAGE_VOICE = 				0x46000000;
	public static int MESSAGE_IMAGE =				0x47000000;
	public static int MESSAGE_VIDEO = 				0x48000000;
}
