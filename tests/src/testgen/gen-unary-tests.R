


## Generates test cases for
## miscellaneous unary functions

source("src/testgen/gen.R")

fns <- c('as.array',
         'as.expression',
         'as.call',
         'as.character',
         'as.complex',
         'as.double',
         'as.factor',
         'as.integer',
         'as.list',
         'as.logical',
         'as.matrix',
         'as.name',
         'as.numeric',
         'as.ordered',
         'as.pairlist',
         'as.raw',
         'as.single',
         'as.symbol',
         'as.vector',
         'is.array',
         'is.atomic',
         'is.call',
         'is.character',
         'is.complex',
         'is.double',
         'is.element',
         'is.environment',
         'is.expression',
         'is.factor',
         'is.finite',
         'is.function',
         'is.infinite',
         'is.integer',
         'is.language',
         'is.list',
         'is.loaded',
         'is.logical',
         'is.matrix',
         'is.na',
         'is.name',
         'is.nan',
         'is.null',
         'is.numeric',
         'is.object',
         'is.ordered',
         'is.pairlist',
         'is.primitive',
         'is.R',
         'is.raw',
         'is.recursive',
         'is.single',
         'is.symbol',
         'is.table',
         'is.unsorted',
         'is.vector',
         'is.na',
         'length',
         't',
         'unlist')


inputs <- list(
  NULL,

  # logical
  logical(0),
  c(TRUE, TRUE, FALSE, FALSE, TRUE),
  c(a=TRUE, FALSE),
  c(TRUE, FALSE, NA),
 
  # integer
  integer(0),
  structure(integer(0), .Names = character(0)),
  c(1L, 2L, 3L),
  c(1L, NA, 4L, NA, 999L),
  c(1L, 2L, 1073741824L, 1073741824L), # overflow
  
  # double
  double(0),
  signif(1:5 * pi),
  signif(1:5 * -pi),
  c(a = 1L, b = 2L),  # names
  c(a = 1.5, b = 2.5), 
  c(1.5, 1.51, 0, 1.49, -30),
  c(1.5, 1.51, 0, 1.49, -30, NA),
  c(1.5, 1.51, 0, 1.49, -30, NaN),
  c(1.5, 1.51, 0, 1.49, -30, Inf),
  c(1.5, 1.51, 0, 1.49, -30, -Inf),


  # character
  character(0),
  c('4.1', 'blahh', '99.9', '-413', NA),

  # complex
  complex(0),
  
  
  # lists
  list(1, 2, 3),
  list(1, 2, NULL),
  list(1L, 2L, 3L),
  list(1L, 2L, NULL),
  list(1, 2, list(3, 4)),
  list(3, "a", list("b", z = list(TRUE, "c"))),
  
  # matrices
  matrix(1:12, nrow = 3), 
  matrix(1:12, nrow = 3, dimnames = list(x=letters[1:3], y=letters[4:7])),  
  structure(1:3, rando.attrib=941L),

  #arrays
  array(1:3, dim = 3L, dimnames = list(c('a', 'b', 'c'))),
  array(1:3, dim = 3L, dimnames = list(z = c('a', 'b', 'c'))),
  
  # S3 dispatch?
  structure(list('foo'), class='foo'),
  structure(list('bar'), class='foo'),
  
  # Symbols
  as.name('xyz'),
  
  # Function Calls
  call('sin', 3.14)
)


for(fn in fns) {

  # Setup generic implementations
  test <- test.open("gen-unary-tests.R", fn)
  writeln(test, "library(hamcrest)")
  
  # define some nonsense generic functions
  writeFixture(test, "%s.foo <- function(...) 41", fn)
  writeFixture(test, "as.vector.foo <- function(...) 99", fn)
  writeFixture(test, "Math.bar <- function(...) 44")
  writeFixture(test, "Summary.bar <- function(...) 45")
  writeFixture(test, "Ops.bar <- function(...) 46")
  
  
  tol <- 0.0001
  
  # Check that numerical values are correct
  for(input in inputs) {
    writeTest(test, fn, input, tol = tol)
  }
  
  # Check S3 dispatch
  writeTest(test, fn, structure("foo", class='foo'))
  writeTest(test, fn, structure(list(1L, "bar"), class='bar'))
  
  
  if(fn %in% c("unlist")) {
    # Try recursive = FALSE
    for(input in inputs) {
      writeTest(test, fn, input, recursive = FALSE)
    }
    # Try use.names = FALSE
    for(input in inputs) {
      writeTest(test, fn, input, use.names = FALSE)
    }
  }
  
  if(fn %in% c("is.vector", "as.vector")) {
    modes <- c("any",
               "logical", "integer", "numeric", "double", "complex", "raw", "character",
               "list", "expression", "rubish", NA)
    for(input in inputs) {
      for(mode in modes) {
        writeTest(test, fn, input, mode = mode, tol = tol)
      }
    }
  }
  
  # Remove symbols we added to the global environment
  rm(list = c(sprintf("%s.foo", fn), "as.vector.foo", "Math.bar", "Summary.bar", "Ops.bar"))
  
  close(test)
}

run.test <- function() {
    for(f in ls(envir = .GlobalEnv)) {
      if(grepl(f, pattern="^test\\.")) {
        print(f)
        do.call(f, list())
      } 
    }
}

