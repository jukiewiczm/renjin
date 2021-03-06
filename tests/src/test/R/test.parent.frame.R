
library(hamcrest)


## Functions used in test cases

foo.default <- function(n) {
    parent.frame(n)
}

foo <- function(n) {
    UseMethod("foo")
}

g <- function() {
    foo.default(2)
}

as.character.baz <- function(x) {
    parent.frame()
}

## Test cases

test.simple <- function() {
    zz <- 99
    pf <- foo.default(1)
    
    assertThat(pf$zz, identicalTo(99))
}

test.s3 <- function() {
    qq <- 42
    pf <- foo(1)
    
    assertThat(pf$qq, identicalTo(42))
}

test.s3.from.eval <- function() {
    qz <- 93
    pf <- eval(quote(foo(1)))

    assertThat(pf$qz, identicalTo(93))
}


test.three <- function() {
    qz <- 43
    pf <- g()
    assertThat(pf$qz, identicalTo(43))
}


# Test call from top level
pf <- as.character(structure(1, class = "baz"))
assertThat(pf, identicalTo(.GlobalEnv))