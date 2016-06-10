package nl.tkp.nota.jobs.Conversie


//import scalikejdbc._

import org.apache.flink._
import org.apache.flink.api.common.functions.{FilterFunction, FlatMapFunction, RichMapFunction}
import org.apache.flink.api.common.typeinfo.BasicTypeInfo
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.api.java.typeutils.TupleTypeInfo
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.scala.typeutils._
//import org.joda.time.{DateTime, Interval, LocalDateTime}
import org.apache.flink.api.scala._

import org.apache.flink.api.java.io.jdbc.JDBCInputFormat
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat.JDBCInputFormatBuilder
import org.apache.flink.api.java.typeutils.TupleTypeInfo


object Conversie {
  def main(args: Array[String]): Unit = {

    println("Start")
  }
}


case class NotaRecord( ntaId: Int, notaRunId: Int, psnWerkgeverId: Int )
case class Nota( ntaId: Int, notaRunId: Int, psnWerkgeverId: Int, notablokken: Seq[NotaBlok] )

case class NotaBlok( notablokId: Int, ntaId: Int, BlokOmschrijving: String, blokvolgorde: Int)

object Download {
  def main(args: Array[String]) {

    val flinkEnv = ExecutionEnvironment.getExecutionEnvironment

    val jdbcInputFormat = JDBCInputFormat.buildJDBCInputFormat()
                            .setDrivername("oracle.jdbc.OracleDriver")
                            .setDBUrl("jdbc:oracle:thin:@devdbs01:1521:PPSDEV")
                            .setUsername("KOOIP")
                            .setPassword("ikke1234")

                            .setQuery("select id nta_id from pas_notas")  //, nrn_id, psn_werkgever_id
      .finish()

    val dbData   = flinkEnv.createInput( jdbcInputFormat )


    println("jdbc="+ dbData)

    println("Start download ")

    /* Scalajdbc part --- not used */
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