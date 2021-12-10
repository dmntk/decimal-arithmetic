package sofware.engos.feel.arithmetic;

class LoanComparator {

  /**
   * Calculates loanAmt = requestedAmt * (1 + points/100) + fee
   */
  double loanAmt(double requestedAmt, double points, double fee) {
    System.out.print("\n>> calculating loanAmt:\n");

    System.out.printf(">> requestedAmt = %s\n", requestedAmt);
    System.out.printf(">>       points = %s\n", points);
    System.out.printf(">>          fee = %s\n", fee);

    double points_div_100 = points / 100.0;
    System.out.printf("points/100 = %s\n", points_div_100);

    double one_plus_points_div_100 = 1.0 + points_div_100;
    System.out.printf("1 + points/100 = %s\n", one_plus_points_div_100);

    double requested_amt_mul_one_plus_points_div_100 = requestedAmt * one_plus_points_div_100;
    System.out.printf("requestedAmt * (1 + points/100) = %s\n", requested_amt_mul_one_plus_points_div_100);

    double result = requested_amt_mul_one_plus_points_div_100 + fee;
    System.out.printf("requestedAmt * (1 + points/100) + fee = %s\n", result);
    System.out.printf("<< loanAmt = %s\n", result);

    return result;
  }

  /**
   * Calculates monthly payment = p * r/12/( 1 - (1 + r/12) ** -n)
   */
  double monthlyPayment(double p, double r, double n) {
    System.out.print("\n>> calculating monthlyPayment:\n");

    System.out.printf(">> p = %s\n", p);
    System.out.printf(">> r = %s\n", r);
    System.out.printf(">> n = %s\n", n);

    double r_div_12 = r / 12.0;
    System.out.printf("r/12 = %s\n", r_div_12);

    double one_plus_r_div_12 = 1.0 + r_div_12;
    System.out.printf("1 + r/12 = %s\n", one_plus_r_div_12);

    double one_plus_r_div_12_pow_neg_n = Math.pow(one_plus_r_div_12, -n);
    System.out.printf("(1 + r/12) ** -n = %s\n", one_plus_r_div_12_pow_neg_n);

    double one_minus_one_plus_r_div_12_pow_neg_n = 1.0 - one_plus_r_div_12_pow_neg_n;
    System.out.printf("1 - (1 + r/12) ** -n = %s\n", one_minus_one_plus_r_div_12_pow_neg_n);

    double p_mul_r = p * r;
    System.out.printf("p * r = %s\n", p_mul_r);

    double p_mul_r_div_12 = p_mul_r / 12.0;
    System.out.printf("p * r / 12 = %s\n", p_mul_r_div_12);

    double result = p_mul_r_div_12 / one_minus_one_plus_r_div_12_pow_neg_n;
    System.out.printf("p * r / 12 / ( 1 - (1 + r/12) ** -n) = %s\n", result);
    System.out.printf("<< monthlyPayment = %s\n", result);

    return result;
  }

  /**
   * Calculates equity36Mo = p * (1 + r/12) ** n - pmt * (-1 + (1 + r/12) ** n) / r
   */
  double equity36Mo(double p, double r, double n, double pmt) {
    System.out.print("\n>> calculating equity36Mo:\n");

    System.out.printf(">>   p = %s\n", p);
    System.out.printf(">>   r = %s\n", r);
    System.out.printf(">>   n = %s\n", n);
    System.out.printf(">> pmt = %s\n", pmt);

    double r_div_12 = r / 12.0;
    System.out.printf("r/12 = %s\n", r_div_12);

    double one_plus_r_div_12 = 1.0 + r_div_12;
    System.out.printf("1 + r/12 = %s\n", one_plus_r_div_12);

    double one_plus_r_div_12_pow_n = Math.pow(one_plus_r_div_12, n);
    System.out.printf("(1 + r/12) ** n = %s\n", one_plus_r_div_12_pow_n);

    double p_mul_one_plus_r_div_12_pow_n = p * one_plus_r_div_12_pow_n;
    System.out.printf("p * (1 + r/12) ** n = %s\n", p_mul_one_plus_r_div_12_pow_n);

    double neg_one_one_plus_r_div_12_pow_n = -1.0 + one_plus_r_div_12_pow_n;
    System.out.printf("-1 + (1 + r/12) ** n = %s\n", neg_one_one_plus_r_div_12_pow_n);

    double pmt_mul_neg_one_one_plus_r_div_12_pow_n = pmt * neg_one_one_plus_r_div_12_pow_n;
    System.out.printf("pmt * (-1 + (1 + r/12) ** n) = %s\n", pmt_mul_neg_one_one_plus_r_div_12_pow_n);

    double pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r = pmt_mul_neg_one_one_plus_r_div_12_pow_n / r;
    System.out.printf("pmt * (-1 + (1 + r/12) ** n) / r = %s\n", pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r);

    double result = p_mul_one_plus_r_div_12_pow_n - pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r;
    System.out.printf("p * (1 + r/12) ** n - pmt * (-1 + (1 + r/12) ** n) / r = %s\n", result);
    System.out.printf("<< equity36Mo = %s\n", result);

    return result;
  }

  /**
   * Calculates equity36moPct = 1 - equity36Mo(loanAmt, rate, n, paymentAmt) / requestedAmt * 0.8
   */
  double equity36moPct(double requestedAmt, double loanAmt, double rate, double equity36Mo, double paymentAmt) {
    System.out.print("\n>> calculating equity36moPct:\n");

    System.out.printf(">> requestedAmt = %s\n", requestedAmt);
    System.out.printf(">>      loanAmt = %s\n", loanAmt);
    System.out.printf(">>         rate = %s\n", rate);
    System.out.printf(">>   equity36Mo = %s\n", equity36Mo);
    System.out.printf(">>   paymentAmt = %s\n", paymentAmt);


    double equity36Mo_div_requestedAmt = equity36Mo / requestedAmt;
    System.out.printf("equity36Mo / requestedAmt = %s\n", equity36Mo_div_requestedAmt);

    double equity36Mo_div_requestedAmt_mul_08 = equity36Mo_div_requestedAmt * 0.8;
    System.out.printf("equity36Mo / requestedAmt * 0.8 = %s\n", equity36Mo_div_requestedAmt_mul_08);

    double result = 1.0 - equity36Mo_div_requestedAmt_mul_08;
    System.out.printf("1 - equity36Mo / requestedAmt * 0.8 = %s\n", result);
    System.out.printf("<< equity36moPct = %s\n", result);

    return result;
  }
}

public class Main {

  public static void main(String[] args) {
    LoanComparator comparator = new LoanComparator();
    double requestedAmt = 330000.0;
    double points = 0.0;
    double fee = 0.0;
    double rate = 0.03500;
    double loanAmt = comparator.loanAmt(requestedAmt, points, fee);
    double paymentAmt = comparator.monthlyPayment(loanAmt, rate, 360.0);
    double equity36Mo = comparator.equity36Mo(loanAmt, rate, 36.0, paymentAmt);
    double equity36moPct = comparator.equity36moPct(requestedAmt, loanAmt, rate, equity36Mo, paymentAmt);
    System.out.println("\n\nRESULT");
    System.out.println("--------------------------------------------------------------------------------------");
    System.out.printf("  paymentAmt    = %s\n", paymentAmt);
    System.out.printf("  equity36moPct = %s\n", equity36moPct);
  }
}