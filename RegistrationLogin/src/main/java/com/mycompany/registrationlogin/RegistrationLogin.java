package com.mycompany.registrationlogin;

import java.util.Scanner;

public class RegistrationLogin {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LoginClass auth = new LoginClass();
        Message msgTool = new Message();

        System.out.println("--- QUICKCHAT ACCOUNT CREATION ---");
        System.out.print("Enter First Name: ");
        String first = input.nextLine();

        System.out.print("Enter Last Name: ");
        String last = input.nextLine();
        
        String username = "";
        while (true) {
            System.out.print("Enter Username: ");
            username = input.nextLine();
            if (auth.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.");
            }
        }
        
        String password = "";
        while (true) {
            System.out.print("Enter Password: ");
            password = input.nextLine();
            if (auth.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.");
            }
        }
        
        String cell = "";
        while (true) {
            System.out.print("Enter Phone Number: ");
            cell = input.nextLine();
            if (auth.checkCellPhoneNumber(cell)) {
                break;
            } else {
                System.out.println("The phone number format is not correct. It must start with '+' and be 13 digits or fewer.");
            }
        }
        
        String regStatus = auth.registerUser(username, password, cell);
        System.out.println("\n" + regStatus);
        
        if (regStatus.contains("successfully")) {
            System.out.println("\nWelcome to QuickChat.");
            System.out.println("--- SIGN IN ---");
            boolean loginSuccessful = false;

            while (!loginSuccessful) {
                System.out.print("Username: ");
                String logUsername = input.nextLine();

                System.out.print("Password: ");
                String logPassword = input.nextLine();

                loginSuccessful = auth.loginUser(logUsername, logPassword);
                System.out.println(auth.returnLoginStatus(loginSuccessful, first, last));
            }

            // Global State Accumulators for Session Messages
            int totalMessagesSent = 0;
            
            // App Core Numeric Control Loop
            boolean running = true;
            while (running) {
                System.out.println("\n--- MENU ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose an option: ");
                
                String optionStr = input.nextLine();
                int option = 0;
                try {
                    option = Integer.parseInt(optionStr);
                } catch (NumberFormatException e) {
                    option = -1;
                }

                switch (option) {
                    case 1:
                        System.out.print("How many messages do you wish to enter? ");
                        int count = Integer.parseInt(input.nextLine());
                        
                        for (int i = 0; i < count; i++) {
                            System.out.println("\n--- Entering Message " + (i + 1) + " of " + count + " ---");
                            
                            // 1. Recipient Capture
                            String recipient;
                            while (true) {
                                System.out.print("Enter Recipient Phone Number: ");
                                recipient = input.nextLine();
                                String checkCell = msgTool.checkRecipientCell(recipient);
                                System.out.println(checkCell);
                                if (checkCell.contains("successfully")) {
                                    break;
                                }
                            }
                            
                            // 2. Message Content Capture
                            String txt;
                            while (true) {
                                System.out.print("Enter Message text: ");
                                txt = input.nextLine();
                                String checkLen = msgTool.checkMessageLength(txt);
                                if (checkLen.contains("ready")) {
                                    System.out.println("Message sent");
                                    break;
                                } else {
                                    System.out.println(checkLen);
                                }
                            }
                            
                            // 3. Automated Data Fields Generation
                            String msgId = msgTool.generateMessageID();
                            String hash = msgTool.createMessageHash(msgId, i, txt);
                            
                            System.out.println("Message ID generated: " + msgId);
                            System.out.println("Message Hash: " + hash);
                            
                            // 4. Action Option
                            System.out.println("Choose an action:\n1) Send Message\n2) Disregard Message\n3) Store Message to send later");
                            int action = Integer.parseInt(input.nextLine());
                            System.out.println(msgTool.storeMessage(action));
                            
                            if (action == 1) {
                                totalMessagesSent++;
                            }
                            
                            // Print out message summary sequence
                            System.out.println("\n--- Message Summary Details ---");
                            System.out.println("Message ID: " + msgId);
                            System.out.println("Message Hash: " + hash);
                            System.out.println("Recipient: " + recipient);
                            System.out.println("Message: " + txt);
                            
                            // Export to JSON Display Simulation
                            String json = msgTool.storeMessageInJSON(msgId, hash, recipient, txt, i);
                            System.out.println("\n--- Stored JSON Record ---");
                            System.out.println(json);
                        }
                        
                        System.out.println("\nTotal accumulated messages sent this session: " + totalMessagesSent);
                        break;
                        
                    case 2:
                        System.out.println("Coming Soon.");
                        break;
                        
                    case 3:
                        System.out.println("Exiting Application. Goodbye!");
                        running = false;
                        break;
                        
                    default:
                        System.out.println("Invalid option, please choose 1, 2, or 3.");
                        break;
                }
            }
        }
    }
}