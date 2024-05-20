import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;

public class TestSeatBooking {

    Map<String, Boolean> seatMapAudi1 ;

    Show show ;

    @Before
    public void before(){
        seatMapAudi1 = new HashMap<>();
        for(int i = Constants.A_MIN_AUDI1; i<=Constants.A_MAX_AUDI1; i++)
            seatMapAudi1.put("A" + i, false);
        for(int i = Constants.B_MIN_AUDI1; i<=Constants.B_MAX_AUDI1; i++)
            seatMapAudi1.put("B" + i, false);
        for(int i = Constants.C_MIN_AUDI1; i<=Constants.C_MAX_AUDI1; i++)
            seatMapAudi1.put("C" + i, false);
        show = new Show(1, seatMapAudi1);
        
    }

    /**
     * 
     */
    @Test(expected = SeatBookingException.class)
    public void testBadInputSmallLetters() {
        show.seatBookingInvoiceGeneration("b1"); //Same will happen if we try to book any unlisted seat
    }

    @Test(expected = SeatBookingException.class)
    public void testBadInputAddSeatsWithoutComma() {
        show.seatBookingInvoiceGeneration("B1B2");
    }

    @Test
    public void testBookSeatsWithLotOfWhitespaces() {
        show.seatBookingInvoiceGeneration("        B1    ,          B4");
    }

    @Test
    public void testOneSeatBookedTotalAndSubTotalShouldMatch(){
        show.seatBookingInvoiceGeneration("B1");
        assertEquals(show.getTestTransactionAmount(), Show.calculateTotal(), 0);
    }

}
