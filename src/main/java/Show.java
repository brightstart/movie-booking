import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Show {

    private Map<String, Boolean> seatMap;
    private static List<Seat> bookedSeats ;
    private double transactionAmount;
    private int showNo;
    private double testTransactionAmount; //TestDouble

    public void setTestTransactionAmount(double testTransactionAmount) {
        this.testTransactionAmount = testTransactionAmount;
    }

    public double getTestTransactionAmount() {
        return testTransactionAmount;
    }

    public Show(int showNo, Map<String, Boolean> seatMap) {
        this.showNo = showNo;
        this.seatMap = seatMap;
        bookedSeats = new LinkedList<>();
    }

    private void bookSeat(String s) throws SeatBookingException{
        seatMap.entrySet().stream()
        .filter(entry -> entry.getKey().equals(s))
        .findFirst()
        .ifPresentOrElse(
            entry -> {
                if (entry.getValue()) {
                    throw new SeatBookingException("Print: " + entry.getKey() + " Not available, Please select different seats");
                } else {
                    createSeatObjectAndAddToList(entry.getKey());
                    seatMap.replace(entry.getKey(), true);
                }
            },
            () -> {
                    throw new SeatBookingException("Invalid seat input.");
            }
        );

    }

    public void seatBookingInvoiceGeneration(String inputString) throws SeatBookingException {
        bookSeats(inputString);
        System.out.println("Print: Successfully Booked - Show " + showNo);
        System.out.println("Subtotal: Rs. " + round(transactionAmount));
        setTestTransactionAmount(transactionAmount); //for testing
        double serviceTax = calculateServiceTax(transactionAmount);
        double sbc = calculateSBC(transactionAmount);
        double kkc = calculateKKC(transactionAmount);
        System.out.println("Service Tax @14%: Rs. " + round(serviceTax));
        System.out.println("Swachh Bharat Cess @0.5%: Rs. " + round(sbc));
        System.out.println("Krishi Kalyan Cess @0.5%: Rs. " + round(kkc));
        System.out.println("Total : Rs. " + round(transactionAmount + serviceTax + sbc + kkc));
        setTransactionAmountBackToZero();
        //cannot have transaction amount local here, because the method will be called by then
    }

    private static double calculateKKC(double amount) {
        return amount * 0.005;
    }

    private static double calculateSBC(double amount) {
        return amount * 0.005;
    }

    private static double calculateServiceTax(double amount) {
        return amount * 0.14 ;
    }

    private void bookSeats(String inputString) throws SeatBookingException {
        String[] seats = inputString.split(",");
        for(String seat : seats){
            bookSeat(seat.trim());
        }
    }

  
    private void setTransactionAmountBackToZero() {
        transactionAmount = 0;
     }

    private void createSeatObjectAndAddToList(String type) throws SeatBookingException {
        Seat seat = switch (type.charAt(0)) {
            case 'A' -> { 
                yield  new SeatA(type); }
            case 'B' -> { 
                yield new SeatB(type); }
            case 'C' -> { 
                yield new SeatC(type); }
            default ->  { throw new SeatBookingException("Invalid Seat Number. Row does not exist"); }
        };

        bookedSeats.add(seat);
        addToSubTotal(seat.getPrice());
        
    }

    private void addToSubTotal(double price) {
        transactionAmount = transactionAmount + price;
    }

    public void viewAvailableSeats() {

        System.out.println("Available Seats:");
        
        Map<Character, List<String>> matrixOfAvailableSeats =  seatMap.entrySet().stream()
                                            .filter(entry -> !entry.getValue())
                                            .map(entry -> entry.getKey())
                                            .collect(Collectors.groupingBy(s -> s.charAt(0)));
        
        matrixOfAvailableSeats.forEach((row, availableSeat ) -> {
            System.out.println(String.join(" ", availableSeat));
        } );
                
    }

    public static double calculateTotal() {
        return bookedSeats.stream()
        .map(seat -> seat.getPrice())
        .collect(Collectors.summingDouble(Double::doubleValue));
    }

    public static void displayTotalSales(){
        double total = calculateTotal();
        System.out.println("Total Sales:");
        System.out.println("Revenue: Rs. " + round(total));
        System.out.println("Service Tax: Rs. " + round(calculateServiceTax(total)));
        System.out.println("Swachh Bharat Cess: Rs. " + round(calculateSBC(total)));
        System.out.println("Krishi Kalyan Cess: Rs. " + round(calculateKKC(total)));
    }


    public static String round(double num) {
        DecimalFormat df = new DecimalFormat("#.00");
        // Check if the number is an integer
        if (num == (int) num) {
            return String.valueOf((int) num); // Convert to integer and then to string
        } else {
            return df.format(num); // Format the number to 2 decimal places
        }
    }
            
    
}
