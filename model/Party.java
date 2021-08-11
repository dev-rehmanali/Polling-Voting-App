package model;

/**
 * 
 * @author Insha Maredia
 *
 */


public class Party {
	private String name;
	private float projectedNumberOfSeats;
	private float projectedPercentageOfVotes;
	
	public Party(String partyName) {
		name = partyName;
	}
	
	public Party(String partyName, float projectedNumberOfSeats, float projectedPercentageOfVotes) {
		name = partyName;
		
		// projectedNumberOfSeats - non negative value
		if (projectedNumberOfSeats > 0) {
			this.projectedNumberOfSeats = projectedNumberOfSeats;}
		 else {
			this.projectedNumberOfSeats = 0;}  //in case of an error do not end the program
		
		// projectedPercentageOfVotes- value between 0 and 1
		if (projectedPercentageOfVotes > 0 && projectedPercentageOfVotes <= 1) {
			this.projectedPercentageOfVotes = projectedPercentageOfVotes;}
		else {
			this.projectedPercentageOfVotes = 0;  //in case of an error do not end the program
		}
		
	}
	
	// instance variables and their getter and setter methods
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setProjectedPercentageOfVotes(float projectedPercentageOfVotes) {
		if ( projectedPercentageOfVotes >= 0 && projectedPercentageOfVotes <= 1 ) {
			this.projectedPercentageOfVotes = projectedPercentageOfVotes;}
			
	}
	
	public float getProjectedPercentageOfVotes() {
		return projectedPercentageOfVotes;
	}
	
	
	public void setProjectedNumberOfSeats(float projectedNumberOfSeats) {
		if (projectedNumberOfSeats >= 0) {
			this.projectedNumberOfSeats = projectedNumberOfSeats;}
		
	}
	
	public float getProjectedNumberOfSeats() {
		return projectedNumberOfSeats;
	}

	@Override
	public String toString() {
		return getName() + " (" + (int)(getProjectedPercentageOfVotes() * 100) + "% of votes, " + getProjectedNumberOfSeats() + " seats)" ;  //<name> (<projected % of votes>% , <projected seats>seats) format
	}
	
	public double projectedPercentOfSeats(int totalNumberOfSeatsAvailable) {       //taking int as an argument
		if (totalNumberOfSeatsAvailable <= 0) {       //available seats must be positive
			return 0.0; }    // value should be between 0 and 1
		
		return (double)(projectedNumberOfSeats / totalNumberOfSeatsAvailable);  //returning double
	}
	
	
	
	
	
	public String textVisualizationBySeats(int maxStars, int starsNeededForMajority, double numOfSeatsPerStar) {
		String stars = "";
		int totalStars = 0;
		for (int x = 0; x < maxStars; x ++) {
			if (totalStars < Math.floor(projectedNumberOfSeats / numOfSeatsPerStar)) {
				stars += "*"; 
			}
			else {
				stars += " "; 
			}
			if (x == starsNeededForMajority - 1) {
				stars += "|";
			}
			totalStars ++;
		}
		stars += " ";
		return stars + toString();    //returning a string that displays data in a visual form about number of seats needed for majority
	}
	
	
	
	public String textVisualizationByVotes(int maxStars, int starsNeededForMajority, double percentOfVotesPerStar) {
		String stars = "";
		int totalStars = 0;
		for (int x = 0; x < maxStars; x ++) {
			if (totalStars < Math.floor(maxStars * projectedPercentageOfVotes)) {
				stars += "*";
			}else {
				stars += " ";
			}
			if (x == starsNeededForMajority - 1) {
				stars += "|";
			}
			totalStars ++;
		}
		stars += " ";
		return stars + toString();  //returning a string that displays data in a visual form about votes needed for majority
	}
}