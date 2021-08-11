package model;

import java.util.Random;

/**
 * Generates empty or random polls and parties for this application.
 * 
 * This is implemented using the Singleton design pattern.  Only a single instance of
 * this class will exist while the application is running.  To get a reference to the 
 * single instance, use the getInstance() method.
 * 
 * @author Nathaly Verwaal
 *
 */
public class Factory {
	public static final int DEFAULT_NUMBER_OF_SEATS = 345;
	public static final int DEFAULT_NUMBER_OF_POLLS = 5;


	private int numOfSeats = DEFAULT_NUMBER_OF_SEATS;
	private String[] partyNames = {"BQ", "CPC", "Green", "LPC", "NDP", "PPC", "Rhinoceros"};
	private int numOfPolls = DEFAULT_NUMBER_OF_POLLS;
	
	private static Factory singleton;
	
	/**
	 * Get the single instance of Factory.
	 * @return the single instance of factory.
	 */
	public static Factory getInstance() {
		if (singleton == null) {
			singleton = new Factory();
		}
		return singleton;
	}
	
	/**
	 * String representation of this Factory object that returns
	 * the state of the factory as a String.  The state includes the number of seats, party names,
	 * and the number of polls in the polltracker that this factory is used for.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Factory state: \n");
		str.append("\tNumber of seats: ");
		str.append(numOfSeats);
		str.append("\n");
		str.append("\tNumber of polls: ");
		str.append(numOfPolls);
		str.append("\n");
		str.append("\tparty names: ");
		for (String name : partyNames) {
			str.append(name);
			str.append(",");
		}
		str.append("\n");
		return str.toString();
	}

	/** 
	 * Create a factory for an election that has specified number of 
	 * seats available in the election polled.
	 * @param numOfSeats number of seats available in the election that this factory creates polls and parties for
	 */
	public Factory(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}
	
	/**
	 * Update the number of seat in the poll tracker for this Factory singleton.
	 * @param numOfSeats the number of seats in the election we're tracking.
	 */
	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}
	
	public void setNumOfPolls(int numOfPolls) {
		this.numOfPolls = numOfPolls;
	}
	
	/** 
	 * Create a factory for an election that has specified number of 
	 * seats available in the election polled.
	 * @param numOfSeats number of seats available in the election that this factory creates polls and parties for
	 */
	private Factory() {
	}
	
	/**
	 * Set the names of parties participating in the election.
	 */
	public void setPartyNames(String[] names) {
		if (names != null)	partyNames = names;
	}
	
	/**
	 * Get the names of the parties for this election.
	 * @return list of names that will be used to generate polls.
	 */
	
	public String[] getPartyNames() {
		return partyNames;
	}
	
	/**
	 * Create a party with specified name that is projected to win a random number of seats (between
	 * 0 and <code>maximumSeats</code>) and a random percentage of the vote (between 0 and <code>
	 * maximumPercent</code>).
	 * <p>
	 * The method ensures that the percentage of the vote is reasonable with respect with the number
	 * of votes generated.  The projected number of seats is generated as a completed random number between
	 * 0 and <code>maximumSeats</code>.  The percentage of seats generate will be a non-negative number
	 * which is at most 5% higher or lower than the percentage of seats that was generated.  (And the method
	 * ensures this number is at least 0 and at most <code>maximumPercent</code>.
	 *  
	 * @param name the name for the party to generate
	 * @param maximumSeats maximum number of seats this party should be projected to win
	 * @param maximumPercent maximum percent of the vote this party should be projected to win
	 * @return Party that was randomly generated within specified parameters.
	 */
	public Party createRandomParty(String name, int maximumSeats, int maximumPercent) {
		Random rand = new Random();
		Party p1 = new Party(name);
		
		int projectedNumberOfSeats = rand.nextInt((int)(maximumSeats*100) + 1)/100;
		
		int percentOfSeatsProjected = projectedNumberOfSeats*100/numOfSeats;
		int maximumPercentOfVotes =  Math.max(0,percentOfSeatsProjected + 5);
		int minimumPercentOfVotes = Math.max(0,percentOfSeatsProjected - 5);
		
		//Generate a random number that falls in the correct range
		int range = Math.round(maximumPercentOfVotes - minimumPercentOfVotes);
		int projectedPercentOfVotes = minimumPercentOfVotes + rand.nextInt(range);
		
		// Set seats and percentage in the party we're generating and return result.
		p1.setProjectedNumberOfSeats(projectedNumberOfSeats);
		p1.setProjectedPercentageOfVotes(Math.min(maximumPercent, projectedPercentOfVotes)/100.0f);
		return p1;
	}
	
	/**
	 * Removes the character at specified index from the string and returns the result.
	 * @param str the string to remove a character from
	 * @param index the index of the character to remove
	 * @return the string <code>str</code> with the character at specified index removed.
	 */
	private String removeChar(String str, int index) {
		String first = "";
		String second = "";
		if (index!= 0 ) {
			first = str.substring(0,index);
		}
		if (index != str.length()-1) {
			second = str.substring(index+1);
		}
		return first + second;
	}
	
	/**
	 * Create a random poll which will have the specified name which will contain all the parties for this
	 * election.  The total projected votes for all parties will add to 100% and the total project seats will
	 * add to number of seats available in this election.  But the amount of seats and percent per party will be 
	 * completely random.
	 * @param name the name for the poll to create
	 * @return poll that randomly divides projected number of seats each party will win and projected percent of vote each
	 * party will win.
	 */
	public Poll createRandomPoll(String name) {
		Poll poll = new Poll(name, partyNames.length);
		
		// Create all indices into the partyNames array, so we can randomly choose a party.
		String partyIndices = "";
		for (int index = 0; index < partyNames.length; index++) {
			partyIndices += index;
		}
		
		Random rand = new Random();
		int percentLeft = 100;
		int seatsLeft = numOfSeats;
		for (int counter = 0; counter < partyNames.length-1; counter++) {
			// randomly choose an index from the list of indices into the partyNames array
			int nextIndex = rand.nextInt(partyIndices.length());
			String index = partyIndices.substring(nextIndex,nextIndex+1);

			// Generate a random party with the randomly chosen name and appropriate random seats and percent
			Party p = createRandomParty(partyNames[Integer.parseInt(index)], seatsLeft, percentLeft);
			poll.addParty(p);
			
			// Setup for next party to generate
			percentLeft -= p.getProjectedPercentageOfVotes() * 100;
			seatsLeft -= p.getProjectedNumberOfSeats();
			
			// this has the index we used in this iteration removed
			partyIndices = removeChar(partyIndices, nextIndex);
		}
		
		// There is one party left now, project that it will get the remaining seats and percentage of vote
		poll.addParty(new Party(partyNames[Integer.parseInt(partyIndices)],seatsLeft,percentLeft/100.0f));
		
		return poll;
	}
	
	/**
	 * Create a list of empty polls.
	 * @return a list of empty poll objects
	 */
	public PollList createEmptyPolls() {
		return createEmptyPolls(numOfPolls);
	}
	
	/**
	 * Create a list of empty polls that contains the specified number of polls
	 * in the list.  (Each of which is empty.)
	 * @param numOfPolls number of empty polls to place in the list.
	 * @return the list of empty polls that was generated.
	 */
	public PollList createEmptyPolls(int numOfPolls) {
		PollList list = new PollList(numOfPolls, numOfSeats);
		for (int counter = 0; counter < numOfPolls; counter++) {
			Poll p = new Poll("Poll" + counter, partyNames.length);
			for (String partyName : partyNames) {
				p.addParty(new Party(partyName));
			}
			list.addPoll(p);
		}
		return list;
	}
	
	/**
	 * Create a poll list with randomly created polls.  The number of polls is determined
	 * by <code>numOfPolls</code>.
	 * @return a list of randomly generated polls.
	 */
	public PollList createRandomPollList() {
		return createRandomPollList(numOfPolls);
	}

	/**
	 * Create a poll list with randomly created polls.
	 * @param numOfPolls the number of polls that will be randomly generated and placed in the list.
	 * @return the PollList with randomly generated polls.
	 */
	public PollList createRandomPollList(int numOfPolls) {
		PollList list = new PollList(numOfPolls,numOfSeats);
		for (int counter = 0; counter < numOfPolls; counter++) {
			list.addPoll(createRandomPoll("Poll" + counter));
		}
		return list;
	}
	
	/**
	 * Optional to provide implementation.  It prompts the user for all information needed
	 * to create the list of polls.  No error checking is required.
	 * @param numOfPolls the number of polls to create
	 * @return PollList object with required number of polls with info provided by end user.
	 */
	public PollList promptForPollList(int numOfPolls) {
		return createRandomPollList(numOfPolls);
	}
}
