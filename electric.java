import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class VotingSystem {
    
    private Map<String, String> votes;
    
    // Constructor to initialize the vote storage
    public VotingSystem() {
        votes = new HashMap<>();
    }
    
    public void castVote(String voterId, String vote) {
        try {
            String hashedVote = hashVote(vote);
            
            if (!votes.containsKey(voterId)) {
                votes.put(voterId, hashedVote);
                System.out.println("Vote cast successfully.");
            } else {
                System.out.println("You have already voted.");
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("An error occurred while hashing the vote: " + e.getMessage());
        }
    }
    
    private String hashVote(String vote) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(vote.getBytes());
        StringBuilder sb = new StringBuilder();
        
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString();
    }
    
    public void displayResults() {
        System.out.println("Voting Results (Hashed):");
        
        for (Map.Entry<String, String> entry : votes.entrySet()) {
            System.out.println("Voter ID: " + entry.getKey() + ", Vote: " + entry.getValue());
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VotingSystem votingSystem = new VotingSystem();
        
        try {
            System.out.println("Welcome to the Electronic Voting System.");
            
            while (true) {
                System.out.println("Enter your Voter ID:");
                String voterId = scanner.nextLine().trim();
                
                if (voterId.isEmpty()) {
                    System.out.println("Voter ID cannot be empty. Please try again.");
                    continue;
                }
                
                System.out.println("Enter your vote:");
                String vote = scanner.nextLine().trim();
                
                if (vote.isEmpty()) {
                    System.out.println("Vote cannot be empty. Please try again.");
                    continue;
                }
                
                votingSystem.castVote(voterId, vote);
                
                System.out.println("Do you want to cast another vote? (yes/no)");
                String choice = scanner.nextLine().trim();
                
                if (choice.equalsIgnoreCase("no")) {
                    break;
                }
            }
            
            votingSystem.displayResults();
        } finally {
            scanner.close();
        }
    }
}
