package fr.adservio.service;

public interface CmdService {
  	
	String runCmd(String cmd); 
	
	 void  SSHManager(String userName, String password, 
		     String connectionIP, int connectionPort );
	 
	 
	 void  SSHManager(String userName, String password, String connectionIP, 
		     String knownHostsFileName, int connectionPort);
	 
	 void SSHManager(String userName, String password, String connectionIP, 
		      String knownHostsFileName, int connectionPort, int timeOutMilliseconds);
	 
	 /**
	  * 
	  * @return
	  */
	 boolean connect();
	 
	 void close();
}
