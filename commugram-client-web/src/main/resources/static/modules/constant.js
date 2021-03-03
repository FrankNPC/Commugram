
var SYSTEM = 						0x00000000;//preserved for internal system.
var LOCKED_ACCESS = 			   -0x00000001;//the access has locked by user temporarily.
var ERROR_PARSE = 			   -0x00000002;//fail to resolve data structure..
	
var UID = 						0x01000000;//UID Object.
	
var PLUGIN = 						0x02000000;//Plugin Object.
	
var USER = 						0x04000000;//User Object.
	
var STREAM = 					 	0x10000000;//Stream Object.
var STREAM_FILE = 			 	0x11000000;
var STREAM_VIDEO = 			 	0x12000000;
var STREAM_AUDIO = 			 	0x13000000;
var STREAM_IMAGE = 			 	0x14000000;

var MESSAGE = 					0x40000000;//Message Object.
var MESSAGE_KEY = 				0x41000000;//for encrypt message content till the end or the message tagged by MESSAGE_KEY.
var MESSAGE_TEXT = 				0x42000000;
var MESSAGE_COMBINED = 			0x43000000;
var MESSAGE_HTML = 				0x44000000;
var MESSAGE_FILE = 				0x45000000;
var MESSAGE_VOICE = 				0x46000000;
var MESSAGE_IMAGE =				0x47000000;
var MESSAGE_VIDEO = 				0x48000000;