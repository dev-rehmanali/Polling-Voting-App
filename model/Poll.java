package model;
import java.util.Arrays;
import java.util.Comparator;

public class Poll {
     private String name = "Unnamed Poll";
     private Party[] parties;
     private int partiesInPoll = 0;

    
    public Poll(String name, int numberOfParties) {
        this.name = name;
        if (numberOfParties >= 1) {
            parties = new Party[numberOfParties];
        
        
        } else {
            parties = new Party[10];
        }
    }

    
    public String getPollName() {
        return name;
    }

    public void setPollName(String pollName){
        this.name = pollName;
    }

    public Party[] getPartiesList(){
        return this.parties;
    }

    public void setPartiesList(Party[] parties){
         this.parties = parties;
    }

    
    public int getNumberOfParties() {
        int idx = 0;
      
        if (parties[idx] == null) {
            partiesInPoll = 0;
        } else {
            partiesInPoll = 0;
            while (idx < parties.length && parties[idx] != null) {
                idx++;
                partiesInPoll++;
            }
        }
        return partiesInPoll;
    }

    
    public void addParty(Party tmpParty) {
        int idx = 0;
        String temp = null;
        for (int i = 0; i < partiesInPoll; i++) {
            String party_name = parties[i].getName().toLowerCase();
            if (party_name.equals(tmpParty.getName().toLowerCase())) {
                temp = party_name;
                idx = i;
            }
        }
        if (tmpParty == null) {
            System.out.println("The party is not defined.");
        }
        else if (parties[0] == null) {
            parties[0] = tmpParty;
            partiesInPoll++;
        }
        else if (temp != null) {
            parties[idx] = tmpParty;
        } else if (partiesInPoll >= parties.length) {
            System.out.println("The poll is full and no further party can be added.");
        }
        else {
            parties[partiesInPoll++] = tmpParty;
        }
    }

    public Party getParty(String name) {
        Party temp = null;
        for (Party p: parties) {
            if (p != null && p.getName().toLowerCase().equals(name.toLowerCase())) {
                temp = p;
                break;
            }
        }
        return temp;
    }

    private Party[] createCopyHelper() {
        Party[] party_copy = new Party[partiesInPoll];
        // Iterate over array of parties
        for (int i = 0; i < party_copy.length; i++) {
            String name_copy = parties[i].getName();
            float seat_copy = parties[i].getProjectedNumberOfSeats();
            float vote_copy = parties[i].getProjectedPercentageOfVotes();
            party_copy[i] = new Party(name_copy, seat_copy, vote_copy);
        }
        return party_copy;
    }

    static class ComparatorForSeats implements Comparator<Party> {
        public int compare(Party x, Party y) {
            return Float.compare(y.getProjectedNumberOfSeats(), x.getProjectedNumberOfSeats());
        }
    }

    public Party[] getPartiesSortedBySeats() {
        Party[] sorted_by_seats = createCopyHelper();
        Arrays.sort(sorted_by_seats, new ComparatorForSeats());
        return sorted_by_seats;
    }

    static class ComparatorForVotes implements Comparator<Party> {
        public int compare(Party x, Party y) {
            return Float.compare(y.getProjectedPercentageOfVotes(), x.getProjectedPercentageOfVotes());
        }
    }

    public Party[] getPartiesSortedByVotes() {
        // Making a copy of the poll (parties list)
        Party[] sorted_by_votes = createCopyHelper();
        Arrays.sort(sorted_by_votes, new ComparatorForVotes());
        return sorted_by_votes;
    }

   
    @Override
    public String toString() {
        String pollStr = name;
        for (Party p: parties) {
            if (p != null) {
                String tmpParty = p.toString();
                pollStr = pollStr + "\n" + tmpParty;
    }
        }
        return pollStr;
    }

    
    public static void main(String[] args) {

        Poll test = new Poll("Unnamed Poll", 5);
        System.out.println(test.getPollName());
        System.out.println(test.getNumberOfParties());

        Party PartyA = new Party("PartyA", 30.0f, 0.3f);
        Party PartyB = new Party("PartyB", 20.0f, 0.2f);
        Party PartyC = new Party("PartyC", 20.0f, 0.2f);
        Party PartyD = new Party("PartyD", 15.0f, 0.15f);
        Party PartyE = new Party("Green", 15.0f, 0.15f);

        test.addParty(PartyA);
        System.out.println(test.getNumberOfParties());
        test.addParty(PartyB);
        System.out.println(test.getNumberOfParties());
        test.addParty(PartyC);
        System.out.println(test.getNumberOfParties());
        test.addParty(PartyD);
        System.out.println(test.getNumberOfParties());
        test.addParty(PartyE);
        System.out.println(test.getNumberOfParties());


        Party testGetParty = test.getParty("PartyC");
        System.out.println(testGetParty);

        System.out.println(test.toString());

        Party[] testSortBySeats = test.getPartiesSortedBySeats();
        String testPrintSeats = "\n";
        for (Party p: testSortBySeats) {
            String tmpParty = p.toString();
            testPrintSeats = testPrintSeats + tmpParty + "\n";
        }
        System.out.println(testPrintSeats);

        Party[] testSortByVotes = test.getPartiesSortedByVotes();
        String testPrintVotes = "\n";
        for (Party p: testSortByVotes) {
            String tmpParty = p.toString();
            testPrintVotes = testPrintVotes + tmpParty + "\n";
        }
        System.out.println(testPrintVotes);
    }
}
