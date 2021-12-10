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
