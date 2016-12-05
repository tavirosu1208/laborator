/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriaf.poo.server;

import java.net.*;
import seriaf.poo.server.config.ServerConfig;
import java.util.*;
import java.io.*;
import seriaf.poo.structs.*;

/**
 *
 * @author professor
 */
public class Server {
    
    public ArrayList<ServerPeer> clientList = new ArrayList<>();
    private ServerSocket serverSocket;
    private final int maxClients;
   
    public Server(int tcpPort, int maxClients){
        try{
            this.serverSocket = new ServerSocket(tcpPort);
        }catch(IOException e){
            
        }
        this.maxClients = maxClients;
    }
    
    public static void main(String[] args) {
        try {
            ServerConfig config = new ServerConfig();
            Server server = new Server(config.getTcpPort(),config.getMaxClients());
            server.listen(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void listen(Server server) throws IOException{
        while(true){
            if(clientList.size() <= this.maxClients){
                ServerPeer peer = new ServerPeer(server,serverSocket.accept());
                clientList.add(peer);
                (new Thread(peer)).start();
            }
        }
    }
    
    public synchronized void dispatch(Message mesaj){
        for(ServerPeer peer : clientList){
            if(mesaj instanceof PrivateMessage){
                if(peer.getUsername().equals(((PrivateMessage)mesaj).getRecipient())){
                    try{
                        ObjectOutputStream objectStream = peer.getObjectStream();
                        objectStream.writeObject(mesaj);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            else{
                try{
                    ObjectOutputStream objectStream = peer.getObjectStream();
                    objectStream.writeObject(mesaj);
                }catch(Exception e){
                        e.printStackTrace();
                    }
            }
        }
    }
    
    public synchronized void removeClient(ServerPeer serverPeer){
        clientList.remove(serverPeer);
    }
}
