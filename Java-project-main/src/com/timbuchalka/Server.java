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



    public void run() {
        String[][] quiz = {
                {"______ acts as a bridge between the applications and data processing performed \n at hardware using the interprocess communication \n and the system calls. The ______ does tasks like disk management, task management \nand file management.", "kernel"},
                {"_______ memory is a memory management technique for letting processes execute \noutside of memory. This is very useful especially is an executing program \n cannot fit in the physical memory.", "virtual"},
                {"The main objective of _____programming is to have a process running at all times. With this design, CPU utilization is said to be maximized.", "multi"},
                {"In a Time-sharing system, the CPU executes multiple jobs by switching \namong them, also known as __________. This process happens so fast that\n users can interact with each program while it is running.", "multitasking"},
                {"____ It is one type of scheduling algorithm. In this scheme, the process\n that requests the CPU first is allocated the CPU first. Implementation is \nmanaged by a FIFO queue", "fcfs"},
                {"__ scheduling algorithm is primarily aimed for time-sharing systems. A circular queue \nis a setup in such a way that the CPU scheduler goes around \nthat queue, allocating CPU to each process for a time interval of up to around 10 to 100 milliseconds.", "rr"},
                {"________ situations occur when four conditions occur simultaneously in a system: Mutual exclusion; Hold and Wait; No preemption; and Circular wait.", "deadlock"},
                {"Caching is the processing of utilizing a region of fast memory for a limited data and process. \nA cache memory is usually much efficient because of \nits high _____ speed.", "access"},
                {"_______ is used to enable a process to be larger than the amount of memory allocated to it. \nThe basic idea of this is that only instructions and data \n that are needed at any given time are kept in memory.", "overlay"},
                {"A ______ provides a connection between two applications. Each endpoint of a communication is a ______.", "socket"},
                {"Demand ______ is referred when not all of a process’s pages are in the RAM, \nthen the OS brings the missing(and required) pages from the disk into the RAM.\n" +
                        "\n", "paging"},
                {"The __ is available upon booting and supports hardware’s core functioning, including support for I/O devices, internal and external storage retrieval,\n and more. It is an essential part of computer and server functioning", "os"},
        };
        String message;
        int count = 0;
        int point = 0;
        try {
            while (count < quiz.length){
                Random random = new Random();
                int index = random.nextInt(12);
                String[] question = quiz[index];
                output.println('\n'+ question[0]);
                message = input.readLine();
                for (ClientHandler client : clients) {
                    client.output.println("\n Tour answer is " + (message));
                    client.output.println("\nClient answered a question");
                    count+= 1;
                    count++;
                    if ( message.equalsIgnoreCase(question[1]) ){
                        point+= 1;
                        client.output.println("\nCorrect +1 point , you now have " + (point) + " point");
                    }else{ client.output.println("\n Wrong you have" + point );}
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
