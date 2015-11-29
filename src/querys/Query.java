package querys;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;

import chartsupport.DataListener;

public class Query {

	/**
	 * Query implementation
	 * @author alex
	 * 
	 * Query which contains all Esper Queries.
	 * */
	
	private EPServiceProvider epService;
	
	public Query(EPServiceProvider epService){
		this.epService = epService;
	}
	
	
	public void addQuery(String query, DataListener chart){
		/**Add a custom Query*/
		EPStatement queryX = epService.getEPAdministrator().createEPL(query);
		queryX.addListener(chart);
	}
	
	// Signal ob mehr Personen im Süden oder Norden das Taxi nehmen
	public void addQueryQ1(DataListener chart) {
		/**Add Query Q1*/	
		// 2 Streams werden erzeugt, einer für Norden, einer für Süden.
		// die 2 Streams werden als ein Stream Signal zusammengefasst.
		// Zum Schluss wird in einem letzten Stream die Differenz errechnet, sowie
		// der dazu passende ausgegeben.
		EPStatement stmt01 = epService.getEPAdministrator().createEPL("" +
				" Insert Into Norden " + 
				" Select count(*) AS Anzahl, hackLicense " +
				" From TaxiTripRawEvent.win:time(15min) " +				
				" Where querys.Functions.mapCoordinates(dropLatitude) = 'NORTH' "
				+ "");
		
		EPStatement stmt02 = epService.getEPAdministrator().createEPL("" +
				" Insert Into Sueden " + 
				" Select count(*) AS Anzahl, hackLicense  " +
				" From TaxiTripRawEvent.win:time(15min) " +				
				" Where querys.Functions.mapCoordinates(dropLatitude) = 'SOUTH' "
				+ "");
		
		EPStatement stmt03 = epService.getEPAdministrator().createEPL("" +
				" Insert Into Signal " +
				" Select Norden.Anzahl AS AnzahlNorden, "
				+ " Sueden.Anzahl AS AnzahlSueden" +
				" From Norden.win:length(1) as Norden, Sueden.win:length(1) as Sueden"
				+ "");
		
		EPStatement stmt04 = epService.getEPAdministrator().createEPL("" +
				"select querys.Functions.changeStable( AnzahlSueden , AnzahlNorden ) as Advice from Signal");
		
		stmt04.addListener(chart);
	}
		
	// Durchschnittsgeschwindigkeit pro Bereich
	public void addQueryQ2(DataListener chart) {
		/**Add Query Q2*/
		// Jeder Bereich von NewYork wird in seine Unterbereiche Unterteilt.
		// Die verschiedenen Ströme werden am Ende zu einem Ergebnisstrom zusammengefasst.
		
		EPStatement stmt01 = epService.getEPAdministrator().createEPL("insert into Manhatten"
				+ " select detectionTime, pickLatitude, pickLongitude, avg(tripdistance/(triptime/3600)) As averageSpeed" +
				" from TaxiTripRawEvent.win:time(1 min)"+
				" where pickLatitude between 40.703886 and 40.877000 "
				+ "and pickLongitude between -73.935427 and -74.018329 "
				+ "and triptime > 0.0"
				);
		
		EPStatement stmt02 = epService.getEPAdministrator().createEPL("Insert Into Bronx"
				+ " select detectionTime, pickLatitude, pickLongitude, avg(tripdistance/(triptime/3600)) As averageSpeed" +
				" from TaxiTripRawEvent.win:time(1 min)"+
				" where pickLatitude between 40.797219 and 40.898504 "
				+ "and pickLongitude between -73.781365 and -73.935427 "
				+ "and triptime > 0.0"
			+	"");
		
		EPStatement stmt03 = epService.getEPAdministrator().createEPL("Insert Into Queens"
				+ " select detectionTime, pickLatitude, pickLongitude, avg(tripdistance/(triptime/3600)) As averageSpeed" +
				" from TaxiTripRawEvent.win:time(1 min)"+
				" where pickLatitude between 40.545096 and 40.797219 "
				+ "and pickLongitude between -73.701601 and -73.935427 "
				+ "and triptime > 0.0"
			+	"");
	
		EPStatement stmt04 = epService.getEPAdministrator().createEPL("Insert Into Brooklyn"
				+ " select detectionTime, pickLatitude, pickLongitude, avg(tripdistance/(triptime/3600)) As averageSpeed" +
				" from TaxiTripRawEvent.win:time(1 min)"+
				" where pickLatitude between 40.620147 and 40.703886 "
				+ "and pickLongitude between -73.935427 and -74.041825"
				+ "and triptime > 0.0"
			+	"");
		
		
		EPStatement stmt05 = epService.getEPAdministrator().createEPL("Insert Into StatenIsland"
				+ " select detectionTime, pickLatitude, pickLongitude, avg(tripdistance/(triptime/3600)) As averageSpeed" +
				" from TaxiTripRawEvent.win:time(1 min)"+
				" where pickLatitude between 40.497197 and 40.643240 "
				+ "and pickLongitude between -74.041825 and -74.252572"
				+ "and triptime > 0.0"
			+	"");
		
		EPStatement stmt06 = epService.getEPAdministrator().createEPL(""+
				"select Bronx.averageSpeed as BronxAveragespeed, Manhatten.averageSpeed as ManhattenAveragespeed, Queens.averageSpeed as QueensAveragespeed,"+
				" Brooklyn.averageSpeed as BrooklynAveragespeed, StatenIsland.averageSpeed as StatenIslandAveragespeed "+
				"from  Bronx.win:length(1) as Bronx, Manhatten.win:length(1) as Manhatten, Queens.win:length(1) as Queens, Brooklyn.win:length(1) as Brooklyn, StatenIsland.win:length(1) as StatenIsland");
		
		
		stmt06.addListener(chart);
	}
		

		


	// Vergleich der Personen die nach Norden / Süden fahren
	public void addQueryQ3(DataListener chart) {
			/**Add Query Q3*/
			// Jede Personenstrom z.b. Norden-Süden bekommt einen Stream.
			// Am Ende werden alle Streams zusammengefasst und ausgegeben
			EPStatement stmt01 = epService.getEPAdministrator().createEPL("Insert Into NORTHSOUTH"
					+ " select passengers, pickLatitude, dropLatitude" +
					" from TaxiTripRawEvent"+
					" where pickLatitude > 40.753690 and dropLatitude < 40.753690 "
					+ "and passengers > 0"
				+	"");
		
			EPStatement stmt02 = epService.getEPAdministrator().createEPL("Insert Into SOUTHNORTH"
					+ " select passengers, pickLatitude, dropLatitude" +
					" from TaxiTripRawEvent"+
					" where pickLatitude < 40.753690 and dropLatitude > 40.753690 "
					+ "and passengers > 0"
				+	"");
			
			EPStatement stmt03 = epService.getEPAdministrator().createEPL("Insert Into EASTWEST"
					+ " select passengers, pickLongitude, dropLongitude" +
					" from TaxiTripRawEvent"+
					" where pickLongitude < -73.95 and dropLongitude > -73.95 "
					+ "and passengers > 0"
				+	"");
			
			EPStatement stmt04 = epService.getEPAdministrator().createEPL("Insert Into WESTEAST"
					+ " select passengers, pickLongitude, dropLongitude" +
					" from TaxiTripRawEvent"+
					" where pickLongitude > -73.95 and dropLongitude < -73.95 "
					+ "and passengers > 0"
				+	"");
		
			String min = "2 min";
			
			EPStatement stmt05 = epService.getEPAdministrator().createEPL(""+
					"select sum(EW.passengers) as East_West, sum(WE.passengers) as West_East, sum(NS.passengers) as North_South, sum(SN.passengers) as South_North "+
					"from NORTHSOUTH.win:time("+min+") as NS, SOUTHNORTH.win:time("+min+") as SN, EASTWEST.win:time("+min+") as EW, WESTEAST.win:time("+min+") as WE");
			
			
			stmt05.addListener(chart);
		}
		
		// Trend der Durchschnittsgeschwindigkeit
		public void addQueryQ5(DataListener chart) {
			/**Add Query Q5*/
			// Die aktuelle Geschwindigkeit wird im ersten Stream errechnet.
			// Im 2ten Datenstrom wird die Funktion Trend aufgerufen, die den
			// aktullen Trend als Text über Zeit zurück gibt.
			
			EPStatement stmt01 = epService.getEPAdministrator().createEPL("insert into Speed"+
						 " select detectionTime, avg(tripdistance/(triptime/3600)) As averageSpeed" +
						 " from TaxiTripRawEvent.win:time_batch(1 min).std:lastevent()"+
						 " where triptime > 0.0 and tripdistance > 0.0"+
						"");
			
			EPStatement stmt02 = epService.getEPAdministrator().createEPL(""+
					 " select detectionTime, averageSpeed, querys.Functions.trend( speedTrend.averageSpeed) as Trend from Speed.win:length(1) as speedTrend"
								);

			stmt02.addListener(chart);
		}
		
		// Aktuellerumsatz
		public void addQueryQ10(DataListener chart){
			/**Add Query Q10*/
			// Der Datenstrom gibt den aktuellen Umsatz pro Unternehmen zurück.
			EPStatement  stmt01 = epService.getEPAdministrator().createEPL(
			"select vendorId, sum(totalAmount) as totalAmount from TaxiTripRawEvent.std:groupwin(vendorId).win:time_batch(1 min) group by vendorId");
			
			stmt01.addListener(chart);
		}
		
		// Wie viel Koncurrenten sind unterwegs
		public void addQueryQ11(DataListener chart){
			/**Add Query Q11*/
			// Es werden 2 Datenströme erzeugt, die Ströme Noden und Süden aus Q1 weiterverwenden.
			// Die Datenströme zählen die aktuelle Anzahl der Taxis die im jeweiligen Bereich unterwegs sind.
			// Der letzte Strom gibt die aktuelle Anzahl aus.
			
			EPStatement stmt01 = epService.getEPAdministrator().createEPL("Insert into COUNTN " +
					"select count(North.hackLicense) as CN from Norden.win:time(1 min) as North"
					+ "");
			
			EPStatement stmt02 = epService.getEPAdministrator().createEPL("Insert into COUNTS " +
					"select count(South.hackLicense) as CS from Sueden.win:time(1 min) as South"
					+ "");
			
			EPStatement  stmt03 = epService.getEPAdministrator().createEPL(
			"select s.CS as Competition_South, n.CN as Competition_North from COUNTN.win:length(1) as n, COUNTS.win:length(1) as s");
			
			stmt03.addListener(chart);
		}

}
