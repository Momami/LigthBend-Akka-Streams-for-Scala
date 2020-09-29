package com.lightbend.akkassembly

import akka.NotUsed
import akka.actor.SupervisorStrategy
import akka.stream.{ActorAttributes, Supervision}
import akka.stream.scaladsl.Flow

object QualityAssurance {
  case class CarFailedInspection(car: UnfinishedCar) extends IllegalStateException
}

class QualityAssurance {
  import QualityAssurance._

  val decider: Supervision.Decider = {
    case _: CarFailedInspection => Supervision.Resume
    case _ => Supervision.Stop
  }

  val inspect: Flow[UnfinishedCar, Car, NotUsed] =
    Flow[UnfinishedCar].collect{
      case car if car.color.isDefined && car.engine.isDefined &&
        car.wheels.size == 4 =>
          Car(SerialNumber(), car.color.get, car.engine.get, car.wheels, None)
      case car => throw CarFailedInspection(car)
    }.withAttributes(
      ActorAttributes.supervisionStrategy(decider)
    )
}
