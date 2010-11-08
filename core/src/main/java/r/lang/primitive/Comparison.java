/*
 * R : A Computer Language for Statistical Data Analysis
 * Copyright (C) 1995, 1996  Robert Gentleman and Ross Ihaka
 * Copyright (C) 1997--2008  The R Development Core Team
 * Copyright (C) 2003, 2004  The R Foundation
 * Copyright (C) 2010 bedatadriven
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package r.lang.primitive;

public class Comparison {


  public static boolean equalTo(double x, double y) {
    return x == y;
  }

  public static boolean equalTo(String x, String y) {
    return x.equals(y);
  }

  public static boolean notEqualTo(double x, double y) {
    return x != y;
  }

  public static boolean notEqualTo(String x, String y) {
    return !x.equals(y);
  }

  public static boolean lessThan(double x, double y) {
    return x < y;
  }

  public static boolean lessThan(String x, String y) {
    return x.compareTo(y) < 0;
  }

  public static boolean lessThanOrEqualTo(double x, double y) {
    return x <= y;
  }

  public static boolean lessThanOrEqualTo(String x, String y) {
    return x.compareTo(y) <= 0;
  }

  public static boolean greaterThan(double x, double y) {
    return x > y;
  }

  public static boolean greaterThan(String x, String y) {
    return x.compareTo(y) > 0;
  }

  public static boolean greaterThanOrEqual(double x, double y) {
    return x >= y;
  }

  public static boolean greaterThanOrEqual(String x, String y) {
    return x.compareTo(y) >= 0;
  }
}
