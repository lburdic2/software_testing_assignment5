package org.example.Assignment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FilterInvoiceTest {

    //This is a test of the FilterInvoice function lowValueInvoice with the original
    //database dependency.
    @Test
    void filterInvoiceTest() {
        FilterInvoice filterInvoice= new FilterInvoice(); //This creates a new instacne of the filter invoice class

        Invoice invoice1 =new Invoice("Sarah", 5); //This creates an invoice with the value of five
        Invoice invoice2=new Invoice("Tom", 110); //This creates an invoice with the value of 110

        filterInvoice.dao.save(invoice1); //This saves the first invoice to our filterInvoice object
        filterInvoice.dao.save(invoice2); //This saves the second invoice to our filterInvoice object

        assertThat(filterInvoice.lowValueInvoices().size()).isEqualTo(1); //Since only one of our invoices is less than 100,
                                                                                    // the returned list should be of size one
    }
}