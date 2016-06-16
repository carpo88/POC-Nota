package nl.tkp.nota.jobs.Conversie


//import scalikejdbc._

import org.apache.flink._
import org.apache.flink.api.common.functions.{FilterFunction, FlatMapFunction, RichMapFunction}
import org.apache.flink.api.common.typeinfo.{BasicTypeInfo, TypeInformation}
import org.apache.flink.api.common.typeutils.TypeSerializerFactory
import org.apache.flink.api.java.io.jdbc.split.GenericParameterValuesProvider
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.api.java.typeutils.TupleTypeInfo
import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.api.scala.typeutils._
//import org.joda.time.{DateTime, Interval, LocalDateTime}
import org.apache.flink.api.scala._

import org.apache.flink.api.java.io.jdbc.JDBCInputFormat
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat.JDBCInputFormatBuilder
import org.apache.flink.api.java.typeutils.TupleTypeInfo
import org.apache.flink.api.table.typeutils.RowTypeInfo

import org.apache.flink.api.table.Row

import scala.util.control._


import java.sql.ResultSet

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
//    val flinkEnv =  ExecutionEnvironment.createLocalEnvironment(parallelism = 1)

    flinkEnv.setParallelism(1)

    //flinkEnv.getConfig.disableForceKryo
    //flinkEnv.getConfig.enableForceAvro
    flinkEnv.getConfig.enableObjectReuse
    flinkEnv.getConfig.enableForceKryo


   nl.tkp.nota.jobs.JDBCTestBase.prepareTestDb()


    val testInputFormat = JDBCInputFormat.buildJDBCInputFormat()
      .setDrivername(nl.tkp.nota.jobs.JDBCTestBase.DRIVER_CLASS)
      .setDBUrl(nl.tkp.nota.jobs.JDBCTestBase.DB_URL)
      .setQuery(nl.tkp.nota.jobs.JDBCTestBase.SELECT_ALL_BOOKS)
      .setRowTypeInfo(nl.tkp.nota.jobs.JDBCTestBase.rowTypeInfo)
      .setResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)
      .finish

//    val m0 = testInputFormat.createInputSplits(1).length

    testInputFormat.openInputFormat()
    testInputFormat.open(null)

    val testRow = new org.apache.flink.api.table.Row(5)

    val loop = new Breaks
    loop.breakable{
      while( ! testInputFormat.reachedEnd())
      {
        val testNext = testInputFormat.nextRecord(testRow)
        if (testNext == null) loop.break

        println("testNext ="+ testNext)
      }

    }

    testInputFormat.close()
    testInputFormat.closeInputFormat()

    val myData = flinkEnv.createInput(testInputFormat)

    myData.collect.foreach( r => println( "Row="+r ))

//
//    val notaRowTypeInfo = new RowTypeInfo( Seq( org.apache.flink.api.common.typeinfo.BasicTypeInfo.STRING_TYPE_INFO), Seq("id") )
//
//    val notaRowSerlia = notaRowTypeInfo.createSerializer(flinkEnv.getConfig)
//    println("Serialize ="+ notaRowSerlia)
//
//    val jdbcInputFormat = nl.tkp.nota.jobs.MyJDBCInputFormat.buildJDBCInputFormat()
//                            .setDrivername("oracle.jdbc.OracleDriver")
//                            .setDBUrl("jdbc:oracle:thin:@devdbs01:1521:PPSDEV")
//                            .setUsername("KOOIP")
//                            .setPassword("ikke1234")
//                            .setRowTypeInfo(notaRowTypeInfo)
//                            .setQuery("select to_char(id) id from pas_notas")  //, nrn_id, psn_werkgever_id
//                            .finish()
//
//    val dbData   = flinkEnv.createInput( jdbcInputFormat )
//
//
//    println("jdbc="+ dbData.collect())
//
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