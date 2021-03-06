# Generated by gen-math-unary-tests.R using GNU R version 3.2.0 (2015-04-16)
library(hamcrest)
ceiling.foo <- function(...) 41
Math.bar <- function(...) 44
test.ceiling.1 <- function() assertThat(ceiling(-0.01), identicalTo(0))
test.ceiling.2 <- function() assertThat(ceiling(-0.1), identicalTo(0))
test.ceiling.3 <- function() assertThat(ceiling(-1), identicalTo(-1))
test.ceiling.4 <- function() assertThat(ceiling(-1.5), identicalTo(-1))
test.ceiling.5 <- function() assertThat(ceiling(-2), identicalTo(-2))
test.ceiling.6 <- function() assertThat(ceiling(-2.5), identicalTo(-2))
test.ceiling.7 <- function() assertThat(ceiling(-4), identicalTo(-4))
test.ceiling.8 <- function() assertThat(ceiling(-10), identicalTo(-10))
test.ceiling.9 <- function() assertThat(ceiling(-100), identicalTo(-100))
test.ceiling.10 <- function() assertThat(ceiling(-0.785398), identicalTo(0))
test.ceiling.11 <- function() assertThat(ceiling(-1.5708), identicalTo(-1))
test.ceiling.12 <- function() assertThat(ceiling(-3.14159), identicalTo(-3))
test.ceiling.13 <- function() assertThat(ceiling(-6.28319), identicalTo(-6))
test.ceiling.14 <- function() assertThat(ceiling(0.01), identicalTo(1))
test.ceiling.15 <- function() assertThat(ceiling(0.1), identicalTo(1))
test.ceiling.16 <- function() assertThat(ceiling(1), identicalTo(1))
test.ceiling.17 <- function() assertThat(ceiling(1.5), identicalTo(2))
test.ceiling.18 <- function() assertThat(ceiling(2), identicalTo(2))
test.ceiling.19 <- function() assertThat(ceiling(2.5), identicalTo(3))
test.ceiling.20 <- function() assertThat(ceiling(4), identicalTo(4))
test.ceiling.21 <- function() assertThat(ceiling(10), identicalTo(10))
test.ceiling.22 <- function() assertThat(ceiling(100), identicalTo(100))
test.ceiling.23 <- function() assertThat(ceiling(0.785398), identicalTo(1))
test.ceiling.24 <- function() assertThat(ceiling(1.5708), identicalTo(2))
test.ceiling.25 <- function() assertThat(ceiling(3.14159), identicalTo(4))
test.ceiling.26 <- function() assertThat(ceiling(6.28319), identicalTo(7))
test.ceiling.27 <- function() assertThat(ceiling(NULL), throwsError())
test.ceiling.28 <- function() assertThat(ceiling(c(0.01, 0.1, 1, 1.5)), identicalTo(c(1, 1, 1, 2)))
test.ceiling.29 <- function() assertThat(ceiling(integer(0)), identicalTo(numeric(0)))
test.ceiling.30 <- function() assertThat(ceiling(numeric(0)), identicalTo(numeric(0)))
test.ceiling.31 <- function() assertThat(ceiling(NaN), identicalTo(NaN))
test.ceiling.32 <- function() assertThat(ceiling(NA_real_), identicalTo(NA_real_))
test.ceiling.33 <- function() assertThat(ceiling(Inf), identicalTo(Inf))
test.ceiling.34 <- function() assertThat(ceiling(-Inf), identicalTo(-Inf))
test.ceiling.35 <- function() assertThat(ceiling(c(1L, 4L)), identicalTo(c(1, 4)))
test.ceiling.36 <- function() assertThat(ceiling(structure(1, class = "foo")), identicalTo(41))
test.ceiling.37 <- function() assertThat(ceiling(structure(1, class = "bar")), identicalTo(44))
test.ceiling.38 <- function() assertThat(ceiling(structure(list("a"), class = "foo")), identicalTo(41))
test.ceiling.39 <- function() assertThat(ceiling(structure(list("b"), class = "bar")), identicalTo(44))
test.ceiling.40 <- function() assertThat(ceiling(structure(c(1, 2, 3), .Names = c("a", "b", "c"))), identicalTo(structure(c(1, 2, 3), .Names = c("a", "b", "c"))))
test.ceiling.41 <- function() assertThat(ceiling(structure(c(1, 2), .Names = c("x", ""))), identicalTo(structure(c(1, 2), .Names = c("x", ""))))
test.ceiling.42 <- function() assertThat(ceiling(structure(1:12, .Dim = 3:4)), identicalTo(structure(c(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), .Dim = 3:4)))
test.ceiling.43 <- function() assertThat(ceiling(structure(0, rando.attr = 4L)), identicalTo(structure(0, rando.attr = 4L)))
test.ceiling.44 <- function() assertThat(ceiling(structure(0, class = "zinga")), identicalTo(structure(0, class = "zinga")))
