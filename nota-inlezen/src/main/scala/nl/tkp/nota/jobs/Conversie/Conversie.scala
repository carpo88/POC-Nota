package nl.tkp.nota.jobs.Conversie


//import scalikejdbc._

import org.apache.flink._
import org.apache.flink.api.common.functions.{FilterFunction, FlatMapFunction, RichMapFunction}
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.scala.typeutils._
//import org.joda.time.{DateTime, Interval, LocalDateTime}

import org.apache.flink.api.java.io.

object Conversie {
  def main(args: Array[String]): Unit = {

    println("Start")
  }
}


case class NotaRecord( ntaId: Int, notaRunId: Int, psnWerkgeverId: Int )
case class Nota( ntaId: Int, notaRunId: Int, psnWerkgeverId: Int, notablokken: Map[NotaBlok] )

case class NotaBlok( notablokId: Int, ntaId: Int, BlokOmschrijving: String, blokvolgorde: Int)


object Download {
  def main(args: Array[String]) {

    val flinkEnv = ExecutionEnvironment.getExecutionEnvironment

//    val q = flinkEnv.createInput(
//      JDBCInputFormat.buildJDBCInputFormat()//
//        .setDrivername(getDatabaseDriverName())//
//        .setDBUrl(getDatabaseUrl())//
//        .setUsername(getDatabaseUsername())//
//        .setPassword(getDatabasePassword())//
//        .setQuery(getQuery())//
//        .finish()
//      , NotaRecord.typeInformation
//    )

    println("Start download ")

//    Class.forName("oracle.jdbc.OracleDriver")
//    ConnectionPool.singleton("jdbc:oracle:thin:kooip/ikke1234@devdbs01:1521:PPSDEV","kooip", "ikke1234")
//    implicit val session = AutoSession
//
//
//          val myNotas =
//            sql"""
//                 select
//                  ID NTA_ID
//                  ,NRN_ID
//                  ,PSN_WERKGEVER_ID
//         from
//                    pas_notas
//           """.map(_.toMap).list.apply
//
//          myNotas.foreach( n => println("Nota = "+ n))
//
//          val myNotaBlokken =
//            sql"""
//              select id nbk_id, nta_id, omschrijving_blok, volgorde_notablok from pas_notablokken
//            """.map(_.toMap).list.apply
//
//          val flinkNotas = flinkEnv.fromCollection(myNotas)

  }

}


/*
pas-notas
                  ,PSN_BETALER_ID
                  ,STATUS
                  ,NOTASOORT
                  ,NOTA_TOTAAL
                  ,TE_ANNULEREN
                  ,REDEN_ANNULERING
                  ,NOTATYPE
                  ,REFERENTIEOPDRACHTGEVER
                  ,CONTACTAFDELING_TKP
                  ,TELEFOONNUMMER_TKP
                  ,FAXNUMMER_TKP
                  ,EMAILADRES_TKP
                  ,NOTANUMMER
                  ,NOTADATUM
                  ,VERVALDATUM
                  ,BETALINGSKENMERK
                  ,INTERFACE_UITGEVOERD
                  ,BERICHT_ID
                  ,VERZENDDATUM
                  ,AFREKENINGBEDRAG_TOTAAL
                  ,CORRECTIEBEDRAG_TOTAAL
                  ,AANTAL_AFREKENINGEN
                  ,AANTAL_CORRECTIES
                  ,AANTAL_DIENSTVERBANDEN


 */