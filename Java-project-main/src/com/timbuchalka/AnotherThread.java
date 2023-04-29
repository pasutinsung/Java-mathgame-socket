// package com.timbuchalka;

// package com.timbuchalka;

// import java.io.*;
// import java.net.*;
// import java.util.Scanner;

// public class AnotherThread {
// public static void main(String[] args) throws IOException {
// Scanner scanner = new Scanner(System.in);
// Socket socket = new Socket("localhost", 5000);
// BufferedReader input = new BufferedReader(new
// InputStreamReader(socket.getInputStream()));
// PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

// Thread readerThread = new Thread(new Runnable() {
// @Override
// public void run() {
// String message;
// try {
// while ((message = input.readLine()) != null) {
// System.out.println(message);
// }
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
// });
// readerThread.start();

// String message;
// while ((message = scanner.nextLine()) != null) {
// output.println(message);
// }

// readerThread.interrupt();
// socket.close();
// scanner.close();
// }
// }
