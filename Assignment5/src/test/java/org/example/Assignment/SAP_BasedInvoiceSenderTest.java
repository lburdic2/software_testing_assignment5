package org.example.Assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SAP_BasedInvoiceSenderTest {

    //This function tests the sendLowValuedInvoices function of the SAP_BasedInvoiceSender class.
    //It ensures that an exception is thrown and the invoices that caused the exception are returned
    @Test
    void testThrowExceptionWhenBadInvoice() throws FailToSendSAPInvoiceException {
        FilterInvoice filter=mock(FilterInvoice.class); //This is a mock object of the FilterInvoice class
        SAP sap=mock(SAP.class); //This is a mock object of the SAP class

        Invoice invoice1=new Invoice("Sarah", 10); //This is a normal invoice with the value of 10
        Invoice invoice2=new Invoice("Tom", 20); //This is a normal invoice with the value of 20
        Invoice invoice3=new Invoice("Smith", 30); //This is the invoice that will cause the exception. It has a value of 30

        List<Invoice> listInvoices=List.of( //This is a list that contains all the invoices I just created
                invoice3, //I purposefully placed the invoice that will trigger the exception at the first index of the list
                invoice2, //this is so the send function will be called upon it first I can then verify that the send function was called upon the other two invoices
                invoice1  //if it was, this means that the exception did not disrupt the running of the program and was caught succesfully
        );

        when(filter.lowValueInvoices()).thenReturn(listInvoices); //This is a when then statement. WHen the function lowValueInvoices is called on the
                                                                  //filter object, the list listInvoices is returned. It is a stub

        doThrow(new FailToSendSAPInvoiceException("Failed to send invoice")).when(sap).send(invoice3); //This causes the FailToSendSAPInvoiceException exception to be
                                                                                                      //thrown when the sap object has the function send called upon it with the argument invoice3

        SAP_BasedInvoiceSender sender= new SAP_BasedInvoiceSender(filter, sap); //This creates an instance of the SAP_BasedInvoiceSender class with the mocked dependencies

        List<Invoice> returnedList=sender.sendLowValuedInvoices(); //This collects the list returned by the sendLowValuedInvoices function into another list called returnedList

        assertThat(returnedList.size()).isEqualTo(1); //This checks that the returnedList only contains one element
        assertThat(returnedList.getFirst()).isEqualTo(invoice3); //This line checks that the one element that the returnedList contains is indeed invoice3

        verify(sap, times(1)).send(invoice3); //This verifies that the sap object had the function send called only once with invoice 3
        verify(sap, times(1)).send(invoice2); //This verifies that the sap object had the function send called only once with invoice2.
                                                                     //if it passes, then the exception was handled gracefully and did not stop the program
        verify(sap, times(1)).send(invoice1); //This verifies that the sap object had the function send called only once with invoice1
                                                                     //if it passes, then the exception was handled gracefully and did not stop the program



    }
}