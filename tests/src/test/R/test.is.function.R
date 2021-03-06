# Generated by gen-unary-tests.R using GNU R version 3.2.0 (2015-04-16)
library(hamcrest)
is.function.foo <- function(...) 41
as.vector.foo <- function(...) 99
Math.bar <- function(...) 44
Summary.bar <- function(...) 45
Ops.bar <- function(...) 46
test.is.function.1 <- function() assertThat(is.function(NULL), identicalTo(FALSE))
test.is.function.2 <- function() assertThat(is.function(logical(0)), identicalTo(FALSE))
test.is.function.3 <- function() assertThat(is.function(c(TRUE, TRUE, FALSE, FALSE, TRUE)), identicalTo(FALSE))
test.is.function.4 <- function() assertThat(is.function(structure(c(TRUE, FALSE), .Names = c("a", ""))), identicalTo(FALSE))
test.is.function.5 <- function() assertThat(is.function(c(TRUE, FALSE, NA)), identicalTo(FALSE))
test.is.function.6 <- function() assertThat(is.function(integer(0)), identicalTo(FALSE))
test.is.function.7 <- function() assertThat(is.function(structure(integer(0), .Names = character(0))), identicalTo(FALSE))
test.is.function.8 <- function() assertThat(is.function(1:3), identicalTo(FALSE))
test.is.function.9 <- function() assertThat(is.function(c(1L, NA, 4L, NA, 999L)), identicalTo(FALSE))
test.is.function.10 <- function() assertThat(is.function(c(1L, 2L, 1073741824L, 1073741824L)), identicalTo(FALSE))
test.is.function.11 <- function() assertThat(is.function(numeric(0)), identicalTo(FALSE))
test.is.function.12 <- function() assertThat(is.function(c(3.14159, 6.28319, 9.42478, 12.5664, 15.708)), identicalTo(FALSE))
test.is.function.13 <- function() assertThat(is.function(c(-3.14159, -6.28319, -9.42478, -12.5664, -15.708)), identicalTo(FALSE))
test.is.function.14 <- function() assertThat(is.function(structure(1:2, .Names = c("a", "b"))), identicalTo(FALSE))
test.is.function.15 <- function() assertThat(is.function(structure(c(1.5, 2.5), .Names = c("a", "b"))), identicalTo(FALSE))
test.is.function.16 <- function() assertThat(is.function(c(1.5, 1.51, 0, 1.49, -30)), identicalTo(FALSE))
test.is.function.17 <- function() assertThat(is.function(c(1.5, 1.51, 0, 1.49, -30, NA)), identicalTo(FALSE))
test.is.function.18 <- function() assertThat(is.function(c(1.5, 1.51, 0, 1.49, -30, NaN)), identicalTo(FALSE))
test.is.function.19 <- function() assertThat(is.function(c(1.5, 1.51, 0, 1.49, -30, Inf)), identicalTo(FALSE))
test.is.function.20 <- function() assertThat(is.function(c(1.5, 1.51, 0, 1.49, -30, -Inf)), identicalTo(FALSE))
test.is.function.21 <- function() assertThat(is.function(character(0)), identicalTo(FALSE))
test.is.function.22 <- function() assertThat(is.function(c("4.1", "blahh", "99.9", "-413", NA)), identicalTo(FALSE))
test.is.function.23 <- function() assertThat(is.function(list(1, 2, 3)), identicalTo(FALSE))
test.is.function.24 <- function() assertThat(is.function(list(1, 2, NULL)), identicalTo(FALSE))
test.is.function.25 <- function() assertThat(is.function(list(1L, 2L, 3L)), identicalTo(FALSE))
test.is.function.26 <- function() assertThat(is.function(list(1L, 2L, NULL)), identicalTo(FALSE))
test.is.function.27 <- function() assertThat(is.function(list(1, 2, list(3, 4))), identicalTo(FALSE))
test.is.function.28 <- function() assertThat(is.function(structure(1:12, .Dim = 3:4)), identicalTo(FALSE))
test.is.function.29 <- function() assertThat(is.function(structure(1:12, .Dim = 3:4, .Dimnames = structure(list(    x = c("a", "b", "c"), y = c("d", "e", "f", "g")), .Names = c("x", "y")))), identicalTo(FALSE))
test.is.function.30 <- function() assertThat(is.function(structure(1:3, rando.attrib = 941L)), identicalTo(FALSE))
test.is.function.31 <- function() assertThat(is.function(structure(1:3, .Dim = 3L, .Dimnames = list(c("a", "b", "c")))), identicalTo(FALSE))
test.is.function.32 <- function() assertThat(is.function(structure(list("foo"), class = "foo")), identicalTo(FALSE))
test.is.function.33 <- function() assertThat(is.function(structure(list("bar"), class = "foo")), identicalTo(FALSE))
test.is.function.34 <- function() assertThat(is.function(quote(xyz)), identicalTo(FALSE))
test.is.function.35 <- function() assertThat(is.function(quote(sin(3.14))), identicalTo(FALSE))
test.is.function.36 <- function() assertThat(is.function(structure("foo", class = "foo")), identicalTo(FALSE))
test.is.function.37 <- function() assertThat(is.function(structure(list(1L, "bar"), class = "bar")), identicalTo(FALSE))
