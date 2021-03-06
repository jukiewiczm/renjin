# --------------------------------------
# ASSERTION FUNCTION
# --------------------------------------
deparse0 <- function(expr) {
  paste(deparse(expr), collapse = "")
}

assertThat <- function(actual, matcher) {
	
	call <- match.call()

	if(!matcher(actual)) {
		stop(sprintf("\nassertThat(%s, %s) failed\nGot: %s", 
				deparse0(call$actual), deparse0(call$matcher), deparse0(actual)))
	}
}


assertTrue <- function(value) {

	call <- match.call()

	if(!identical(value, TRUE)) {
		stop(sprintf("\nassertTrue(%s) failed\nGot: %s", 
				deparse0(call$value), deparse0(value)))
	}	
}


assertFalse <- function(value) {

	call <- match.call()

	if(!identical(value, FALSE)) {
		stop(sprintf("\nassertFalse(%s) failed\nGot: %s", 
				deparse0(call$value), deparse0(value)))
	}	
}



# --------------------------------------
# MATCHER FUNCTIONS
# --------------------------------------
compareReal <- function(actual, expected, tol) {
  rel.diff <- abs(expected - actual) / abs(expected)
  finite <- is.finite(rel.diff) & expected != 0
  finiteValuesCloseEnough <- all(rel.diff[finite] < tol)
  nonFiniteValuesIdentical <- identical(expected[!finite], actual[!finite])
  
  return( finiteValuesCloseEnough && nonFiniteValuesIdentical )
}

identical.attributes <- function(actual, expected, tol = NULL) {
    # Should have the same set of names,
    # though not necessarily in the same order
    if(length(setdiff(names(expected), names(actual))) > 0) {
        return(FALSE)
    }
    
    # Otherwise verify that the values are identical
    for(a in names(expected)) {
        if(!identical.rec(actual[[a]], expected[[a]], tol)) {
            return(FALSE)
        }
    }
    return(TRUE)
}

identical.rec <- function(actual, expected, tol = NULL) {
    if (length(actual) != length(expected))
      return(FALSE)
    if (typeof(actual) != typeof(expected))
      return(FALSE)
    if (!identical.attributes(attributes(actual), attributes(expected), tol)) {
      return(FALSE)
    }
    if (is.list(actual)) {
      for (i in seq_along(actual)) {
        isSame <- identical.rec(actual[[i]], expected[[i]], tol)
        if (!isSame){
          return(FALSE)
        }
      }
      return(TRUE)
    } else if (!is.null(tol) && is.double(actual)) {
      compareReal(unclass(actual), unclass(expected), tol)
    } else if (!is.null(tol) && is.complex(actual)) {
      compareReal(unclass(Re(actual)), unclass(Re(expected)), tol) &&
        compareReal(unclass(Im(actual)), unclass(Im(expected)), tol)
    } else {
      return(identical(actual, expected))
    }
}


closeTo <- function(expected, delta) {
    stopifnot(is.numeric(expected) & is.numeric(delta) & length(delta) == 1L)
	function(actual) {
		length(expected) == length(actual) &&
				all(abs(expected-actual)<delta)	
	}
}

identicalTo <- function(expected, tol = NULL) {
	tolMissing <- missing(tol) 
	function(actual) {
	    identical.rec(actual, expected, tol)
	}
}

deparsesTo <- function(expected) {
    function(actual) {
        identical(paste(deparse(actual), collapse=""), expected)
    }
}

equalTo <- function(expected) {
	function(actual) {
		if (is.list(actual))
 	        identical.rec(actual, expected)
 		else
 		    length(actual) == length(expected) && all(actual == expected)
  	}
}

instanceOf <- function(expected) {
    function(actual) {
        inherits(actual, expected)
    }
}

isTrue <- function() {
    function(actual) {
        identical(TRUE, actual)
    }
}

isFalse <- function() {
    function(actual) {
        identical(FALSE, actual)
    }
}

throwsError <- function() {
	function(actual) {
		result <- tryCatch( force(actual), error = function(e) e )
		return(inherits(result, "error")) 
	}
}

emitsWarning <- function() {
	function(actual) {
		result <- tryCatch( force(actual), warning = function(e) e )
		return(inherits(result, "warning")) 
	}
}

not <- function(matcher) {
	function(actual) {
		return(!matcher(actual))
	}
}
