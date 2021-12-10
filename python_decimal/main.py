from decimal import *


def calc_loan_amt(requested_amt, points, fee):
    print('\n>> calculating loanAmt:')

    print(f'>> requestedAmt = {requested_amt}')
    print(f'>>       points = {points}')
    print(f'>>          fee = {fee}')

    points_div_100 = points / Decimal(100)
    print(f'points/100 = {points_div_100}')

    one_plus_points_div_100 = Decimal(1) + points_div_100
    print(f'1 + points/100 = {one_plus_points_div_100}')

    requested_amt_mul_one_plus_points_div_100 = requested_amt * one_plus_points_div_100
    print(f'requestedAmt * (1 + points/100) = {requested_amt_mul_one_plus_points_div_100}')

    result = requested_amt_mul_one_plus_points_div_100 + fee
    print(f'requestedAmt * (1 + points/100) + fee = {result}')
    print(f'"<< loanAmt = {result}')

    return result


def calc_monthly_payment(p, r, n):
    print('\n>> calculating monthlyPayment:')

    print(f'>> p = {p}')
    print(f'>> r = {r}')
    print(f'>> n = {n}')

    r_div_12 = r / Decimal(12)
    print(f'r/12 = {r_div_12}')

    one_plus_r_div_12 = Decimal(1) + r_div_12
    print(f'1 + r/12 = {one_plus_r_div_12}')

    one_plus_r_div_12_pow_neg_n = one_plus_r_div_12 ** -n
    print(f'(1 + r/12) ** -n = {one_plus_r_div_12_pow_neg_n}')

    one_minus_one_plus_r_div_12_pow_neg_n = Decimal(1) - one_plus_r_div_12_pow_neg_n
    print(f'1 - (1 + r/12) ** -n = {one_minus_one_plus_r_div_12_pow_neg_n}')

    p_mul_r = p * r
    print(f'p * r = {p_mul_r}')

    p_mul_r_div_12 = p_mul_r / Decimal(12)
    print(f'p * r / 12 = {p_mul_r_div_12}')

    result = p_mul_r_div_12 / one_minus_one_plus_r_div_12_pow_neg_n
    print(f'p * r / 12 / ( 1 - (1 + r/12) ** -n) = {result}')
    print(f'<< monthlyPayment = {result}')

    return result


def calc_equity_36_mo(p, r, n, pmt):
    print('\n>> calculating equity36Mo:')

    print(f'>>   p = {p}')
    print(f'>>   r = {r}')
    print(f'>>   n = {n}')
    print(f'>> pmt = {pmt}')

    r_div_12 = r / Decimal(12)
    print(f'r/12 = {r_div_12}')

    one_plus_r_div_12 = Decimal(1) + r_div_12
    print(f'1 + r/12 = {one_plus_r_div_12}')

    one_plus_r_div_12_pow_n = one_plus_r_div_12 ** n
    print(f'(1 + r/12) ** n = {one_plus_r_div_12_pow_n}')

    p_mul_one_plus_r_div_12_pow_n = p * one_plus_r_div_12_pow_n
    print(f'p * (1 + r/12) ** n = {p_mul_one_plus_r_div_12_pow_n}')

    neg_one_one_plus_r_div_12_pow_n = Decimal(-1) + one_plus_r_div_12_pow_n
    print(f'-1 + (1 + r/12) ** n = {neg_one_one_plus_r_div_12_pow_n}')

    pmt_mul_neg_one_one_plus_r_div_12_pow_n = pmt * neg_one_one_plus_r_div_12_pow_n
    print(f'pmt * (-1 + (1 + r/12) ** n) = {pmt_mul_neg_one_one_plus_r_div_12_pow_n}')

    pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r = pmt_mul_neg_one_one_plus_r_div_12_pow_n / r
    print(f'pmt * (-1 + (1 + r/12) ** n) / r = {pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r}')

    result = p_mul_one_plus_r_div_12_pow_n - pmt_mul_neg_one_one_plus_r_div_12_pow_n_div_r
    print(f'p * (1 + r/12) ** n - pmt * (-1 + (1 + r/12) ** n) / r = {result}')
    print(f'<< equity36Mo = {result}')

    return result


def calc_equity_36_mo_pct(requested_amt, loan_amt, rate, equity_36_mo, payment_amt):
    print(f'\n>> calculating equity36moPct:')

    print(f'>> requestedAmt = {requested_amt}')
    print(f'>>      loanAmt = {loan_amt}')
    print(f'>>         rate = {rate}')
    print(f'>>   equity36Mo = {equity_36_mo}')
    print(f'>>   paymentAmt = {payment_amt}')

    equity_36_mo_div_requested_amt = equity_36_mo / requested_amt
    print(f'equity36Mo / requestedAmt = {equity_36_mo_div_requested_amt}')

    equity_36_mo_div_requested_amt_mul_08 = equity_36_mo_div_requested_amt * Decimal(0.8)
    print(f'equity36Mo / requestedAmt * 0.8 = {equity_36_mo_div_requested_amt_mul_08}')

    result = Decimal(1) - equity_36_mo_div_requested_amt_mul_08
    print(f'1 - equity36Mo / requestedAmt * 0.8 = {result}')
    print(f'<< equity36moPct = {result}')

    return result


def main():
    requested_amt = Decimal(330000)
    points = Decimal(0)
    fee = Decimal(0)
    rate = Decimal(0.035)
    loan_amt = calc_loan_amt(requested_amt, points, fee)
    payment_amt = calc_monthly_payment(loan_amt, rate, Decimal(360))
    equity_36_mo = calc_equity_36_mo(loan_amt, rate, Decimal(36), payment_amt)
    equity_36_mo_pct = calc_equity_36_mo_pct(requested_amt, loan_amt, rate, equity_36_mo, payment_amt)
    print('\n\nRESULT')
    print('--------------------------------------------------------------------------------------')
    print(f'  paymentAmt    = {payment_amt}')
    print(f'  equity36moPct = {equity_36_mo_pct}')


if __name__ == '__main__':
    main()
