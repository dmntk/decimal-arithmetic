extern crate rust_decimal;
extern crate rust_decimal_macros;

use rust_decimal::prelude::ToPrimitive;
use rust_decimal::{Decimal, MathematicalOps};
use rust_decimal_macros::dec;
use std::ops::{Add, Div, Mul, Sub};
use std::str::FromStr;

const DEC_0: Decimal = dec!(0);
const DEC_MINUS_1: Decimal = dec!(-1);
const DEC_1: Decimal = dec!(1);
const DEC_12: Decimal = dec!(12);

/**
 * Calculates monthly payment = p * r/12/( 1 - (1 + r/12) ** -n)
 */
fn monthly_payment(p: Decimal, r: Decimal, n: Decimal) -> Decimal {
  println!("\n>> calculating monthlyPayment:");

  println!(">> p = {}", p);
  println!(">> r = {}", r);
  println!(">> n = {}", n);

  let r_div_12 = r.div(DEC_12);
  println!("r/12 = {}", r_div_12);

  let one_plus_r_div_12 = DEC_1.add(r_div_12);
  println!("1 + r/12 = {}", one_plus_r_div_12);

  let one_plus_r_div_12_pow_neg_n = one_plus_r_div_12.powi(-n.to_i64().unwrap());
  println!("(1 + r/12) ** -n = {}", one_plus_r_div_12_pow_neg_n);

  let one_minus_one_plus_r_div_12_pow_neg_n = DEC_1.sub(one_plus_r_div_12_pow_neg_n);
  println!("1 - (1 + r/12) ** -n = {}", one_minus_one_plus_r_div_12_pow_neg_n);

  let p_mul_r = p.mul(r);
  println!("p * r = {}", p_mul_r);

  let p_mul_r_div_12 = p_mul_r.div(DEC_12);
  println!("p * r / 12 = {}", p_mul_r_div_12);

  let result = p_mul_r_div_12.div(one_minus_one_plus_r_div_12_pow_neg_n);
  println!("p * r / 12 / ( 1 - (1 + r/12) ** -n) = {}", result);
  println!("<< monthlyPayment = {}", result);

  result
}

/// Calculates loanAmt = requestedAmt * (1 + points/100) + fee
fn loan_amt(requested_amt: Decimal, points: Decimal, fee: Decimal) -> Decimal {
  println!("\n>> calculating loanAmt:");

  println!(">> requestedAmt = {}", requested_amt);
  println!(">>       points = {}", points);
  println!(">>          fee = {}", fee);

  let points_div_100 = points.div(dec!(100));
  println!("points/100 = {}", points_div_100);

  let one_plus_points_div_100 = DEC_1.add(points_div_100);
  println!("1 + points/100 = {}", one_plus_points_div_100);

  let requested_amt_mul_one_plus_points_div_100 = requested_amt.mul(one_plus_points_div_100);
  println!("requestedAmt * (1 + points/100) = {}", requested_amt_mul_one_plus_points_div_100);

  let result = requested_amt_mul_one_plus_points_div_100.add(fee);
  println!("requestedAmt * (1 + points/100) + fee = {}", result);
  println!("<< loanAmt = {}", result);

  result
}

/// Calculates equity36Mo = p * (1 + r/12) ** n - pmt * (-1 + (1 + r/12) ** n) / r
fn equity_36_mo(p: Decimal, r: Decimal, n: Decimal, pmt: Decimal) -> Decimal {
  println!("\n>> calculating equity36Mo:");

  println!(">>   p = {}", p);
  println!(">>   r = {}", r);
  println!(">>   n = {}", n);
  println!(">> pmt = {}", pmt);

  let r_div_12 = r.div(DEC_12);
  println!("r/12 = {}", r_div_12);

  let one_plus_r_div_12 = DEC_1.add(r_div_12);
  println!("1 + r/12 = {}", one_plus_r_div_12);

  let one_plus_r_div_12_pow_n = one_plus_r_div_12.powi(n.to_i64().unwrap());
  println!("(1 + r/12) ** n = {}", one_plus_r_div_12_pow_n);

  let p_mul_one_plus_r_div_12_pow_n = p.mul(one_plus_r_div_12_pow_n);
  println!("p * (1 + r/12) ** n = {}", p_mul_one_plus_r_div_12_pow_n);

  let neg_one_one_plus_r_div_12_pow_n = DEC_MINUS_1.add(one_plus_r_div_12_pow_n);
  println!("-1 + (1 + r/12) ** n = {}", neg_one_one_plus_r_div_12_pow_n);

  let pmt_mul_neg_one_one_plus_r_div_12_pow_n = pmt.mul(neg_one_one_plus_r_div_12_pow_n);
  println!("pmt * (-1 + (1 + r/12) ** n) = {}", pmt_mul_neg_one_one_plus_r_div_12_pow_n);

  let pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r = pmt_mul_neg_one_one_plus_r_div_12_pow_n.div(r);
  println!("pmt * (-1 + (1 + r/12) ** n) / r = {}", pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r);

  let result = p_mul_one_plus_r_div_12_pow_n.sub(pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r);
  println!("p * (1 + r/12) ** n - pmt * (-1 + (1 + r/12) ** n) / r = {}", result);
  println!("<< equity36Mo = {}", result);

  result
}

/// Calculates equity36moPct = 1 - equity36Mo(loanAmt, rate, n, paymentAmt) / requestedAmt * 0.8
fn equity_36_mo_pct(requested_amt: Decimal, loan_amt: Decimal, rate: Decimal, equity_36_mo: Decimal, payment_amt: Decimal) -> Decimal {
  println!("\n>> calculating equity36moPct:");

  println!(">> requestedAmt = {}", requested_amt);
  println!(">>      loanAmt = {}", loan_amt);
  println!(">>         rate = {}", rate);
  println!(">>   equity36Mo = {}", equity_36_mo);
  println!(">>   paymentAmt = {}", payment_amt);

  let equity_36_mo_div_requested_amt = equity_36_mo.div(requested_amt);
  println!("equity36Mo / requestedAmt = {}", equity_36_mo_div_requested_amt);

  let equity_36_mo_div_requested_amt_mul_08 = equity_36_mo_div_requested_amt.mul(dec!(0.8));
  println!("equity36Mo / requestedAmt * 0.8 = {}", equity_36_mo_div_requested_amt_mul_08);

  let result = DEC_1.sub(equity_36_mo_div_requested_amt_mul_08);
  println!("1 - equity36Mo / requestedAmt * 0.8 = {}", result);
  println!("<< equity36moPct = {}", result);

  result
}

fn run() {
  let requested_amt = dec!(330000);
  let points = DEC_0;
  let fee = DEC_0;
  let rate = dec!(0.035);
  let loan_amt = loan_amt(requested_amt, points, fee);
  let payment_amt = monthly_payment(loan_amt, rate, dec!(360));
  let equity_36_mo = equity_36_mo(loan_amt, rate, dec!(36), payment_amt);
  let equity_36_mo_pct = equity_36_mo_pct(requested_amt, loan_amt, rate, equity_36_mo, payment_amt);
  println!("\n\nRESULT");
  println!("--------------------------------------------------------------------------------------");
  println!("  paymentAmt    = {}", payment_amt);
  println!("  equity36moPct = {}", equity_36_mo_pct);
}

fn check_sqrt(value_str: &str) {
  let value = Decimal::from_str(value_str).unwrap();
  println!("sqrt({}) = {}", value, value.sqrt().unwrap().normalize());
}

fn check_log(value_str: &str) {
  let value = Decimal::from_str(value_str).unwrap();
  println!("ln({}) = {}", value, value.ln().normalize());
}

fn check_exp(value_str: &str) {
  let value = Decimal::from_str(value_str).unwrap();
  println!("exp({}) = {}", value, value.exp_with_tolerance(dec!(0.000_000_2)).normalize());
  println!("exp({}) = {}", value, value.exp_with_tolerance(dec!(0.000_000_002)).normalize());
  println!("exp({}) = {}", value, value.exp_with_tolerance(dec!(0.000_000_000_2)).normalize());
  println!("exp({}) = {}", value, value.exp_with_tolerance(dec!(0.000_000_000_000_2)).normalize());
}

fn main() {
  run();

  // sqrt()
  check_sqrt("16");
  check_sqrt("13.475896857");

  // log()
  check_log("10");
  check_log("18.384757546");

  // exp()
  check_exp("5");
  check_exp("11");
}
