package org.example.Assignment;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilterInvoiceTest {

    //This is a test of the FilterInvoice function lowValueInvoice with the stubbed
    //database dependency.
    @Test
    void filterInvoiceStubbedTest() {
        QueryInvoicesDAO dao= mock(QueryInvoicesDAO.class); //This mocks the QueryInvoicesDAO class, allowing us to mock the database

        List<Invoice> listInvoices=List.of(               // We are creating a list of invoices here
                new Invoice("Sarah", 10), //This invoice has a value of 10
                new Invoice("Tom", 110), //This invoice has a value of 100
                new Invoice("Smith", 120) //This invoice has a value of 120
        );
        when(dao.all()).thenReturn(listInvoices); //This mocks the dao.all() function so that when it is called, we stub it with the list of invoices we previously created

        FilterInvoice filterInvoice=new FilterInvoice(dao); //We are now creating a Filter Invoice objects with the mocked dao object

        assertThat(filterInvoice.lowValueInvoices().size()).isEqualTo(1); //SInce only one of our invoices in our invoice list was less than 100,
                                                                                  // the function lowValueInvoices should only return a list of size one

    }
}