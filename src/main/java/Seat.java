
public class Seat {

    protected String seatNumber;
    protected double price;
    public double getPrice() {
        return price;
    }
    public Seat(String seatNumber, double price) {
        this.seatNumber = seatNumber;
        this.price = price;
    }
    public char getRow(String seatNumber){
        return seatNumber.charAt(0);
    }

    public int getNumber(String seatNumber){
        int number = 999 ;
        try{
            number = Integer.parseInt(seatNumber.substring(1, seatNumber.length()-1));
        }
        catch(ArithmeticException | NumberFormatException e){

        }
        return number;
    }

    public void setSeatNumber(String s){
        seatNumber = s;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((seatNumber == null) ? 0 : seatNumber.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Seat other = (Seat) obj;
        if (seatNumber == null) {
            if (other.seatNumber != null)
                return false;
        } else if (!seatNumber.equals(other.seatNumber))
            return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        return true;
    }

    
}
