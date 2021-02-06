package server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This is the chat server program.
 * Press Ctrl + C to terminate the program.
 * 
 *
 * @author www.codejava.net
 * @author Adapted by Sean McNamee
 */
public class ChatServer {
    private static int port = 8989; //When changing, adjust client side as well

    //TODO change this to be a Set of Sets. Doing so would allow getting all of a groups' members quickly.
    private Set<UserThread> userThreads = new HashSet<>(); //What clients are connected
 
    public ChatServer() {

    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.execute();
    }
 
    /**
     * Handles the overall server logic. create ServerSocket, accept incoming sockets.
     */
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Chat Server is listening on port " + port);
            
            //Wait for a user to connect. start a UserThread to deal with communication.
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");
 
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
 
            }
 
        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    /**
     * Delivers a message from one user to others (broadcasting) in the same group
     */
    void broadcast(String message, UserThread sendingClient) {
        for (UserThread aUser : userThreads) {
            if (aUser != sendingClient && aUser.getGroup().equals(sendingClient.getGroup())) {
                aUser.sendMessage(message);
            }
        }
    }
 
    /**
     * When a client is disconneted, removes the associated UserThread
     */
    void removeUser(UserThread aUser) {
        userThreads.remove(aUser);
        System.out.println("The user " + aUser.getUserName() + " quitted");
    }
 
 
    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    boolean hasUsers() {
        return !this.userThreads.isEmpty();
    }
}


