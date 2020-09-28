package com.lightbend.akkassembly

import akka.{Done, NotUsed}
import akka.event.LoggingAdapter
import akka.stream.scaladsl.{Flow, Sink}

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

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

  def sample(sampleSize: FiniteDuration): Flow[Car, Car, NotUsed] = {
    Flow[Car].takeWithin(sampleSize)
  }
}
