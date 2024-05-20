import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/*
For detailed instructions, please refer to the README file.
It contains all the necessary guidelines and information for submitting and working with this project.
 */



public class Start {

    public static Map<Integer, Show> init(){

        /*TODO : This is error prone - can have typos. better to find alternatives*/

        Map<String, Boolean> seatMapAudi1 = new HashMap<>();
        for(int i = Constants.A_MIN_AUDI1; i<=Constants.A_MAX_AUDI1; i++)
            seatMapAudi1.put("A" + i, false);
        for(int i = Constants.B_MIN_AUDI1; i<=Constants.B_MAX_AUDI1; i++)
            seatMapAudi1.put("B" + i, false);
        for(int i = Constants.C_MIN_AUDI1; i<=Constants.C_MAX_AUDI1; i++)
            seatMapAudi1.put("C" + i, false);

        Map<String, Boolean> seatMapAudi2 = new HashMap<>();
        for(int i = Constants.A_MIN_AUDI2; i<=Constants.A_MAX_AUDI2; i++)
            seatMapAudi2.put("A" + i, false);
        for(int i = Constants.B_MIN_AUDI2; i<=Constants.B_MAX_AUDI2; i++)
            seatMapAudi2.put("B" + i, false);
        for(int i = Constants.C_MIN_AUDI2; i<=Constants.C_MAX_AUDI2; i++)
            seatMapAudi2.put("C" + i, false);
        
        Map<String, Boolean> seatMapAudi3 = new HashMap<>();
        for(int i = Constants.A_MIN_AUDI3; i<=Constants.A_MAX_AUDI3; i++)
            seatMapAudi3.put("A" + i, false);
        for(int i = Constants.B_MIN_AUDI3; i<=Constants.B_MAX_AUDI3; i++)
            seatMapAudi3.put("B" + i, false);
        for(int i = Constants.C_MIN_AUDI3; i<=Constants.C_MAX_AUDI3; i++)
            seatMapAudi3.put("C" + i, false);

        Show show1 = new Show(1, seatMapAudi1);
        Show show2 = new Show(2, seatMapAudi2);
        Show show3 = new Show(3, seatMapAudi3);

        Map<Integer, Show> runningShows = new HashMap<>();
        runningShows.put(1, show1);
        runningShows.put(2, show2);
        runningShows.put(3, show3);

        return runningShows;
    }


    //you may remove the static keyword if required, and please DO NOT CREATE a new object for scanner class
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Map<Integer, Show> runningShows = Start.init();

        Integer showNo;

        String choice = "Yes";

        Show show ;

        do {
                System.out.print("Enter Show no: ");
                //Read user input
                showNo = scanner.nextInt();

                for(Map.Entry<Integer, Show> entry : runningShows.entrySet()){
                    if(entry.getKey() == showNo)
                    {
                        show = entry.getValue();
                        show.viewAvailableSeats();
                        //read from reddit, this will make it read the '\n' at the head of the input stream
                        scanner.nextLine(); 
                        System.out.println("Input:");
                        System.out.print("Enter seats: ");
                        String inputString = scanner.nextLine();
                        try{
                            show.seatBookingInvoiceGeneration(inputString);
                        }
                        catch (SeatBookingException e){
                            System.out.println(e.getMessage());
                        }
                        System.out.print("Would you like to continue (Yes/No): ");
                        choice = scanner.next();
                    }
                }

            } while(!choice.equalsIgnoreCase("No"));

            Show.displayTotalSales();                        

    }
}

