package com.timbuchalka;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Client() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("R JARN PAO GAME");
        setSize(600, 600);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.SOUTH);

        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputPanel.add(inputField, BorderLayout.CENTER);

        sendButton = new JButton("Send answer");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputPanel.add(sendButton, BorderLayout.EAST);

        setVisible(true);
    }

    private void sendMessage() {
        String message = inputField.getText();
        output.println(message);
        inputField.setText("");
    }

    public void connect() throws IOException {
        socket = new Socket("localhost", 9001);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                try {
                    while ((message = input.readLine()) != null) {
                        chatArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        readerThread.start();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect();
    }
}