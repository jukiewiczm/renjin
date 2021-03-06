# Generated by gen-misc-tests.R using GNU R version 3.2.0 (2015-04-16)
library(hamcrest)
test.pretty.1 <- function() assertThat(pretty(x = 1:15), identicalTo(c(0, 2, 4, 6, 8, 10, 12, 14, 16)))
test.pretty.2 <- function() assertThat(pretty(x = 1:15, h = 2), identicalTo(c(0, 5, 10, 15)))
test.pretty.3 <- function() assertThat(pretty(x = 1:15, n = 3), identicalTo(c(0, 5, 10, 15)))
test.pretty.4 <- function() assertThat(pretty(x = c(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30)), identicalTo(c(0, 5, 10, 15, 20, 25, 30)))
test.pretty.5 <- function() assertThat(pretty(x = 1:20), identicalTo(c(0, 5, 10, 15, 20)))
test.pretty.6 <- function() assertThat(pretty(x = 1:20, n = 2), identicalTo(c(0, 10, 20)))
test.pretty.7 <- function() assertThat(pretty(x = 1:20, n = 10), identicalTo(c(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20)))
test.pretty.8 <- function() assertThat(pretty(x = 3.14159265358979), identicalTo(c(2, 4)))
test.pretty.9 <- function() assertThat(pretty(x = 1.234e+100), identicalTo(c(1.2e+100, 1.3e+100), tol = 0.000001))
test.pretty.10 <- function() assertThat(pretty(x = 1001.1001), identicalTo(c(1000, 1100)))
test.pretty.11 <- function() assertThat(pretty(1001.1001, shrink = 0.2), identicalTo(c(1000, 1020)))
