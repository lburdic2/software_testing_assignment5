package org.example.Assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SAP_BasedInvoiceSenderTest {

    //This method tests the function sendLowValuedInvoices of the class SAP_BasedInvoiceSender with the mocked dependencies
    //FilterInvoice and SAP
    @Test
    void  testWhenLowInvoicesSent() {
        FilterInvoice filter=mock(FilterInvoice.class); //This is a mocked object of the FilterInvoice class
        SAP sap=mock(SAP.class); //This is a mocked SAP class object

        Invoice invoice1=new Invoice("Sarah", 50); //This is an invoice object with the value 50
        Invoice invoice2=new Invoice("Tom", 25); //This is an invoice object with the value 25

        List<Invoice> listInvoices=List.of( //I then put both of the previous invoices into a list
                invoice1,
                invoice2
        );

        when(filter.lowValueInvoices()).thenReturn(listInvoices); //This is a when then statement. When the lowValueInvoices function is called on the
                                                                  //mocked filter object, the listInvoices is returned

        SAP_BasedInvoiceSender sender=new SAP_BasedInvoiceSender(filter, sap); //This creates a SAP_BasedInvoiceSender object with the two mocked dependencies
        sender.sendLowValuedInvoices(); //This calls the sendLowValuedInvoices function on the SAP_BasedInvoiceSender object

        verify(sap, times(1)).send(invoice1); //This verifies that when sendLowValuedInvoices was called on sender, the mocked dependency, sap, had its function send called once
                                                                     //with the argument invoice1
        verify(sap, times(1)).send(invoice2); //This verifies that when sendLowValuedInvoices was called, the mocked dependency, sap, had its send function called once with the argument invoice2
        verify(sap, times(2)).send(any(Invoice.class)); //This verifies that sap's function send was called twice in total with the argument being any object of the class Invoice

    }

    //This function tests the function sendLowValuedInvoices when no invoices are sent from the FilterInvoice object
    @Test
    void  testWhenNoInvoices() {
        FilterInvoice filter=mock(FilterInvoice.class); //This is a mocked object of the FilterInvoice class
        SAP sap=mock(SAP.class); //This is a mocked SAP class object

        List<Invoice> listInvoice=List.of(); //This creates an empty list.

        when(filter.lowValueInvoices()).thenReturn(listInvoice); //This is a when then statement. That means that when lowValueInvoices
                                                                 //is called on the filter object, the list listInvoice will be returned
        SAP_BasedInvoiceSender sender=new SAP_BasedInvoiceSender(filter, sap); //This creates a SAP_BasedInvoiceSender object with the two mocked dependencies
        sender.sendLowValuedInvoices(); //This calls the sendLowValuedInvoices function on the SAP_BasedInvoiceSender object

        verify(sap, times(0)).send(any(Invoice.class)); //This verifies that the sap object never had its send function called upon it
                                                                               //with an argument of any object of the invoice class

    }
}