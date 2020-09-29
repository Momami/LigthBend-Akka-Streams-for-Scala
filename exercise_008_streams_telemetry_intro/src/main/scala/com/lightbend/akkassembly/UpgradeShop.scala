package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.FlowShape
import akka.stream.scaladsl.{Balance, Flow, GraphDSL, Merge}
import com.lightbend.akkassembly.Upgrade._

class UpgradeShop {
  val installUpgrades: Flow[UnfinishedCar, UnfinishedCar, NotUsed] =
    Flow.fromGraph(
      GraphDSL.create() {
        implicit builder: GraphDSL.Builder[NotUsed] =>
          import GraphDSL.Implicits._
          val balance = builder.add(Balance[UnfinishedCar](3))
          val merge = builder.add(Merge[UnfinishedCar](3))
          val dX = Flow[UnfinishedCar].map(_.installUpgrade(DX))
          val sport = Flow[UnfinishedCar].map(_.installUpgrade(Sport))

          balance ~> dX ~> merge
          balance ~> merge
          balance ~> sport ~> merge


          FlowShape(balance.in, merge.out)
      }
    )
}
