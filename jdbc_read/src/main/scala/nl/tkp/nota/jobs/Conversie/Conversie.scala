package nl.tkp.nota.jobs.Conversie


//import scalikejdbc._

import nl.tkp.nota.jobs.JDBCFullTest
import org.apache.flink._
import org.apache.flink.api.common.functions.{FilterFunction, FlatMapFunction, RichMapFunction}
import org.apache.flink.api.common.typeinfo.{BasicTypeInfo, TypeInformation}
import org.apache.flink.api.common.typeutils.TypeSerializerFactory
//import org.apache.flink.api.java.io.jdbc.split.GenericParameterValuesProvider
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

import org.apache.flink.api.table._

import scala.util.control._



import java.sql.ResultSet
import scala.language.implicitConversions

object Download {
  def main(args: Array[String]) {

    val flinkEnv = ExecutionEnvironment.getExecutionEnvironment

    nl.tkp.nota.jobs.JDBCTestBase.prepareTestDb()
//    val fullJDBCTest = new JDBCFullTest
//    fullJDBCTest.testJdbcInOut()

    import nl.tkp.nota.jobs.JDBCTestBase

    val testInputFormat = JDBCInputFormat
      .buildJDBCInputFormat
      .setDrivername(JDBCTestBase.DRIVER_CLASS)
      .setDBUrl(JDBCTestBase.DB_URL)
      .setQuery(JDBCTestBase.SELECT_ALL_BOOKS)
      .setRowTypeInfo(JDBCTestBase.rowTypeInfo)
      .setResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)
      .finish

    val myData = flinkEnv.createInput(testInputFormat)

    myData.collect.foreach( r => println( "Row="+r ))

  }

}
