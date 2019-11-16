/*
 * sbt
 * Copyright 2011 - 2019, Lightbend, Inc.
 * Copyright 2008 - 2010, Mark Harrah
 * Licensed under Apache License 2.0 (see LICENSE)
 */

package sbt
package internal
package server

import sjsonnew.support.scalajson.unsafe.Converter
import sbt.internal.buildserver._
import sbt.internal.protocol._

private[sbt] final case class BuildServerError(code: Long, message: String)
    extends Throwable(message)

private[sbt] object BuildServerProtocol {

  lazy val handler: ServerHandler = ServerHandler({
    case callback: ServerCallback =>
      import callback._
      ServerIntent(
        {
          def json(r: JsonRpcRequestMessage) =
            r.params.getOrElse(
              throw LangServerError(
                sbt.internal.langserver.ErrorCodes.InvalidParams,
                s"param is expected on '${r.method}' method."
              )
            )

          {
            case r: JsonRpcRequestMessage if r.method == "build/initialize" =>
              import sbt.internal.buildserver.codec.JsonProtocol._
              val param = Converter.fromJson[InitializeBuildParams](json(r)).get
              // TODO: What languages to support?
              val capabilities = param.capabilities.languageIds.filter(_ == "scala")
              // TODO: Do we need to distinguish between LSP initialization and BSP?
              setInitialized(true);
              jsonRpcRespond(
                InitializeBuildResult(
                  "sbt",
                  "1.0",
                  "2.0",
                  BuildServerCapabilities(
                    CompileProvider(capabilities),
                    TestProvider(capabilities),
                    RunProvider(capabilities),
                    true,
                    true,
                    true,
                    true
                  ),
                  None
                ),
                Option(r.id)
              )
            // TODO: Only do this for BSP requests.
            case r: JsonRpcRequestMessage if !initialized =>
              jsonRpcRespondError(
                Option(r.id),
                ErrorCodes.NotInitializedError,
                "The build has not been initialized. The client must sent a build/initialize request as the first request."
              )
            case r: JsonRpcRequestMessage if r.method == "workspace/buildTargets" =>
              appendExec(Exec("bspWorkspaceBuildTargets", None, Some(CommandSource(name))))
              ()
          }
        },
        PartialFunction.empty
      )
  })
}
