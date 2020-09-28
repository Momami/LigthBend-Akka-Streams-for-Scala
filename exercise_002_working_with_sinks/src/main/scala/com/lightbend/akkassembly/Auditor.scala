package com.lightbend.akkassembly

import akka.Done
import akka.event.LoggingAdapter
import akka.stream.scaladsl.Sink

import scala.concurrent.Future

class Auditor {
  val count: Sink[Any, Future[Int]] =
    Sink.fold(0){
      case (count, _) => count + 1
    }

  def log(implicit l: LoggingAdapter): Sink[Any, Future[Done]] = {
    Sink.foreach{
      elem => l.debug(elem.toString)
    }
  }
}
