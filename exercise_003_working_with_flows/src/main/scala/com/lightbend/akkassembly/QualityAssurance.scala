package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.Flow

class QualityAssurance {
  val inspect: Flow[UnfinishedCar, Car, NotUsed] =
    Flow[UnfinishedCar].collect{
      case car if car.color.isDefined && car.engine.isDefined &&
        car.wheels.size == 4 =>
          Car(SerialNumber(), car.color.get, car.engine.get, car.wheels, None)
    }
}
