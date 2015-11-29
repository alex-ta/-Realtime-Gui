package event;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TaxiTripRawEvent {

	/**
	 * TaxiTripRawEvent implementation
	 * @author alex
	 * 
	 * ValueObject that contain every Data in the File,
	 * duplicated values get checked. Data gets Parsed.
	 *
	 * */
	
	private String ID;
	
	private String medallion;		//0
	private String hackLicense;		//1
	private String vendorId;		//2
	private String rateCode;		//3
	private String flag;			//4
	private Date pickUp;			//5
	private Date dropOff;			//6
	private int passengers;			//7
	private long triptime;			//8
	private double tripdistance;	//9
	private double pickLongitude;	//10
	private double pickLatitude;	//11
	private double dropLongitude;	//12
	private double dropLatitude;	//13
	//private String medallion2;	//14
	//private String hackLicense2;	//15
	//private String vendorId2;		//16
	//private String pickUp2;		//17
	private String paymentType;		//18
	private double fareAmount;		//19
	private String surcharge;		//20
	private double taxAmount;		//21
	private double tipAmount;		//22
	private double tollAmount;		//23
	private double totalAmount;		//24
	
	private long detectionTime;
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getMedallion() {
		return medallion;
	}

	public void setMedallion(String medallion) {
		this.medallion = medallion;
	}

	public String getHackLicense() {
		return hackLicense;
	}

	public void setHackLicense(String hackLicense) {
		this.hackLicense = hackLicense;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getPickUp() {
		return pickUp;
	}

	public void setPickUp(Date pickUp) {
		this.pickUp = pickUp;
	}

	public Date getDropOff() {
		return dropOff;
	}

	public void setDropOff(Date dropOff) {
		this.dropOff = dropOff;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public long getTriptime() {
		return triptime;
	}

	public void setTriptime(long triptime) {
		this.triptime = triptime;
	}

	public double getTripdistance() {
		return tripdistance;
	}

	public void setTripdistance(double tripdistance) {
		this.tripdistance = tripdistance;
	}

	public double getPickLongitude() {
		return pickLongitude;
	}

	public void setPickLongitude(double pickLongitude) {
		this.pickLongitude = pickLongitude;
	}

	public double getPickLatitude() {
		return pickLatitude;
	}

	public void setPickLatitude(double pickLatitude) {
		this.pickLatitude = pickLatitude;
	}

	public double getDropLongitude() {
		return dropLongitude;
	}

	public void setDropLongitude(double dropLongitude) {
		this.dropLongitude = dropLongitude;
	}

	public double getDropLatitude() {
		return dropLatitude;
	}

	public void setDropLatitude(double dropLatitude) {
		this.dropLatitude = dropLatitude;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public double getFareAmount() {
		return fareAmount;
	}

	public void setFareAmount(double fareAmount) {
		this.fareAmount = fareAmount;
	}

	public String getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getTipAmount() {
		return tipAmount;
	}

	public void setTipAmount(double tipAmount) {
		this.tipAmount = tipAmount;
	}

	public double getTollAmount() {
		return tollAmount;
	}

	public void setTollAmount(double tollAmount) {
		this.tollAmount = tollAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public long getLongTime(){
		Calendar cal = Calendar.getInstance();
        cal.setTime(dropOff);
        return cal.getTimeInMillis();
	}
    
	public TaxiTripRawEvent(){}
	
	public TaxiTripRawEvent(String...rows) {
		
		/**
		 * Setting the Data 
		 *
		 * */
		
		SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(rows[0].equals(rows[14])){
			this.medallion = rows[0];
		}
		if(rows[1].equals(rows[15])){
			this.hackLicense = rows[1];
		}
		if(rows[2].equals(rows[16])){
			this.vendorId = rows[2];
		}
		this.rateCode = rows[3];
		this.flag = rows[4];
		
    	try {	
    		if(rows[5].equals(rows[17])){
    			this.pickUp = sdfSource.parse(rows[5]);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}	
    	
    	try {	
    		this.dropOff = sdfSource.parse(rows[6]);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}	    	
    	
    	this.passengers = Integer.parseInt(rows[7]);
    	this.triptime = Long.parseLong(rows[8]);
    
    	this.tripdistance = Double.parseDouble(rows[9]);	
    	this.pickLongitude = Double.parseDouble(rows[10]);	
    	this.pickLatitude = Double.parseDouble(rows[11]);	
    	this.dropLongitude = Double.parseDouble(rows[12]);	
    	this.dropLatitude = Double.parseDouble(rows[13]);	
    	

    	this.paymentType = rows[18];	
    	this.fareAmount = Double.parseDouble(rows[19]);		
    	this.surcharge = rows[20];		
    	this.taxAmount = Double.parseDouble(rows[21]);		
    	this.tipAmount = Double.parseDouble(rows[22]);		
    	this.tollAmount = Double.parseDouble(rows[23]);		
    	this.totalAmount = Double.parseDouble(rows[24]);		
    	this.ID = hackLicense + "#" + dropOff.toString();
	
    	this.detectionTime = pickUp.getTime();
    	
	}
    
    @Override
    public String toString() {
    	//return this.DetectionTime+";"+this.LongTime+";"+this.DetectionTime+";"+this.TripTime;
    	return this.ID;
    }

	public long getDetectionTime() {
		return detectionTime;
	}

	public void setDetectionTime(long detectionTime) {
		this.detectionTime = detectionTime;
	}

}
