package sofware.engos.feel.arithmetic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static sofware.engos.feel.arithmetic.Constants.*;

interface Constants {
  MathContext MATH_CONTEXT = MathContext.DECIMAL128;
  RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
  int SCALE = 36;
  BigDecimal DEC_MINUS_1 = dec_int(-1);
  BigDecimal DEC_1 = dec_int(1);
  BigDecimal DEC_12 = dec_int(12);
  BigDecimal DEC_100 = dec_int(100);

  static BigDecimal dec_str(String value) {
    return new BigDecimal(value, MATH_CONTEXT);
  }

  static BigDecimal dec_int(int value) {
    return new BigDecimal(value, MATH_CONTEXT);
  }
}

class LoanComparator {

  /**
   * Calculates equity36moPct = 1 - equity36Mo(loanAmt, rate, n, paymentAmt) / requestedAmt * 0.8
   */
  BigDecimal equity36moPct(BigDecimal requestedAmt, BigDecimal loanAmt, BigDecimal rate, BigDecimal n, BigDecimal paymentAmt) {
    System.out.print("\ncalculating equity36moPct:\n");
    System.out.printf(">> requestedAmt = %s\n", requestedAmt);
    System.out.printf(">>      loanAmt = %s\n", loanAmt);
    System.out.printf(">>         rate = %s\n", rate);
    System.out.printf(">>            n = %s\n", n);
    System.out.printf(">>   paymentAmt = %s\n\n", paymentAmt);

    BigDecimal equity36Mo = equity36Mo(loanAmt, rate, n, paymentAmt);
    System.out.printf("equity36Mo(loanAmt, rate, n, paymentAmt) = %s\n", equity36Mo);

    BigDecimal equity36Mo_div_requestedAmt = equity36Mo.divide(requestedAmt, SCALE, ROUNDING_MODE);
    System.out.printf("equity36Mo(loanAmt, rate, n, paymentAmt) / requestedAmt = %s\n", equity36Mo_div_requestedAmt);

    BigDecimal equity36Mo_div_requestedAmt_mul_08 = equity36Mo_div_requestedAmt.multiply(dec_str("0.8"), MATH_CONTEXT);
    System.out.printf("equity36Mo(loanAmt, rate, n, paymentAmt) / requestedAmt * 0.8 = %s\n", equity36Mo_div_requestedAmt_mul_08);

    BigDecimal result = DEC_1.subtract(equity36Mo_div_requestedAmt_mul_08, MATH_CONTEXT);
    System.out.printf("1 - equity36Mo(loanAmt, rate, n, paymentAmt) / requestedAmt * 0.8 = %s\n", result);
    System.out.printf("equity36moPct = %s\n", result);

    return result;
  }

  /**
   * Calculates monthly payment = p * r/12/( 1 - (1 + r/12) ** -n)
   */
  BigDecimal monthlyPayment(BigDecimal p, BigDecimal r, BigDecimal n) {
    System.out.print("\ncalculating monthlyPayment:\n");

    System.out.printf(">> p = %s\n", p);
    System.out.printf(">> r = %s\n", r);
    System.out.printf(">> n = %s\n\n", n);

    BigDecimal r_div_12 = r.divide(DEC_12, SCALE, ROUNDING_MODE);
    System.out.printf("r/12 = %s\n", r_div_12);

    BigDecimal one_plus_r_div_12 = DEC_1.add(r_div_12, MATH_CONTEXT);
    System.out.printf("1 + r/12 = %s\n", one_plus_r_div_12);

    BigDecimal one_plus_r_div_12_pow_neg_n = one_plus_r_div_12.pow(-n.intValue(), MATH_CONTEXT);
    System.out.printf("(1 + r/12) ** -n = %s\n", one_plus_r_div_12_pow_neg_n);

    BigDecimal one_minus_one_plus_r_div_12_pow_neg_n = DEC_1.subtract(one_plus_r_div_12_pow_neg_n, MATH_CONTEXT);
    System.out.printf("1 - (1 + r/12) ** -n = %s\n", one_minus_one_plus_r_div_12_pow_neg_n);

    BigDecimal p_mul_r = p.multiply(r, MATH_CONTEXT);
    System.out.printf("p * r = %s\n", p_mul_r);

    BigDecimal p_mul_r_div_12 = p_mul_r.divide(DEC_12, SCALE, ROUNDING_MODE);
    System.out.printf("p * r / 12 = %s\n", p_mul_r_div_12);

    BigDecimal result = p_mul_r_div_12.divide(one_minus_one_plus_r_div_12_pow_neg_n, SCALE, ROUNDING_MODE);
    System.out.printf("p * r / 12 / ( 1 - (1 + r/12) ** -n) = %s\n", result);
    System.out.printf("monthlyPayment = %s\n", result);

    return result;
  }

  /**
   * Calculates loanAmt = requestedAmt * (1 + points/100) + fee
   */
  BigDecimal loanAmt(BigDecimal requestedAmt, BigDecimal points, BigDecimal fee) {
    System.out.print("\ncalculating loanAmt:\n");

    System.out.printf(">> requestedAmt = %s\n", requestedAmt);
    System.out.printf(">>       points = %s\n", points);
    System.out.printf(">>          fee = %s\n\n", fee);

    BigDecimal points_div_100 = points.divide(DEC_100, SCALE, ROUNDING_MODE);
    System.out.printf("points/100 = %s\n", points_div_100);

    BigDecimal one_plus_points_div_100 = DEC_1.add(points_div_100, MATH_CONTEXT);
    System.out.printf("1 + points/100 = %s\n", one_plus_points_div_100);

    BigDecimal requested_amt_mul_one_plus_points_div_100 = requestedAmt.multiply(one_plus_points_div_100, MATH_CONTEXT);
    System.out.printf("requestedAmt * (1 + points/100) = %s\n", requested_amt_mul_one_plus_points_div_100);

    BigDecimal result = requested_amt_mul_one_plus_points_div_100.add(fee, MATH_CONTEXT);
    System.out.printf("requestedAmt * (1 + points/100) + fee = %s\n", result);
    System.out.printf("loanAmt = %s\n", result);

    return result;
  }

  /**
   * Calculates equity36Mo = p * (1 + r/12) ** n - pmt * (-1 + (1 + r/12) ** n) / r
   */
  BigDecimal equity36Mo(BigDecimal p, BigDecimal r, BigDecimal n, BigDecimal pmt) {
    System.out.print("\ncalculating equity36Mo:\n");

    System.out.printf(">>   p = %s\n", p);
    System.out.printf(">>   r = %s\n", r);
    System.out.printf(">>   n = %s\n", n);
    System.out.printf(">> pmt = %s\n\n", pmt);

    BigDecimal r_div_12 = r.divide(DEC_12, SCALE, ROUNDING_MODE);
    System.out.printf("r/12 = %s\n", r_div_12);

    BigDecimal one_plus_r_div_12 = DEC_1.add(r_div_12, MATH_CONTEXT);
    System.out.printf("1 + r/12 = %s\n", one_plus_r_div_12);

    BigDecimal one_plus_r_div_12_pow_n = one_plus_r_div_12.pow(n.intValue(), MATH_CONTEXT);
    System.out.printf("(1 + r/12) ** n = %s\n", one_plus_r_div_12_pow_n);

    BigDecimal p_mul_one_plus_r_div_12_pow_n = p.multiply(one_plus_r_div_12_pow_n, MATH_CONTEXT);
    System.out.printf("p * (1 + r/12) ** n = %s\n", p_mul_one_plus_r_div_12_pow_n);

    BigDecimal neg_one_one_plus_r_div_12_pow_n = DEC_MINUS_1.add(one_plus_r_div_12_pow_n, MATH_CONTEXT);
    System.out.printf("-1 + (1 + r/12) ** n = %s\n", neg_one_one_plus_r_div_12_pow_n);

    BigDecimal pmt_mul_neg_one_one_plus_r_div_12_pow_n = pmt.multiply(neg_one_one_plus_r_div_12_pow_n, MATH_CONTEXT);
    System.out.printf("pmt * (-1 + (1 + r/12) ** n) = %s\n", pmt_mul_neg_one_one_plus_r_div_12_pow_n);

    BigDecimal pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r = pmt_mul_neg_one_one_plus_r_div_12_pow_n.divide(r, SCALE, ROUNDING_MODE);
    System.out.printf("pmt * (-1 + (1 + r/12) ** n) / r = %s\n", pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r);

    BigDecimal result = p_mul_one_plus_r_div_12_pow_n.subtract(pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r, MATH_CONTEXT);
    System.out.printf("p * (1 + r/12) ** n - pmt * (-1 + (1 + r/12) ** n) / r = %s\n\n", result);
    System.out.printf("equity36Mo = %s\n\n", result);

    return result;
  }
}

public class Main {

  public static void main(String[] args) {
    LoanComparator comparator = new LoanComparator();
    BigDecimal requestedAmt = dec_str("330000");
    BigDecimal points = dec_int(0);
    BigDecimal fee = dec_int(0);
    BigDecimal rate = dec_str("0.03500");
    BigDecimal loanAmt = comparator.loanAmt(requestedAmt, points, fee);
    BigDecimal paymentAmt = comparator.monthlyPayment(loanAmt, rate, dec_int(360));
    BigDecimal equity36moPct = comparator.equity36moPct(requestedAmt, loanAmt, rate, dec_int(36), paymentAmt);
    System.out.printf(">> paymentAmt:test    = %s\n", "1481.847469769075");
    System.out.printf(">> paymentAmt:D128    = %s\n", paymentAmt);
    System.out.printf(">> paymentAmt:rust    = %s\n", "1481.8474697691209029114153224");
    System.out.printf(">> equity36moPct:test = %s\n", "0.1229130806675829");
    System.out.printf(">> equity36moPct:D128 = %s\n", equity36moPct);
    System.out.printf(">> equity36moPct:rust = %s\n", "0.1229130806675864888391782027");
  }
}
