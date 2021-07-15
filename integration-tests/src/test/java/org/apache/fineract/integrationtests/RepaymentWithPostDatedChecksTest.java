package org.apache.fineract.integrationtests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.accounting.Account;
import org.apache.fineract.integrationtests.common.loans.LoanApplicationTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanProductTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanStatusChecker;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepaymentWithPostDatedChecksTest {

    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private static final Logger LOG = LoggerFactory.getLogger(LoanDisbursementDetailsIntegrationTest.class);
    private LoanTransactionHelper loanTransactionHelper;
    private Integer loanID;
    private Integer disbursementId;
    final String approveDate = "01 March 2014";
    final String expectedDisbursementDate = "01 March 2014";
    final String proposedAmount = "5000";
    final String approvalAmount = "5000";
    private final SimpleDateFormat dateFormatterStandard = new SimpleDateFormat("dd MMMM yyyy");

    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

    @Test
    public void testRepaymentWithPostDatedChecks() {

        Calendar meetingCalendar = Calendar.getInstance();
        meetingCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        meetingCalendar.setTime(new java.util.Date());

        int today = meetingCalendar.get(Calendar.DAY_OF_WEEK);
        // making sure that the meeting calendar is set for the coming monday.
        if (today >= Calendar.MONDAY) {
            meetingCalendar.add(Calendar.DAY_OF_YEAR, +(Calendar.MONDAY - today + 7));
        } else {
            meetingCalendar.add(Calendar.DAY_OF_YEAR, +(Calendar.MONDAY - today));
        }

        Calendar groupMeetingChangeCalendar = (Calendar) meetingCalendar.clone();

        meetingCalendar.add(Calendar.WEEK_OF_YEAR, -3);

        final String groupMeetingDate = this.dateFormatterStandard.format(meetingCalendar.getTime());

        final String disbursalDate = groupMeetingDate;

        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        final Integer loanProductID = createLoanProduct(false, "1");

        final String loanApplicationJSON = new LoanApplicationTestBuilder() //
                .withPrincipal("100000") //
                .withLoanTermFrequency("2") //
                .withLoanTermFrequencyAsMonths() //
                .withNumberOfRepayments("2") //
                .withRepaymentEveryAfter("1") //
                .withRepaymentFrequencyTypeAsMonths() //
                .withInterestRatePerPeriod("2") //
                .withAmortizationTypeAsEqualInstallments() //
                .withInterestTypeAsDecliningBalance() //
                .withInterestCalculationPeriodTypeSameAsRepaymentPeriod() //
                .withExpectedDisbursementDate("20 September 2011") //
                .withSubmittedOnDate("20 September 2011") //
                .build(clientID.toString(), loanProductID.toString(), null);

        ArrayList<HashMap> loanSchedule = this.loanTransactionHelper.getLoanRepaymentSchedule(this.requestSpec, this.responseSpec, loanID);

        final Integer loanId = this.loanTransactionHelper.getLoanId(loanApplicationJSON);

        /**
         * TODO: Get Repayment Schedule.
         */

        // Test for loan account is created
        Assertions.assertNotNull(loanId);
        HashMap loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanId);

        LoanStatusChecker.verifyLoanIsPending(loanStatusHashMap);

        // Test for loan account is created, can be approved
        this.loanTransactionHelper.approveLoan(disbursalDate, loanId);
        loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanId);
        LoanStatusChecker.verifyLoanIsApproved(loanStatusHashMap);

        // Test for loan account approved can be disbursed
        this.loanTransactionHelper.disburseLoan(disbursalDate, loanId);
        loanStatusHashMap = LoanStatusChecker.getStatusOfLoan(this.requestSpec, this.responseSpec, loanId);
        LoanStatusChecker.verifyLoanIsActive(loanStatusHashMap);

    }

    private Integer createLoanProduct(final boolean multiDisburseLoan, final String accountingRule, final Account... accounts) {
        LOG.info("------------------------------CREATING NEW LOAN PRODUCT ---------------------------------------");
        LoanProductTestBuilder builder = new LoanProductTestBuilder() //
                .withPrincipal("12,000.00") //
                .withNumberOfRepayments("4") //
                .withRepaymentAfterEvery("1") //
                .withRepaymentTypeAsMonth() //
                .withinterestRatePerPeriod("1") //
                .withInterestRateFrequencyTypeAsMonths() //
                .withAmortizationTypeAsEqualInstallments() //
                .withInterestTypeAsDecliningBalance() //
                .withTranches(multiDisburseLoan) //
                .withAccounting(accountingRule, accounts);
        if (multiDisburseLoan) {
            builder = builder.withInterestCalculationPeriodTypeAsRepaymentPeriod(true);
        }
        final String loanProductJSON = builder.build(null);
        return this.loanTransactionHelper.getLoanProductId(loanProductJSON);
    }
}
