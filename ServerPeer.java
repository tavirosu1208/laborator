/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriaf.poo.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.*;
import java.net.Socket;
import seriaf.poo.structs.*;

/**
 *
 * @author professor
 */
public class ServerPeer implements Runnable{

    private final Socket mSocket;
    private String username;
    private Server server;
    private ObjectOutputStream obj;

    public ServerPeer(Server server,Socket communicationSocket) {
        mSocket = communicationSocket;
        this.server = server;
        try{
            OutputStream os = mSocket.getOutputStream();
            this.obj = new ObjectOutputStream(os);
        }
        catch(IOException e){
            
        }
    }

    @Override
    public void run() {
        Message mesaj;
        try {
            ObjectInputStream stream = new ObjectInputStream(mSocket.getInputStream());

            while (true) {
                mesaj = (Message)stream.readObject();
                username = mesaj.getSender();
                System.out.println(mesaj.toString().trim());
                if(mesaj instanceof PrivateMessage)
                {
                    sendMessage(mesaj);
                    server.dispatch(mesaj);
                }
                else{
                    server.dispatch(mesaj);
                }
            }
        } catch (EOFException ex) {
            // client disconnected gracefully so do nothing
            server.removeClient(this);
        } catch (IOException ex) {
            System.err.println("Client connection reset: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("Unknown object received.");
        }
    }
    private void sendMessage(Message mesaj){
        try{
        obj.writeObject(mesaj);
        }
        catch(IOException e){
            
        }
    }
    public String getUsername(){
        return username;
    }
    public ObjectOutputStream getObjectStream(){
        return this.obj;
    }
}
