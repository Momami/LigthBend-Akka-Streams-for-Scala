package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Source}

import scala.concurrent.duration.FiniteDuration

class BodyShop(buildTime: FiniteDuration) {
  val cars: Source[UnfinishedCar, NotUsed] =
    Source.repeat(UnfinishedCar()).throttle(1, buildTime)
}
