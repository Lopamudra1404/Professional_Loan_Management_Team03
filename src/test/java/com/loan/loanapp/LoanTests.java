package com.loan.loanapp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.loan.loanapp.dao.CustomerRepository;
import com.loan.loanapp.dao.LoansRepository;
import com.loan.loanapp.dto.LoanDto;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanType;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.LoansException;
import com.loan.loanapp.service.LoansServiceImpl;

@SpringBootTest

public class LoanTests

{

//	@RunWith(MockitoJUnitRunner.class)
	class LoansServiceImplTest {

	    @Mock
	    private LoansRepository loansRepo;
	    @Mock
	    private CustomerRepository customerRepo;
	    @InjectMocks
	    private LoansServiceImpl loansService;
	    private Customer testCustomer;
	    private Loan testLoan;
	    
	    @Before(value = "")
	    public void setUp() {

	        testCustomer = new Customer();

	        testCustomer.setCustomerId(1);

	        testCustomer.setLoans(new ArrayList<>());

	        testLoan = new Loan();

	        testLoan.setLoanId(1);

	        testLoan.setCustomer(testCustomer);

	        // Mocking repository methods

	        when(customerRepo.findById(1)).thenReturn(Optional.of(testCustomer));

	        when(loansRepo.findById(1)).thenReturn(Optional.of(testLoan));

	    }

	    @SuppressWarnings("rawtypes")
		@Test

	    void testGetAllLoans() {

	        Collection<Loan> allLoans = loansService.getAllLoans();

	        assertEquals(1, allLoans.size());

	        assertEquals(testLoan, ((ArrayList) allLoans).get(0));

	    }

	    @Test

	    void testAddLoanByCustomerId() throws CustomerException {

	        LoanDto loanDto = new LoanDto(null, null, null);

	        loanDto.setLoanAmount(5000.0);

	        loanDto.setLoanTenture(12);

	        loanDto.setLoanType(LoanType.ARCHITECT_Loan);

	        Loan addedLoan = loansService.addLoanByCustomerId(loanDto, 1);

	        assertEquals(loanDto.getLoanAmount(), addedLoan.getLoanAmount());

	        assertEquals(loanDto.getLoanTenture(), addedLoan.getLoanTenture());

	        assertEquals(loanDto.getLoanType(), addedLoan.getLoanType());

	    }

	    @Test

	    void testAddLoanByCustomerIdCustomerNotFound(LoanDto loanDto, Integer customerId) throws CustomerException {

			LoanDto loanDto1 = new LoanDto(null, null, null);

	        loanDto1.setLoanAmount(5000.0);

	        loanDto1.setLoanTenture(12);

	        loanDto1.setLoanType(LoanType.ARCHITECT_Loan);

	        loansService.addLoanByCustomerId(loanDto1, 2);

	    }

	    @Test

	    void testGetLoanByCustomerId() throws LoansException {

	        List<Loan> customerLoans = loansService.getLoanByCustomerId(1);

	        assertEquals(1, customerLoans.size());

	        assertEquals(testLoan, customerLoans.get(0));

	    }

	    @Test
	    void testGetLoanByCustomerIdCustomerNotFound() throws LoansException {

	        loansService.getLoanByCustomerId(2);

	    }
	    
	    

	    @Test
	    void testGetLoanByCustomerIdNoLoansFound() throws LoansException {

	        testCustomer.setLoans(new ArrayList<>());

	        loansService.getLoanByCustomerId(1);

	    }

	}

}
