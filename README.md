# FEEL arithmetic comparison

## Example from official test case

Test case: [compliance-level-3/0014-loan-comparison](https://github.com/dmn-tck/tck/blob/master/TestCases/compliance-level-3/0014-loan-comparison/0014-loan-comparison-test-01.xml)

| Language and settings          | paymentAmt                                  | equity36moPct                            |
|--------------------------------|---------------------------------------------|------------------------------------------|
| **FEEL** test case             | `1481.847469769075`                         | `0.1229130806675829`                     |
| **Java** double                | `1481.8474697691177`                        | `0.1229130806675861`                     |
| **Java** BigDecimal DECIMAL128 | `1481.847469769120902911415325410837588883` | `0.1229130806675864888391782030891035`   |
| **Rust** rust_decimal          | `1481.8474697691209029114153224`            | `0.1229130806675864888391782027`         |
| **Python** Decimal             | `1481.84746976912096426540912284564247`     | `0.122913080667586431825357200570817236` |

## FEEL math

### sqrt()

| Language and settings          | sqrt(16) | sqrt(13.475896857)                   |
|--------------------------------|----------|--------------------------------------|
| **Java** BigDecimal DECIMAL128 | `4`      | `3.67095312650543115049087926601238` |
| **Rust** rust_decimal          | `4`      | `3.670953126505431150490879266`      |

### log()

| Language and settings          | log(10)                                  | log(18.384757546)                        |
|--------------------------------|------------------------------------------|------------------------------------------|
| **Java** BigDecimal DECIMAL128 | `2.302585092994045684017991454684364208` | `2.911521927067692382874039675862431077` |
| **Rust** rust_decimal          | `2.3025850929940456840179914554`         | `2.9115219270676923828740396759`         |

### exp()

| Language and settings          | exp(5)                                     |
|--------------------------------|--------------------------------------------|
| **FEEL** specification         | `148.413159102577`                         |
| **Java** BigDecimal DECIMAL128 | `148.413159102576603421115580040552279711` |
| **Rust** rust_decimal          | `148.41315910242917001115674611`           |

## Some benchmarks

| Operation               | rust_decimal | decNumber with FFI |       |
|-------------------------|--------------|--------------------|-------|
| addition: 0.1+0.3       |  `9 ns`      |  `15 ns`           | +166% |
| subtraction: 0.1-0.3    |  `9 ns`      |  `76 ns`           | +844% |
| multiplication: 0.1*0.3 |  `8 ns`      |  `30 ns`           | +375% |
| division: 0.1/0.3       | `33 ns`      |  `89 ns`           | +269% |
| comparison: 0.1 > 0.3   |  `4 ns`      |  `24 ns`           | +600% |
