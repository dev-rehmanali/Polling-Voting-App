package application;

import model.*;
import java.util.Scanner;

public class TextApplication {
	public static final int MAX_NUMBER_OF_STARS = 25;
	private PollList polls;
	
	
	public TextApplication() {
		// Default constructor
	}
	
	public TextApplication(PollList polls) {
		/* This constructor takes a PollList as an argument and uses it 
		 * to initialize the polls instance variable.
		 * 
		*/
		
		this.polls=polls;		
		
		
	}
	
	public void displayPollsBySeat(String[] partyNames) {
		/*
		 * This method takes an array of poll that should be included  in the aggregate poll to display
		 * as an argument. This is done by passing each Poll in the polls
		 * instance variable as an argument to the previous method (displayPollDataBySeat).
		 * when it reaches the size of the number of polls it will print the Aggregate Poll.
		 * */
		
		// For loop to print party data, when loop completes print Aggregate poll data by calling .getAggregateCode method.
		
		Poll[] allPolls = polls.getPolls();
		// This loop prints the poll data at the index as a string and then goes to the next line
		for (int index = 0; index < partyNames.length ; index++) {
				System.out.print(allPolls[index].getPollName() + "/n" + allPolls[index].getParty(partyNames[index]).textVisualizationBySeats(MAX_NUMBER_OF_STARS,index, index));
			
			// If index reaches the limit, print the aggregate Poll
			if(index >= partyNames.length) {
				
				System.out.print(" " + polls.getAggregatePoll(partyNames));
			
			}
			
			
			
			
		}
		
	}
	
	public PollList getPolls() {
		
		
		return polls;
	}

	public void displayPollDataBySeat(Poll newPoll) {
		/*
		 * This method takes a Poll as an argument. It will visualize the data in the poll. 
		 * This will display the data by invoking the textVisulaizationBySeat method 
		 * and displaying the string returned. The list of parties will be sorted by calling
		 *  getPartiesSortedBySeat .
		 * */
		
		// For loop to get party data at that poll and print out as string

		
		   int seats = 0;
                   for(Party x:newPoll.getPartiesSortedBySeats()) {
                             seats += x.getProjectedNumberOfSeats();
                   }
                   double displaySeats=seats/MAX_NUMBER_OF_STARS;
                   for(Party x:newPoll.getPartiesSortedBySeats()) {
                              System.out.println(x.textVisualizationBySeats(MAX_NUMBER_OF_STARS,(int)Math.ceil(MAX_NUMBER_OF_STARS/2),displaySeats));

        }


    }

	
	public void run() {
		Scanner scanner = new Scanner(System.in);
		// Get the number of seats in the election.
		
		
		System.out.print("Welcome to the poll tracker : \n" );
		System.out.print("How many seats are available in the election? : ");
		int numOfSeats = scanner.nextInt();
			
		// Get the names of the parties in the election
			
		System.out.print("Which parties are in the election (provide names seperated by a comma): ");
			
		// Split party names by comma
		String namesString = scanner.next();
		String partyNames[] = namesString.split(",");
			
		// Get the number of Polls to track in the election
		System.out.print("Enter the number of Polls are in the election :");
		int numOfPolls = scanner.nextInt();
		
		// Prompt to get the user to enter the data for the polls or to generate a random pollList
		System.out.print("Do you want to enter the data for the polls as opposed to generating them by random? Enter 1 for yes, 2 for no: ");
		int decider = scanner.nextInt();
		
		if (decider == (1) ) {
		
			// This loop should take user inputs to give the party their number of seats and their respective percentage of votes of that party in that poll.
			
			PollList pollList = new PollList(numOfPolls,numOfSeats);
			
			for (int counter = 1; counter < numOfPolls + 1; counter++) {
				System.out.print("This is the data for Poll number " + counter + "\n");
				Poll aPoll = new Poll(""+counter, partyNames.length);
				for (int index = 0;  index < partyNames.length; index++ ) {
					
					
					System.out.print(partyNames[index] + ": enter the projected number of seats for this party :");
					float projectedNumberOfSeatsForParty = scanner.nextFloat();
					System.out.print(partyNames[index] + ": enter the projected percentage of votes for this party :");
					float percentageOfVotesForParty = scanner.nextFloat();
					Party party = new Party(partyNames[index], projectedNumberOfSeatsForParty, percentageOfVotesForParty );
					aPoll.addParty(party);
					
				}
				pollList.addPoll(aPoll);
			}
			//
			// Ask the user which type of poll they would like to display.
			System.out.print("Options: all (show result of all polls), aggregate (show aggregate result), quit (end application)");
			String decision = scanner.next();
			TextApplication textApp = new TextApplication();
			Poll aggregatePoll = pollList.getAggregatePoll(partyNames);
			if (decision.equals("all")) {
				
				
				Poll[] poll = pollList.getPolls();
				
				int index =0;
				for( index = 0; index <= numOfPolls; index++) {
					Poll aPoll = poll[index];
					textApp.displayPollDataBySeat(aPoll);
					}	
				
				if(index >=numOfPolls) {
					System.out.print("Aggregate Poll:\n");
					textApp.displayPollDataBySeat(aggregatePoll);
					System.out.print("\n");
				}
			
			}
			
			else if(decision.equals("aggregate")) {
				System.out.print("Aggregate Poll:\n");
				textApp.displayPollDataBySeat(aggregatePoll);
			}
			
			else {

				System.out.print("Good bye!");
				System.exit(0);
			}
		}
		
		// If user does not decide to input the data for the polls a random poll list will be generated.
		else if(decider == 2) {
			
			System.out.print("Options: all (show result of all polls), aggregate (show aggregate result), quit (end application)");
			String decision = scanner.next();
		
			// Generate a PollList using the method from the factory class.
		
			Factory f = new Factory(numOfSeats);
			
			// create a random pollList with the given party names
			f.setPartyNames(partyNames);

			PollList pollList = f.createRandomPollList(numOfPolls); 

			TextApplication textApp = new TextApplication();
			Poll aggregatePoll = pollList.getAggregatePoll(partyNames);
			// Get the polls in the PollList and display the data by seat
			if (decision.equals("all")) {
				Poll[] poll = pollList.getPolls();
				int index = 0;
			
			// This counter variable will be used to get the name of the Poll
				int counter = 1;
			
				// Loop through PollList to get the poll and display it
				for(; index < numOfPolls; index++) {
					Poll aPoll = poll[index];
					System.out.print("Poll Number " + counter + "\n");
					textApp.displayPollDataBySeat(aPoll);
					counter++;
					}
				if(index >=numOfPolls) {
					System.out.print("Aggregate Poll:\n");
					textApp.displayPollDataBySeat(aggregatePoll);
					}
				}
			else if(decision.equals("aggregate")) {

				System.out.print("Aggregate Poll:\n");
				textApp.displayPollDataBySeat(aggregatePoll);
				}
			else {
				System.out.print("Good bye!");
				System.exit(0);
				
			}
		}
		scanner.close();
	}
	

	public static void main(String[] args) {
		TextApplication app = new TextApplication(null);
		app.run();
		}
}