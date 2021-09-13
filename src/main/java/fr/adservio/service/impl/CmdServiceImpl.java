package fr.adservio.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import fr.adservio.service.CmdService;

@Service
public class CmdServiceImpl implements CmdService {

	private Logger logger = LoggerFactory.getLogger(CmdServiceImpl.class);

	private String strUserName;
	private String strConnectionIP;
	private int intConnectionPort;
	private String strPassword;
	private Session sesConnection;

	@Value("${sftp.sessionTimeout}")
	private Integer sessionTimeout;

	@Value("${sftp.channelTimeout}")
	private Integer channelTimeout;

	private JSch jschSSHChannel;

	@Override
	public String runCmd(String cmd) {

		StringBuilder outputBuffer = new StringBuilder();
		 try
	     {
	        Channel channel = sesConnection.openChannel("exec");
	        ((ChannelExec)channel).setCommand(cmd);
	        InputStream commandOutput = channel.getInputStream();
	        channel.connect();
	        int readByte = commandOutput.read();

	        while(readByte != 0xffffffff)
	        {
	           outputBuffer.append((char)readByte);
	           readByte = commandOutput.read();
	        }

	        channel.disconnect();
	     }
	     catch(IOException ioX)
	     {
	        logger.error(ioX.getMessage());
	        return null;
	     }
	     catch(JSchException jschX)
	     {
	    	 logger.error("Error {}",jschX);
	        return null;
	     }

	     return outputBuffer.toString();
	}


	private void doCommonConstructorActions(String userName, String password, String connectionIP,
			String knownHostsFileName) {
		jschSSHChannel = new JSch();
		strUserName = userName;
		strPassword = password;
		strConnectionIP = connectionIP;

	}


	@Override
	public void SSHManager(String userName, String password, String connectionIP, int connectionPort) {
		doCommonConstructorActions(userName, password, connectionIP, "");
		intConnectionPort = connectionPort;
	}

	@Override
	public void SSHManager(String userName, String password, String connectionIP, String knownHostsFileName,
			int connectionPort) {
		doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
		intConnectionPort = connectionPort;

	}

	@Override
	public void SSHManager(String userName, String password, String connectionIP, String knownHostsFileName,
			int connectionPort, int timeOutMilliseconds) {
		doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
		intConnectionPort = connectionPort;

	}

	@Override
	public boolean connect() {
		String errorMessage = null;
		boolean status = true;

		try {
			sesConnection = jschSSHChannel.getSession(strUserName, strConnectionIP, intConnectionPort);
			sesConnection.setPassword(strPassword);
			sesConnection.setConfig("StrictHostKeyChecking", "no");
			sesConnection.connect(sessionTimeout);
			//status = true;
		} catch (JSchException jschX) {
			status= false;
			errorMessage = jschX.getMessage();
			logger.error("Connection error {}", errorMessage);
		}

		return status;
	}

	@Override
	public void close() {
		sesConnection.disconnect();

	}

	public Session getSesConnection() {
		return sesConnection;
	}

	public void setSesConnection(Session sesConnection) {
		this.sesConnection = sesConnection;
	}

}
