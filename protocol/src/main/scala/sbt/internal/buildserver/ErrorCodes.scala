/*
 * sbt
 * Copyright 2011 - 2019, Lightbend, Inc.
 * Copyright 2008 - 2010, Mark Harrah
 * Licensed under Apache License 2.0 (see LICENSE)
 */

package sbt
package internal
package buildserver

/** Holds the error codes for the BSP implementation here. */
object ErrorCodes {
  // this is essentially a lookup table encoded in Scala,
  // so heavy usage of vertical alignment is beneficial
  // format: off

  val NotInitializedError = -32002L // A request was received from the client before it has sent the
                                    // initialization request.

  // format: on
}
