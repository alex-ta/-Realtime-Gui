package querys;



public class Functions {

	/**
	 * Functions implementation
	 * @author alex
	 * 
	 * Functions used by Esper
	 * */


	public static String mapCoordinates(Double coordinate) {
		/**Mapps Coordinates to North or South*/
		if(coordinate > 40.753690) {
			return "NORTH";
		}		
		return "SOUTH";
	}		
	
	public static String changeStable(Long countSouth, Long countNorth){
		/**Returns the current Advice*/
		if(countNorth != null && countSouth != null){
			long difference = countNorth - countSouth;
			if(difference > 250){
				return "wait North";
			} else if(difference < -250) {
				return "wait South";
			} 
		}
		return "stay where you are";	
	}
	
	
	public static double lastValue = 0;
	
	public static String trend (double averageSpeed){
		/**returns the current trend*/
		String res = "";	
		if (averageSpeed > lastValue){
			res = "UP";
		}else if(averageSpeed < lastValue){
			res ="DOWN";
		}else{
			res = "EQUAL";
		}
		
		lastValue = averageSpeed;
		return res;
	}
}
