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


object Download {
  def main(args: Array[String]) {

    val flinkEnv = ExecutionEnvironment.getExecutionEnvironment

    flinkEnv.setParallelism(1)

   nl.tkp.nota.jobs.JDBCTestBase.prepareTestDb()


    val testInputFormat = JDBCInputFormat.buildJDBCInputFormat()
      .setDrivername(nl.tkp.nota.jobs.JDBCTestBase.DRIVER_CLASS)
      .setDBUrl(nl.tkp.nota.jobs.JDBCTestBase.DB_URL)
      .setQuery(nl.tkp.nota.jobs.JDBCTestBase.SELECT_ALL_BOOKS)
      .setRowTypeInfo(nl.tkp.nota.jobs.JDBCTestBase.rowTypeInfo)
      .setResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)
      .finish


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

  }

}
