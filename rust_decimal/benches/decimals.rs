#![feature(test)]

extern crate test;

use rust_decimal_macros::dec;
use test::Bencher;

#[bench]
fn bench_dec_add(b: &mut Bencher) {
  let x = dec!(0.1);
  let y = dec!(0.3);
  b.iter(|| x + y)
}

#[bench]
fn bench_dec_sub(b: &mut Bencher) {
  let x = dec!(0.1);
  let y = dec!(0.3);
  b.iter(|| x - y)
}

#[bench]
fn bench_dec_mul(b: &mut Bencher) {
  let x = dec!(0.1);
  let y = dec!(0.3);
  b.iter(|| x * y)
}

#[bench]
fn bench_dec_div(b: &mut Bencher) {
  let x = dec!(0.1);
  let y = dec!(0.3);
  b.iter(|| x / y)
}

#[bench]
fn bench_dec_compare(b: &mut Bencher) {
  let x = dec!(0.1);
  let y = dec!(0.3);
  b.iter(|| x > y)
}
