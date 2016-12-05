/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriaf.poo.client;

import java.io.IOException;
import java.io.*;
import java.net.Socket;
import seriaf.poo.structs.Message;
import seriaf.poo.structs.PrivateMessage;

/**
 *
 * @author professor
 */
//transformam clasa intr-un fir de executie
public class ClientPeer implements Runnable{ 

    private final ObjectOutputStream mObjectStream;
    private final String mSender;
    private final Socket socket;

    public ClientPeer(String sender, Socket communicationSocket) throws IOException {
        mSender = sender;
        mObjectStream = new ObjectOutputStream(communicationSocket.getOutputStream());
        this.socket = communicationSocket;
    }

    public void sendMessage(String message) throws IOException {
        mObjectStream.writeObject(new Message(mSender, message));
    }

    public void sendMessage(String recipient, String message) throws IOException {
        mObjectStream.writeObject(new PrivateMessage(recipient, mSender, message));
    }
    //metoda run a thread-ului
    @Override
    public void run (){
        try{
            //citeste mesajele venite de la server
            InputStream str = socket.getInputStream();
            ObjectInputStream stream = new ObjectInputStream(str);
            while(true){
                Message mesaj;
                mesaj = (Message)stream.readObject();
                System.out.println(mesaj.toString().trim());
            }
          }
            catch(IOException | ClassNotFoundException ex){
                    
                    }
        }
    }
