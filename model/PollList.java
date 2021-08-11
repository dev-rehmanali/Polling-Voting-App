package model;


public class PollList {
	private Poll[] polls;
	private int numOfSeats;
	
	public PollList(int numOfPolls, int numOfSeats) {
		if (numOfPolls < 1) {
			numOfPolls = 5;
			}
		
		if (numOfSeats < 1) {
			numOfSeats = 10;
			}
		
		polls = new Poll[numOfPolls];
		this.numOfSeats = numOfSeats;
		
	}
	
	public int getNumOfSeats() {
		
		return numOfSeats;
	}
	
	public Poll[] getPolls() {
		return polls;
	}

	public void addPoll(Poll aPoll) {
		/*
		 * This method which does not return anything and takes an object of type Poll as an argument. 
		 * This method will add polls to the list for the election, for errors it will leave the list unchanged.
		 * It will return an error if the argument is "null" or if the list is full.
		 * The method will not allow duplicates and will replace the existing entry.  
		 * */
		
		 	// Check if poll is null, leave list unchanged.
		   if (aPoll == null) {
	           System.out.println("Error:argument is null");
	           
	          
	           return;
	       }

		   // Add elements to the list while checking for duplicates.
	       int index = 0;
	       for (; index < polls.length && polls[index] != null; index++) {
	           if (aPoll.getPollName().toLowerCase().equals((polls[index].getPollName().toLowerCase()))) {
	               polls[index] = aPoll;
	               return;
	           }
	       }
	       	// Check if list is full
	       if (index == polls.length) {
	           System.out.println("Error message: the list is full and no further polls can be added.  ");
	           return;
	       }

	       polls[index] = aPoll;
	   }
				
	
	public Poll getAggregatePoll(String[] partyNames) {
		
		/*
		 	This method returns a poll representing the aggregate of all polls in the list.  The method takes as an argument the
		 names of all the parties as an array of Strings that should be included in the aggregate poll.
		 If the resulting poll would divide more seats between the parties than the total number of seats 
		 available in the election, the number of seats for each party will be adjusted.
		 The same will be done if the percentage is greater than 100.
		 * */
		
		
		// Create Aggregate poll
		
		
		Poll aggregatePoll = new Poll("Aggregate", partyNames.length);

		float totalVotes = 0, totalSeats = 0;
		Party[] temp = new Party[partyNames.length];

		// compute aggrepagePartyData, total votes and total Seats
		for (int index = 0; index < partyNames.length; index++) {
			temp[index] = getAveragePartyData(partyNames[index]);
			totalSeats += temp[index].getProjectedNumberOfSeats();
			totalVotes += temp[index].getProjectedPercentageOfVotes();
		}

		// if total seats exceept numOfSeats adjust seats of each party
		if (totalSeats > numOfSeats) {
			float seatDiff = totalSeats - numOfSeats;
			for (int index = 0; index < partyNames.length; index++) {
				float prev = temp[index].getProjectedNumberOfSeats();
				temp[index].setProjectedNumberOfSeats(prev - (seatDiff * prev / totalSeats));
			}
		}

		// if total votes percetage is greater than 100% adjust votes of each party
		if (totalVotes > 1.0) {
			float voteDiff = totalVotes - 1;
			for (int index = 0; index < partyNames.length; index++) {
				float prev = temp[index].getProjectedPercentageOfVotes();
				temp[index].setProjectedPercentageOfVotes(prev - voteDiff * prev);
			}
		}

		// add party to aggregatePoll
		for (int index = 0; index < temp.length; index++) {
			aggregatePoll.addParty(temp[index]);
		}
		return aggregatePoll;
	}
	
	public Party getAveragePartyData(String partyName) {
	
		
		/* 
			This method will get the Expected number of seats.The expected number of seats should be the average expected number of seats
		 this party is expected to win over all polls in the list. Therefore we need calculate the sums of the number of Seats
		 and the sum of the percentage of votes and divide by the number of occurrences  */
		
		float sumNumOfSeats = 0;
		float sumPercentageOfVotes = 0;
		
		
		// Initialize the counter
		int occurrenceCounter = 0;
		
		//For loop to iterate through list and get the projected number of seats and number of occurrences
		for(int index = 0; index < polls.length; index ++) {
			Party party = polls[index].getParty(partyName);
			
			if (party != null) {
				// Add the projected number of seats to sum. 
				sumNumOfSeats += party.getProjectedNumberOfSeats();
				
				sumPercentageOfVotes += party.getProjectedPercentageOfVotes();
				occurrenceCounter ++;
				}
			}
		// Cannot divide by zero, so any party with no occurrence will now be this new party.
		if(occurrenceCounter == 0) {
			Party independentParty = new Party("IndependentCandidate",0,0);
			return independentParty;
			
		}
		// Calculate the averages
		float expectedNumOfSeats = sumNumOfSeats / occurrenceCounter;
		float expectedPercentageOfVotes = sumPercentageOfVotes / occurrenceCounter;
		
		//
		Party averageOfParty = new Party( partyName, expectedNumOfSeats ,expectedPercentageOfVotes );
		
		
		
		return averageOfParty;
	}
	
	public Poll adjustPollToMaximums(Poll aPoll) {
		return aPoll;
	}
	
	@Override
	public String toString() {
		String numberOfSeats = "Number of seats: " +this.numOfSeats;
		return numberOfSeats;
	}
}
