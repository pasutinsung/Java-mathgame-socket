package com.timbuchalka;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static int clientCount = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9001);
        System.out.println("Server is running...");
        while (true) {
            Socket clientSocket = server.accept();
            System.out.println("Client " + ++clientCount + " connected.");
            ClientHandler client = new ClientHandler(clientSocket, clients, clientCount);
            clients.add(client);
            new Thread(client).start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private ArrayList<ClientHandler> clients;
    private int id;

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients, int id) throws IOException {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.id = id;
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream(), true);

    }
    public static String newEquation() {
        Random rand = new Random();
        String question = Integer.toString(rand.nextInt(9) + 1);
        int operands = rand.nextInt(3) + 2;
        String[] operators = {"+", "-", "*", "/"};
        for (int i = 1; i < operands; i++) {
            String operator = operators[rand.nextInt(operators.length)];
            String operand = Integer.toString(rand.nextInt(9) + 1);
            question += " " + operator + " " + operand;
        }
        return question;
    }

    public void run() {
        String message;
        int count = 0;
        int point = 0 ;
        try {
            while (true){
                message = input.readLine();
                String question = newEquation();
                output.println(question);
                for (ClientHandler client : clients) {
                    client.output.println("Client answered " + (count+1) + "question from " + (10));
                    count+= 1;
                }

            }
        } catch (IOException e) {
            System.out.println("Client " + id + " disconnected.");
            clients.remove(this);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
