package com.mycompany.registrationlogin;

import java.util.Random;

public class Message {

    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_MSG_LENGTH = 250;

    public boolean checkMessageID(String messageID) {
        if (messageID == null) return false;
        return messageID.length() <= MAX_ID_LENGTH;
    }

    public String checkRecipientCell(String cell) {
        if (cell == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        // FIX: Must start with '+' and be 13 digits or fewer total (as per project rules)
        if (cell.startsWith("+") && cell.length() <= 13) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String createMessageHash(String messageId, int messageNum, String message) {
        if (messageId == null || message == null || messageId.length() < 2 || message.trim().isEmpty()) {
            return "00:0:ERROR";
        }
        
        String firstTwoId = messageId.substring(0, 2);
        
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        
        String combined = firstTwoId + ":" + messageNum + ":" + firstWord + lastWord;
        return combined.toUpperCase();
    }

    public String checkMessageLength(String message) {
        if (message == null) return "Message exceeds 250 characters by 0; please reduce the size.";
        if (message.length() <= MAX_MSG_LENGTH) {
            return "Message ready to send";
        } else {
            int exceededBy = message.length() - MAX_MSG_LENGTH;
            return "Message exceeds 250 characters by " + exceededBy + "; please reduce the size.";
        }
    }

    public String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public String storeMessage(int choice) {
        switch (choice) {
            case 1:
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    public String storeMessageInJSON(String messageID, String hash, String recipient, String text, int msgNum) {
        return "{\n" +
                "  \"messageNum\": " + msgNum + ",\n" +
                "  \"messageID\": \"" + messageID + "\",\n" +
                "  \"messageHash\": \"" + hash + "\",\n" +
                "  \"recipient\": \"" + recipient + "\",\n" +
                "  \"messageText\": \"" + text + "\"\n" +
                "}";
    }
}