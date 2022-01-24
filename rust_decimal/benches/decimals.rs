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
fn bench_dec_add_1(b: &mut Bencher) {
  let x = dec!(123.45);
  let y = dec!(0.3847847);
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

#[bench]
fn bench_dec_add_co(b: &mut Bencher) {
  let x = 0.1;
  let y = 0.2;
  b.iter(|| x + y)
}

#[bench]
fn bench_dec_sub_co(b: &mut Bencher) {
  let x = 0.1;
  let y = 0.2;
  b.iter(|| x - y)
}

#[bench]
fn bench_dec_mul_co(b: &mut Bencher) {
  let x = 0.1;
  let y = 0.2;
  b.iter(|| x * y)
}

#[bench]
fn bench_dec_div_co(b: &mut Bencher) {
  let x = 0.1;
  let y = 0.2;
  b.iter(|| x / y)
}

#[bench]
fn bench_dec_compare_co(b: &mut Bencher) {
  let x = 0.1;
  let y = 0.2;
  b.iter(|| x > y)
}
