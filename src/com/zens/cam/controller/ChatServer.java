package com.zens.cam.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class ChatServer extends WebSocketServer {
    private String fileName = null;

    public ChatServer( int port ) throws UnknownHostException {
        super( new InetSocketAddress( InetAddress.getByName( "localhost" ), port ) );
    }
    
    public ChatServer( InetSocketAddress address ) {
        super( address );
    }
    public ChatServer( String address ,int port) throws UnknownHostException {
        super( new InetSocketAddress( InetAddress.getByName( address ), port ) );
    }

    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
        try {
            this.sendToAll( conn + " entered the room!" );
        } catch ( InterruptedException ex ) {
            ex.printStackTrace();
        }
        System.out.println( conn + " entered the room!" );
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
        try {
            this.sendToAll( conn + " has left the room!" );
        } catch ( InterruptedException ex ) {
            ex.printStackTrace();
        }
        System.out.println( conn + " has left the room!" );
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        conn.getRemoteSocketAddress().getAddress().getAddress();
        try {
            this.sendToAll( conn + "说: " + message );
        } catch ( InterruptedException ex ) {
            ex.printStackTrace();
        }
       System.out.println( conn + ": " + message );
        byte[] fileBanary = message.getBytes();
        saveFileFromBytes(fileBanary, "/Users/zyq/MyWork/test.txt");
        fileName = message;
        System.out.println("收到字符串流");
    }
    
    public void onMessage(WebSocket conn, byte[] message) {
        saveFileFromBytes(message, "/Users/zyq/MyWork/" + fileName);
        System.out.println("收到2进制流");
    }

    public static void main( String[] args ) throws InterruptedException , IOException {
       // WebSocket.DEBUG = false;
        int port = 8080;
        try {
            port = Integer.parseInt( args[ 0 ] );
        } catch ( Exception ex ) {
        }
        ChatServer s = new ChatServer("localhost", port );
        s.start();
        System.out.println( "ChatServer started on port: " + s.getPort() );

        BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
        while ( true ) {
            String in = sysin.readLine();
            s.sendToAll( in );
        }
    }

    @Override
    public void onError( WebSocket conn, Exception ex ) {
        ex.printStackTrace();
    }

    /**
     * Sends <var>text</var> to all currently connected WebSocket clients.
     * 
     * @param text
     *            The String to send across the network.
     * @throws InterruptedException
     *             When socket related I/O errors occur.
     */
    public void sendToAll( String text ) throws InterruptedException {
        for( WebSocket c : connections() ) {
            c.send( text );
        }
    }
    
    public static boolean saveFileFromBytes(byte[] b, String outputFile)  
      {  
        BufferedOutputStream stream = null;  
        File file = null;  
        try  
        {  
          file = new File(outputFile);  
          FileOutputStream fstream = new FileOutputStream(file);  
          stream = new BufferedOutputStream(fstream);  
          stream.write(b);  
        }  
        catch (Exception e)  
        {  
          e.printStackTrace();
          return false;
        }  
        finally  
        {  
          if (stream != null)  
          {  
            try  
            {  
              stream.close();  
            }  
            catch (IOException e1)  
            {  
              e1.printStackTrace();  
            }  
          }  
        }  
        return true;  
      }  
}